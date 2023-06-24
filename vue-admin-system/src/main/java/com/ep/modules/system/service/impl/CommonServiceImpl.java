package com.ep.modules.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ep.modules.system.service.CommonService;
import com.ep.modules.system.service.SQLService;
import com.ep.utils.ResultInfo;
import com.ep.utils.ReturnCode;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
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
        List<Object> data = null;
        Integer count = 0;
        if (json.get("sql") != null) {
            String Sql = json.getString("sql");
            data = this.sqlService.selectListBySQL(Sql);
        }else if(json.get("table") != null) {
            String sql = "";
            JSONObject table = (JSONObject) JSONObject.toJSON(json.get("table"));
            if (table.get("name") == null) {
                return ResultInfo.fail(ReturnCode.S400.ordinal(), "You must pass in the table name");
            }else {
                if (table.get("fields") != null) {
                    sql = "SELECT " +  table.get("fields") + " FROM " + table.get("name");
                } else {
                    sql = "SELECT * FROM " + table.get("name");
                }
                if (table.get("filter") != null) {
                    sql += this.getWhereConditons(table.get("filter"));
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
        return ResultInfo.success(map);
    }

    @Override
    public ResultInfo save(JSONObject json) throws Exception {
        Boolean isSuccess = false;
        if (json.get("sql") != null && json.get("type") != null) {
            String Sql = json.getString("sql");
            String type = json.getString("type");
            switch (type) {
                case "add":  isSuccess = this.sqlService.insertBySQL(Sql); break;
                case "edit": isSuccess = this.sqlService.updateBySQL(Sql); break;
                case "del": isSuccess = this.sqlService.deleteBySQL(Sql); break;
                default: return ResultInfo.fail(ReturnCode.S400.getCode(), "type类型传递错误");
            }
        }else if (json.get("table") != null && json.get("type") != null) {
            String type = json.getString("type");
            JSONObject table = (JSONObject) JSONObject.toJSON(json.get("table"));
            String tableName = table.getString("name");
            if (tableName != null) {
                JSONObject data = (JSONObject) JSONObject.toJSON(table.get("data"));
                if ("add".equals(type) || "edit".equals(type)) {
                    if (data == null) {
                        return ResultInfo.fail(ReturnCode.S400.getCode(), "data为空");
                    }
                }
                switch (type) {
                    case "add":  isSuccess = this.executeAdd(tableName, data); break;
                    case "edit": isSuccess = this.executeEdit(tableName, data, table); break;
                    case "del": isSuccess = this.executeDelete(tableName, table); break;
                    default: return ResultInfo.fail(ReturnCode.S400.getCode(), "type类型传递错误");
                }
            } else {
                return ResultInfo.fail(ReturnCode.S400.getCode(), "name类型传递错误");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("result", isSuccess);
            return ResultInfo.success(map);
        } else {
            return ResultInfo.fail(ReturnCode.S400.getCode(), "参数有误");
        }
        return ResultInfo.fail(ReturnCode.S400.getCode(), "参数有误");
    }

    private String getWhereConditons(Object filterCondition) {
        String sql = " WHERE ";
        JSONArray filters = (JSONArray) JSONObject.toJSON(filterCondition);
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
        return sql;
    }

    /***
     * 添加逻辑处理
     * @param tableName
     * @param data
     * @return
     */
    private Boolean executeAdd(String tableName, JSONObject data) {
        String sql = ("INSERT INTO " + tableName);
        String keys = "";
        String values = "";
        for(String key: data.keySet()){
            String value =data.get(key).toString();
            keys += (key+",");
            values += ("'" + value+"',");
        };
        keys=keys.substring(0,keys.length()-1);
        values=values.substring(0,values.length()-1);
        sql = sql + "("+keys+")" + " VALUES " + "("+values+")";
        return  this.sqlService.insertBySQL(sql);
    }

    /***
     * 修改逻辑处理
     * @param tableName
     * @param data
     * @param table
     * @return
     */
    private Boolean executeEdit(String tableName, JSONObject data, JSONObject table) {
        String sql ="UPDATE " + tableName + " SET ";
        String values = "";
        for(String key: data.keySet()){
            String value =data.get(key).toString();
            values = key + "='" + value + "',";
        };
        values=values.substring(0,values.length()-1);
        sql = sql + values;
        if (table.get("filter") != null) {
            sql += this.getWhereConditons(table.get("filter"));
        }
        return this.sqlService.updateBySQL(sql);
    }

    /***
     * 删除逻辑处理
     * @param tableName
     * @param table
     * @return
     */
    private Boolean executeDelete(String tableName,JSONObject table){
        String sql ="DELETE FROM " + tableName;
        if (table.get("filter") != null) {
            sql += this.getWhereConditons(table.get("filter"));
        }
        return this.sqlService.deleteBySQL(sql);
    }
}
