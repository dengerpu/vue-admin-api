package com.ep.modules.system.controller;

import com.ep.modules.system.entity.User;
import com.ep.modules.system.service.LoginService;
import com.ep.utils.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
 * @author dep
 * @version 1.0
 * @date 2023-04-11 22:07
 */
@Api(tags = "登录")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public ResultInfo login(@RequestBody User user) {
        return loginService.login(user);
    }

    @ApiOperation("退出")
    @GetMapping("/logout")
    public ResultInfo logout() {
        return loginService.logout();
    }
}
