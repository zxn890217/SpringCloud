package com.example.usercenter.controller;

import com.example.usercenter.common.base.controller.BaseRestController;
import com.example.usercenter.common.base.service.BaseService;
import com.example.usercenter.model.QUser;
import com.example.usercenter.model.User;
import com.example.usercenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zxn on 2017/10/23.
 */
@RestController
@RequestMapping(value="/user")
public class UserController extends BaseRestController<User, QUser>{
    @Autowired
    private UserService userService;

    @Override
    protected BaseService<User, QUser> getService() {
        return userService;
    }
}
