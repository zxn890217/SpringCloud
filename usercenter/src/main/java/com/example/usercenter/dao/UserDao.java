package com.example.usercenter.dao;

import com.example.usercenter.common.base.dao.BaseDao;
import com.example.usercenter.model.QUser;
import com.example.usercenter.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by zxn on 2017/10/23.
 */
public interface UserDao extends BaseDao<User, QUser> {

}
