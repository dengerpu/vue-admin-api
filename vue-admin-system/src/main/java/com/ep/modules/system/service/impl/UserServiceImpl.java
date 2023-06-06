package com.ep.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ep.modules.system.entity.UserInfo;
import com.ep.modules.system.mapper.UserInfoMapper;
import com.ep.modules.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * @author dep
 * @version 1.0
 * @date 2023-04-21 21:32
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfo(Long id) {
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getUserId,id);
        return userInfoMapper.selectOne(lambdaQueryWrapper);
    }
}
