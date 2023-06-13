package com.ep.modules.system.service.impl;

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
    public ResultInfo query(JSONObject json) {
        ResultInfo resultInfo = new ResultInfo();
        if (json.get("sql") != null) {
            String Sql = json.getString("sql");
            List<Object> objects = this.sqlService.selectListBySQL(Sql);
            resultInfo.setCode(1);
            resultInfo.setData(objects);
        }
        return resultInfo;
    }
}
