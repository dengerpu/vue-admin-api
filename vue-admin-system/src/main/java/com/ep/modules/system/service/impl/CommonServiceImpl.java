package com.ep.modules.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ep.modules.system.service.CommonService;
import com.ep.modules.system.service.SQLService;
import com.ep.utils.ResultInfo;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author dep
 * @version 1.0
 * @date 2023-06-08 22:01
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private SQLService sqlService;

    @Override
    public ResultInfo query(JSONObject json) throws Exception {
        ResultInfo resultInfo = new ResultInfo();
        List<Object> data = null;
        Integer count = 0;
        if (json.get("sql") != null) {
            String Sql = json.getString("sql");
            data = this.sqlService.selectListBySQL(Sql);
        }else if(json.get("table") != null) {
            String sql = "";
            JSONObject table = (JSONObject) JSONObject.toJSON(json.get("table"));
            if (table.get("name") == null) {
                throw new Exception("You must pass in the table name");
            }else {
                if (table.get("fields") != null) {
                    sql = "SELECT " +  table.get("fields") + " FROM " + table.get("name");
                } else {
                    sql = "SELECT * FROM " + table.get("name");
                }
                if (table.get("filter") != null) {
                    sql += " WHERE ";
                    JSONArray filters = (JSONArray) JSONObject.toJSON(table.get("filter"));
                    for (int i = 0; i < filters.size(); i++) {
                        JSONObject filter = filters.getJSONObject(i);
                        if (filter.get("and") != null) {
                            JSONArray filtersAnd = (JSONArray) JSONObject.toJSON(filter.get("and"));
                            JSONObject filtersAndObject = (JSONObject) JSONObject.toJSON(filtersAnd.get(0));
                            if (i == 0) {
                                sql += "(";
                            } else {
                                sql += " AND (";
                            }
                            for (Map.Entry<String, Object> entry : filtersAndObject.entrySet()) {
                                sql += entry.getKey();
                                JSONObject valueJson =  (JSONObject) JSONObject.toJSON(entry.getValue());
                                for(Map.Entry<String, Object> entry1 : valueJson.entrySet()) {
                                    sql += entry1.getKey();
                                    sql += ("'" + entry1.getValue() + "'");
                                }
                                sql += " AND ";
                            }
                            sql = sql.substring(0, sql.length() - 5);
                            sql += ")";
                        }
                        if (filter.get("or") != null) {
                            JSONArray filtersAnd = (JSONArray) JSONObject.toJSON(filter.get("or"));
                            JSONObject filtersOrObject = (JSONObject) JSONObject.toJSON(filtersAnd.get(0));
                            if (i == 0) {
                                sql += "(";
                            } else {
                                sql += " OR (";
                            }
                            for (Map.Entry<String, Object> entry : filtersOrObject.entrySet()) {
                                sql += entry.getKey();
                                JSONObject valueJson =  (JSONObject) JSONObject.toJSON(entry.getValue());
                                for(Map.Entry<String, Object> entry1 : valueJson.entrySet()) {
                                    sql += entry1.getKey();
                                    sql += ("'" + entry1.getValue() + "'");
                                }
                                sql += " OR ";
                            }
                            sql = sql.substring(0, sql.length() - 4);
                            sql += ")";
                        }
                    }
                }

                if (table.get("group") != null) {
                    sql += (" GROUP BY " + "'" +  table.get("group") + "'");
                }

                if (table.get("order") != null) {
                    sql += (" ORDER BY " + "'" + table.get("order") + "'");
                }
                String CountSql = "SELECT COUNT(*) " + sql.substring(sql.indexOf("FROM"));
                count = this.sqlService.selectCountBySQL(CountSql);
                if (table.get("page") != null || table.get("size") != null) {
                    int page = 1;
                    int size = 10;
                    if (table.get("page") != null) {
                        page = (Integer) table.get("page");
                    }
                    if (table.get("size") != null) {
                        size = (Integer) table.get("size");
                    }
                    sql += (" limit " + (page - 1) * size + " , " + page *size);
                }
                data = this.sqlService.selectListBySQL(sql);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rows", data);
        map.put("count", count);
        resultInfo.setCode(1);
        resultInfo.setData(map);
        return resultInfo;
    }
}
