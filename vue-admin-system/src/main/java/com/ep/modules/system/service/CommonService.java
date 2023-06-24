package com.ep.modules.system.service;

import com.alibaba.fastjson.JSONObject;
import com.ep.utils.ResultInfo;

/***
 * @author dep
 * @version 1.0
 * @date 2023-06-08 22:01
 */
public interface CommonService {

    ResultInfo query(JSONObject json) throws Exception;

    ResultInfo save(JSONObject json) throws Exception;
}
