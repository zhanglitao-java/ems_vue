package com.baizhi.dao;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author taoshen
 * @create 2021/3/14
 */
@Mapper//DAO接口需要注入IOC中
public interface UserDAO {
    //用户注册
    void register(User user);

    //根据用户名查找用户
    User findByUsername(String username);
}
