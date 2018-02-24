package com.example.usercenter.service;

import com.example.domain.base.BaseDao;
import com.example.domain.base.BaseService;
import com.example.domain.entity.user.QRole;
import com.example.domain.entity.user.Role;
import com.example.usercenter.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zxn on 2018/2/24.
 */
@Service
public class RoleService extends BaseService<Role, QRole> {
    @Autowired
    private RoleDao roleDao;
    @Override
    protected BaseDao<Role, QRole> getDao() {
        return roleDao;
    }
}
