package com.example.usercenter.service;

import com.example.domain.base.BaseDao;
import com.example.domain.base.BaseService;
import com.example.domain.entity.user.Authority;
import com.example.domain.entity.user.QAuthority;
import com.example.usercenter.dao.AuthorityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZXN on 2018/2/21.
 */
@Service
public class AuthorityService extends BaseService<Authority, QAuthority> {
    @Autowired
    private AuthorityDao authorityDao;
    @Override
    protected BaseDao<Authority, QAuthority> getDao() {
        return authorityDao;
    }
}
