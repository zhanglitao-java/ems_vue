package com.baizhi.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author taoshen
 * @create 2021/3/14
 */
@Data
@Accessors(chain = true)//开启链式调用字段
public class User {
    private String id;
    private String username;
    private String realname;
    private String password;
    private String sex;
    private String status;
    private Date registerTime;
}
