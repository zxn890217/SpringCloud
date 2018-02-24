package com.example.usercenter.service;

import com.example.domain.base.BaseDao;
import com.example.domain.base.BaseService;
import com.example.domain.entity.user.Menu;
import com.example.domain.entity.user.QMenu;
import com.example.usercenter.dao.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zxn on 2018/2/24.
 */
@Service
public class MenuService extends BaseService<Menu, QMenu> {
    @Autowired
    private MenuDao menuDao;
    @Override
    protected BaseDao<Menu, QMenu> getDao() {
        return menuDao;
    }

    public List<Menu> getMenuTree(QMenu query){
        return menuDao.getMenuTree(query);
    }
}
