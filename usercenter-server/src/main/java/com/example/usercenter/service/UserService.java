package com.example.usercenter.service;

import com.example.domain.base.BaseDao;
import com.example.domain.base.BaseService;
import com.example.domain.entity.user.QUser;
import com.example.domain.entity.user.User;
import com.example.usercenter.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public boolean insert(User entity) {
        if(userDao.insert(entity)>0){
            if(entity.getRoles()!=null && entity.getRoles().size()>0)
                userDao.saveUserRole(entity);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(User entity) {
        userDao.deleteUserRole(entity.getId());
        if(entity.getRoles()!=null && entity.getRoles().size()>0)
            userDao.saveUserRole(entity);
        return super.update(entity);
    }

    @Override
    public boolean delete(Long id) {
        userDao.deleteUserRole(id);
        return super.delete(id);
    }

    @Transactional
    public boolean resetPassword(User entity){

        return userDao.updatePassword(entity)>0;
    }
}
