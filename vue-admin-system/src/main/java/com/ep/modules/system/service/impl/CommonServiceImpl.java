package com.ep.modules.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ep.modules.system.service.CommonService;
import com.ep.modules.system.service.SQLService;
import com.ep.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
                    sql += " WHERE 1 = 1";
                    JSONArray filters = (JSONArray) JSONObject.toJSON(table.get("filter"));
                    for (int i = 0; i < filters.size(); i++) {
                        JSONObject filter = filters.getJSONObject(i);

                    }
                }
                data = this.sqlService.selectListBySQL(sql);
            }
        }

        resultInfo.setCode(1);
        resultInfo.setData(data);
        return resultInfo;
    }
}
