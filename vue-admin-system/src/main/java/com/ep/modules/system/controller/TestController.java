package com.ep.modules.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * @author dep
 * @version 1.0
 * @date 2023-04-10 20:58
 */
@Api(tags = "测试")
@RestController
@RequestMapping("/test")
public class TestController {

    @ApiOperation("测试接口")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
