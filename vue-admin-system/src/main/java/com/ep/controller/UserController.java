package com.ep.controller;

import com.ep.entity.UserInfo;
import com.ep.mapper.UserInfoMapper;
import com.ep.service.UserService;
import com.ep.utils.JwtUtil;
import com.ep.utils.ResultInfo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/***
 * @author dep
 * @version 1.0
 * @date 2023-04-21 21:21
 */
@RestController
@RequestMapping("/sys")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/user")
    public ResultInfo getUserInfo(HttpServletRequest request) throws Exception {
        //获取token
        String token = request.getHeader("token");
        Claims claims = JwtUtil.parseJWT(token);
        String userid = claims.getSubject();

        UserInfo userInfo = userService.getUserInfo(Long.parseLong(userid));
        if (userid == null) {
            return ResultInfo.fail(000, "获取用户信息失败");
        }else {
            return ResultInfo.success(userInfo);
        }
    }
}
