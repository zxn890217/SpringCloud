package com.example.demo.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zxn on 2017/11/22.
 */
@Controller
@RequestMapping(value="/hello")
public class HelloController {
    @RequestMapping(value="/say")
    @ResponseBody
    @RequiresPermissions("hello-say")
    public String say(){
        return "Hello world!";
    }
}
