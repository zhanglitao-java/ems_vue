package com.baizhi.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baizhi.dao.UserDAO;
import com.baizhi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author taoshen
 * @create 2021/3/14
 */
@Service//service实现类注入IOC容器
@Transactional//service层要记得开启事务
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDAO userDAO;

    //用户注册
    @Override
    public int register(User user) {
        User user1 = userDAO.findByUsername(user.getUsername());
        if(user1 == null){
            //用户姓名那些东西 都是用户填入的 所以从Controller那边传过来 这里设置User中和用户填写无关的数据
            Assert.isNull(user1);
            user.setRegisterTime(new Date());
            user.setStatus("已激活");

            userDAO.register(user);
            return 1;
        }else{
            Assert.notNull(user1);
            //否则用户已经存在
            return 0;
        }
    }

    //用户登录
    @Override
    public User login(User user) {
        User user1 = userDAO.findByUsername(user.getUsername());
        //如果查询出来不为null  则说明确有此人 则接着验证密码是否正确
        if(ObjectUtil.isNotNull(user1)){
            if(user.getPassword().equals(user1.getPassword())){
                return user1;
            }else{
                throw new RuntimeException("密码填写错误");
            }
        }else{
            throw new RuntimeException("用户名填写错误");
        }
    }
}
