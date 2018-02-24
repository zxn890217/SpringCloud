package com.example.usercenter.controller;

import com.example.domain.base.BaseRestController;
import com.example.domain.base.BaseService;
import com.example.domain.entity.user.QRole;
import com.example.domain.entity.user.Role;
import com.example.usercenter.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zxn on 2018/2/24.
 */
@RestController
@RequestMapping(value = "/role")
public class RoleController extends BaseRestController<Role, QRole>{
    @Autowired
    private RoleService roleService;
    @Override
    protected BaseService<Role, QRole> getService() {
        return roleService;
    }
}
