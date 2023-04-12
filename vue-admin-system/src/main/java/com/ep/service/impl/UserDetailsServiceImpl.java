package com.ep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ep.entity.LoginUser;
import com.ep.entity.User;
import com.ep.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/***
 * @author dep
 * @version 1.0
 * @date 2023-04-11 20:34
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(lambdaQueryWrapper);

        System.out.println(user);
        // 查询不到抛异常（这里可以使用全局异常处理来处理）
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或者密码错误");
        }
        //封装成UserDetails对象返回
        return new LoginUser(user);
    }
}
