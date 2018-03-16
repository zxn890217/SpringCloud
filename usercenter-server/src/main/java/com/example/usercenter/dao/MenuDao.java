package com.example.usercenter.dao;

import com.example.domain.base.BaseDao;
import com.example.domain.entity.user.Menu;
import com.example.domain.entity.user.QMenu;

import java.util.List;

/**
 * Created by zxn on 2018/2/24.
 */
public interface MenuDao extends BaseDao<Menu, QMenu>{
    /**
     * 获取不含权限的菜单
     * */
    public Menu getWithoutAuthority(Long id);
    /**
     * 查询菜单树
     * */
    public List<Menu> queryByUserAndFromType(QMenu query);
    /**
     * 根据id获取子级菜单
     * */
    public List<Menu> getChildren(Long id);
}
