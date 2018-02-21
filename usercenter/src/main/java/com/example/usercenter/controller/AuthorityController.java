package com.example.usercenter.controller;

import com.example.domain.base.BaseRestController;
import com.example.domain.base.BaseService;
import com.example.domain.entity.user.Authority;
import com.example.domain.entity.user.QAuthority;
import com.example.usercenter.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ZXN on 2018/2/21.
 */
public class AuthorityController extends BaseRestController<Authority, QAuthority> {
    @Autowired
    private AuthorityService authorityService;
    @Override
    protected BaseService<Authority, QAuthority> getService() {
        return authorityService;
    }
}
