package com.example.usercenter.service;

import com.example.usercenter.common.base.dao.BaseDao;
import com.example.usercenter.common.base.service.BaseService;
import com.example.usercenter.dao.UserDao;
import com.example.usercenter.model.QUser;
import com.example.usercenter.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zxn on 2017/10/23.
 */
@Service
public class UserService extends BaseService<User, QUser>{
    @Autowired
    private UserDao userDao;

    @Override
    protected BaseDao<User, QUser> getDao() {
        return userDao;
    }
}
