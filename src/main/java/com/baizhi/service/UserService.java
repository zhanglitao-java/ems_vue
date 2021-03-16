package com.baizhi.service;

import com.baizhi.entity.User;

/**
 * @author taoshen
 * @create 2021/3/14
 */
public interface UserService {
    //用户注册
    int register (User user);

    //用户登录
    User login(User user);
}
