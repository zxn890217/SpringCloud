package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.util.RedisCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by zxn on 2017/11/22.
 */
@Controller
@RequestMapping(value="/sign")
public class SignController {

    @RequestMapping(value="/in")
    @ResponseBody
    public User in(){
        UUID uuid = UUID.randomUUID();
        User user = new User(uuid.toString(), "张三", "123456", "男", "15280034363", Arrays.asList(new String[]{"hello-say"}));
        RedisCache.setObject(user.getToken(), user, 3000);
        return user;
    }

    @RequestMapping(value="/out")
    @ResponseBody
    public String out(){
        return "";
    }
}
