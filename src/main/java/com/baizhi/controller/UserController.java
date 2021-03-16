package com.baizhi.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author taoshen
 * @create 2021/3/13
 */
@RestController//@ResponseBody和@Controller的合体
@CrossOrigin//允许跨域
@RequestMapping("user")//整个controller的域名
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    //因为是接口式开发  所以尽量不要使用@RequestMapping 需要使用GetMapping或者PostMapping 标注是何种方法
    @GetMapping("getImage")
    public String getImageCode(HttpServletRequest servlet) throws IOException {//通过形参的方式引入HttpServletContext
        /*
            因为是前后端分离项目 所以就不存在session的概念了 要将数据存储在ServletContext域中
            我们这里处理图片的方式是通过Base64处理 就是将图片的流给Base64加密为字符串之后丢给前端
            然后前端根据加密之后的字符串 进行解密 从而得到图片 进行展示
            所谓Base64 就是通过64个字符用来表示二进制数据 正所谓计算机的世界中无论什么数据都是
            二进制 全是0或者1  所以说就可以通过Base64去传递转换任何数据
         */

        //通过验证码工具类生成一个随机4位的验证码
        String code = VerifyCodeUtils.generateVerifyCode(4);

        //将正确的验证码放进ServletContext域中 等到用户输入的时候和正确的验证码进行比对判断
        servlet.getServletContext().setAttribute("code", code);

        //将图片转为base64加密之后的字符串 利用工具类  我们需要给他一个输出流
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //调用工具类 将生成的图片搞进上面这个输出流
        VerifyCodeUtils.outputImage(120, 30, os, code);
        //通过Spring框架提供的Base64Utils工具调用encodeToString函数对图片输出流进行加密转换
        //切记只能接收字节数组 所以我们上面使用是ByteArrayOutputStream字节数组流
        String s = Base64Utils.encodeToString(os.toByteArray());

        //最终将加密之后的字符串递给前端 在字符串前面要记得加上指令前缀  这样的话到前端那边就不需要
        //再进行处理了 直接到浏览器那边 就会自动处理的 从而完美展示出图片
        log.info("执行了获取验证码getImage，访问地址为 /user/getImage");
        return "data:image/png;base64," + s;
    }

    /**
     * 用户注册
     */
    @PostMapping("register")//因为要接收的信息比较多  所以使用post请求体接收
    //前端传来的json格式数据就使用@RequestBody接收 将键值对按照User实体类字段进行接收
    //放在url后的参数 使用@RequestParam接收 通过HttpServletRequest拿到当前ServletContext域 获取其存储的code验证码
    public Map<String,Object> register(@RequestBody User user,@RequestParam String code,HttpServletRequest request){
        //后端接收参数数据时 要习惯性的打印一下 做一个日志
        log.info("用户填写信息：【{}】",user.toString());
        log.info("用户填写验证码：【{}】",code);

        //将所有信息都通过map形式的返回给前端 因为有@ResponseBody注解 所以返回的是json体
        Map<String,Object> map = new HashMap();

        try {
            String key = (String) request.getServletContext().getAttribute("code");
            if(key.equalsIgnoreCase(code)){
                int state = userService.register(user);
                if(state == 1){
                    map.put("state",true);
                    map.put("msg","注册成功");
                }else if(state == 0){
                    map.put("state",false);
                    map.put("msg","该用户名已经被注册");
                    return map;
                }
            }else{
                map.put("state",false);
                map.put("msg","用户验证码填写错误");
                return map;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            map.put("state",false);
            map.put("msg","注册失败");
        } finally {
            log.info("执行了用户注册register，访问地址为 /user/register");
        }

        return map;
    }

    //用户登录
    @PostMapping("login")
    public Map login(@RequestBody User user){
        log.info("用户登录填写 用户名:{} 密码:{}",user.getUsername(),user.getPassword());
        log.info("执行了用户登录login，访问地址为 /user/login");

        Map<String, Object> map = CollUtil.newHashMap();

        try{
            User user1 = userService.login(user);
            map.put("state",true);
            map.put("msg","登录成功");
            map.put("user",user1);
        }catch (Exception e){
            map.put("state",false);
            map.put("msg",e.getMessage());
        }

        return map;
    }
}
