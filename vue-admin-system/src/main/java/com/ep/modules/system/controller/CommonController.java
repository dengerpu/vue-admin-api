package com.ep.modules.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.ep.modules.system.service.CommonService;
import com.ep.utils.ResultInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
 * @author dep
 * @version 1.0
 * @date 2023-06-08 21:57
 */
@Api(tags = "通用接口")
@RestController
@RequestMapping("/api/data/public")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @PostMapping("/query")
    public ResultInfo CommonQuery(@RequestBody JSONObject json) throws Exception {
        return this.commonService.query(json);
    }
}
