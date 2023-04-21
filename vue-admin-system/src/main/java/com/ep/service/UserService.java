package com.ep.service;

import com.ep.entity.UserInfo;

/***
 * @author dep
 * @version 1.0
 * @date 2023-04-21 21:23
 */
public interface UserService {

    /**
     * 获取用户信息
     * @return
     */
    UserInfo getUserInfo(Long id);
}
