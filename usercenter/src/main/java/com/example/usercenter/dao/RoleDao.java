package com.example.usercenter.dao;

import com.example.domain.base.BaseDao;
import com.example.domain.entity.user.QRole;
import com.example.domain.entity.user.Role;

/**
 * Created by ZXN on 2018/2/23.
 */
public interface RoleDao extends BaseDao<Role, QRole> {
    /**
     * 保存角色资源关系
     * */
    public int saveRoleAuthority(Role entity);
    /**
     * 根据角色ID删除角色资源关系
     * */
    public int deleteRoleAuthority(long id);
}
