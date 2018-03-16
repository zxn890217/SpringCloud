package com.example.usercenter.controller;

import com.example.domain.base.BaseRestController;
import com.example.domain.base.BaseService;
import com.example.domain.base.RespBody;
import com.example.domain.entity.user.QUser;
import com.example.domain.entity.user.User;
import com.example.usercenter.common.utils.MD5Util;
import com.example.usercenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping(value="resetPassword")
    public RespBody resetPassword(User entity){
        String username = entity.getUsername();
        String password = entity.getPassword();
        if(StringUtils.isEmpty(username)){
            return new RespBody(false, "用户名不能为空");
        }
        if(StringUtils.isEmpty(password)){
            return new RespBody(false, "密码不能为空");
        }
        password = MD5Util.md5Hex(username+":"+password);
        entity.setPassword(password);
        if(userService.resetPassword(entity)){
            return new RespBody(true, "重置密码成功");
        }
        return new RespBody(false, "重置密码失败");
    }
}
