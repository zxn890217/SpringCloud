package com.example.usercenter.controller;

import com.example.domain.base.BaseRestController;
import com.example.domain.base.BaseService;
import com.example.domain.entity.user.Menu;
import com.example.domain.entity.user.QMenu;
import com.example.usercenter.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zxn on 2018/2/27.
 */
@RestController
@RequestMapping(value="/menu")
public class MenuController extends BaseRestController<Menu, QMenu> {
    @Autowired
    private MenuService menuService;

    @Override
    protected BaseService<Menu, QMenu> getService() {
        return menuService;
    }

    @GetMapping(value = "/query")
    public List<Menu> query(QMenu query){
        return menuService.query(query);
    }
}
