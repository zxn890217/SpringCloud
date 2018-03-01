package com.example.usercenter.dao;

import com.example.domain.base.BaseDao;
import com.example.domain.entity.user.QUser;
import com.example.domain.entity.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by zxn on 2017/10/23.
 */
public interface UserDao extends BaseDao<User, QUser> {
    /**
     * 根据用户名和密码获取用户
     * */
    public User loadUser(@Param("username")String username, @Param("password")String password);
    /**
     * 保存用户角色关系
     * */
    public int saveUserRole(User entity);
    /**
     * 根据用户ID删除用户角色关系
     * */
    public int deleteUserRole(long id);
}
