package com.example.usercenter.controller;

import com.example.domain.base.BaseRestController;
import com.example.domain.base.BaseService;
import com.example.domain.entity.user.Authority;
import com.example.domain.entity.user.QAuthority;
import com.example.usercenter.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ZXN on 2018/2/21.
 */
@RestController
@RequestMapping(value = "/authority")
public class AuthorityController extends BaseRestController<Authority, QAuthority> {
    @Autowired
    private AuthorityService authorityService;
    @Override
    protected BaseService<Authority, QAuthority> getService() {
        return authorityService;
    }
}
