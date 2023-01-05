package com.ljx.demo.controller;

import com.ljx.demo.mapper.UserMapper;
import com.ljx.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("user")
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("register") //注册
    public String register(String user_name, String password, String email){
        if(ObjectUtils.isEmpty(user_name)){
            return "用户名不允许为空";
        } else if (ObjectUtils.isEmpty(password)) {
            return "密码不能为空";
        }else if(userMapper.findUser(user_name) != 0){
            return "用户名已存在";
        }
        int result = userMapper.saveUser(user_name,password,email);
        if(result == 0){
            return "注册失败";
        }
        return "注册成功";
    }

    @GetMapping("login") //登陆
    public String login(String user_name, String password){
        User user = userMapper.selectUser(user_name, password);
        if(user == null){
            return "用户名或密码错误";
        }
        else{
            return "登陆成功";
        }
    }

    @GetMapping("logout") //注销
    public String logout(String user_name, String password){
        int res = userMapper.deleteUser(user_name, password);
        if(res == 1){
            return "注销成功";
        }else{
            return "注销失败,用户名或密码错误";
        }
    }
}
