package com.example.usercenter.service;

import com.example.domain.base.BaseDao;
import com.example.domain.base.BaseService;
import com.example.domain.entity.user.QUser;
import com.example.domain.entity.user.User;
import com.example.usercenter.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zxn on 2017/10/23.
 */
@Service
public class UserService extends BaseService<User, QUser> {
    @Autowired
    private UserDao userDao;

    @Override
    protected BaseDao<User, QUser> getDao() {
        return userDao;
    }

    public User loadUser(String username, String password){
        return userDao.loadUser(username, password);
    }
}
