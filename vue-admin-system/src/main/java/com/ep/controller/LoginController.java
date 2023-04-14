package com.ep.controller;

import com.ep.entity.User;
import com.ep.service.LoginService;
import com.ep.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
 * @author dep
 * @version 1.0
 * @date 2023-04-11 22:07
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResultInfo login(@RequestBody User user) {
        return loginService.login(user);
    }

    @GetMapping("/logout")
    public ResultInfo logout() {
        return loginService.logout();
    }
}
