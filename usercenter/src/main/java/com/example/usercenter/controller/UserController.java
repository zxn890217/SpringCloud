package com.example.usercenter.controller;

import com.example.domain.base.BaseRestController;
import com.example.domain.base.BaseService;
import com.example.domain.entity.user.QUser;
import com.example.domain.entity.user.User;
import com.example.usercenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zxn on 2017/10/23.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseRestController<User, QUser> {
    @Autowired
    private UserService userService;

    @Override
    protected BaseService<User, QUser> getService() {
        return userService;
    }
}
