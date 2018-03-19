package com.example.usercenter.controller;

import com.example.domain.base.RespBody;
import com.example.domain.entity.user.Menu;
import com.example.domain.entity.user.QMenu;
import com.example.domain.entity.user.User;
import com.example.usercenter.common.utils.MD5Util;
import com.example.usercenter.service.MenuService;
import com.example.usercenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by zxn on 2018/2/26.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    /**
     * 登录页面
     * */
    @RequestMapping(value = "/toLogin")
    public String toLogin(){
        return "admin/login";
    }
    @RequestMapping(value = "/login")
    @ResponseBody
    public RespBody login(User user){
        String username = user.getUsername();
        String password = user.getPassword();
        if(StringUtils.isEmpty(username)){
            return new RespBody(false, "用户名不能为空");
        }
        if(StringUtils.isEmpty(password)){
            return new RespBody(false, "密码不能为空");
        }
        password = MD5Util.md5Hex(username+":"+password);
        User u = userService.loadUser(username, password);
        if(u != null){
            String token = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(token, u, 1, TimeUnit.DAYS);
            return new RespBody(true, "登录成功", token);
        }
        return new RespBody(false, "用户名或密码不存在");
    }

    @RequestMapping(value = "/userInfo")
    @ResponseBody
    public RespBody userInfo(@RequestHeader("token") String token){
        User user = (User) redisTemplate.opsForValue().get(token);
        if(user!=null){
            return new RespBody(true, "登录成功", user);
        }
        return new RespBody(false, "用户未登录或会话超时");
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public RespBody logout(@RequestHeader("token") String token){
        redisTemplate.delete(token);
        return new RespBody(true, "登出成功");
    }

    @RequestMapping(value = "/menus")
    @ResponseBody
    public List<Menu> menus(@RequestHeader("token") String token, @RequestParam("fromType") String fromType){
        User user = (User) redisTemplate.opsForValue().get(token);
        if(user!=null){
            QMenu qMenu = new QMenu();
            qMenu.setFromType(fromType);
            qMenu.setUserId(user.getId());
            return menuService.getMenuTree(qMenu);
        }
        return null;
    }
}
