package com.ep.service;

import com.ep.entity.User;
import com.ep.utils.ResultInfo;

/***
 * @author dep
 * @version 1.0
 * @date 2023-04-11 22:08
 */
public interface LoginService {

    /***
     * 登陆
     * @param user
     * @return
     */
    public ResultInfo login(User user);


    /**
     * 退出
     * @return
     */
    public ResultInfo logout();
}
