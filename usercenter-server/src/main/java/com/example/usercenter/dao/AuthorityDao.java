package com.example.usercenter.dao;

import com.example.domain.base.BaseDao;
import com.example.domain.entity.user.Authority;
import com.example.domain.entity.user.QAuthority;

/**
 * Created by ZXN on 2018/2/21.
 */
public interface AuthorityDao extends BaseDao<Authority, QAuthority> {
    /**
     * 保存权限资源关系
     * */
    public int saveAuthorityResource(Authority entity);
    /**
     * 删除权限资源关系
     * */
    public int deleteAuthorityResource(long id);
}
