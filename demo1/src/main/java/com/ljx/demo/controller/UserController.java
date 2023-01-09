package com.ljx.demo.controller;

import com.ljx.demo.mapper.UserMapper;
import com.ljx.demo.pojo.User;
import com.ljx.demo.pojo.ResponseInfo;
import com.ljx.demo.utils.JwtUitl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@RequestMapping("user")
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("register") //注册
    public ResponseInfo register(String user_name, String password, String email){
        if(ObjectUtils.isEmpty(user_name)){
            return new ResponseInfo(100,"用户名不允许为空", "");
        } else if (ObjectUtils.isEmpty(password)) {
            return new ResponseInfo(100,"密码不能为空", "");
        }else if(userMapper.findUser(user_name) != 0){
            return new ResponseInfo(500,"用户名已存在", "");
        }
        int result = userMapper.saveUser(user_name,password,email);
        if(result == 0){
            return new ResponseInfo(500,"注册失败", "");
        }
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest().getSession();
        session.setAttribute(session.getId(), user_name);
        String token = JwtUitl.createToken(user_name);
        return new ResponseInfo(200,"注册成功", token);
    }

    @GetMapping("login") //登陆
    public ResponseInfo login(String user_name, String password){
        User user = userMapper.selectUser(user_name, password);
        if(user == null){
            return new ResponseInfo(500,"用户名或密码错误", "");
        }
        else{
            String token = JwtUitl.createToken(user_name);
            return new ResponseInfo(200,"登录成功", token);
        }
    }

    @GetMapping("logout") //注销
    public ResponseInfo logout(String user_name, String password){
        int res = userMapper.deleteUser(user_name, password);
        if(res == 1){
            //获取session
            HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                    .getRequestAttributes())).getRequest().getSession();
            //注销用户，使 session 失效
            session.invalidate();
            return new ResponseInfo(200,"注销成功", "");
        }else{
            return new ResponseInfo(500,"注销失败，用户名或密码错误", "");
        }
    }

    @GetMapping("tokenLogin")
    public ResponseInfo tokenLogin(String token){
        int res = JwtUitl.verify(token);
        String name = JwtUitl.returnUsername(token);
        if(res == 1 && userMapper.findUser(name) != 0){
            return new ResponseInfo(200,"登录成功", "");
        }
        else {
            return new ResponseInfo(500, "token验证失败", "");
        }
    }
}
