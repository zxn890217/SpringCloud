package com.example.usercenter.service;

import com.example.domain.base.BaseDao;
import com.example.domain.base.BaseService;
import com.example.domain.entity.user.Authority;
import com.example.domain.entity.user.Menu;
import com.example.domain.entity.user.QAuthority;
import com.example.domain.entity.user.QMenu;
import com.example.usercenter.dao.AuthorityDao;
import com.example.usercenter.dao.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by zxn on 2018/2/24.
 */
@Service
public class MenuService extends BaseService<Menu, QMenu> {
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private AuthorityDao authorityDao;
    @Override
    protected BaseDao<Menu, QMenu> getDao() {
        return menuDao;
    }

    @Override
    public boolean insert(Menu entity) {
        if(entity.getAuthority()!=null
                && entity.getAuthority().getId()==null
                && !StringUtils.isEmpty(entity.getAuthority().getCode())){
            String code = entity.getAuthority().getCode();
            QAuthority query = new QAuthority();
            query.setCode(code);
            List<Authority> list = authorityDao.query(query);
            if(list!=null && list.size()>0){
                entity.setAuthority(list.get(0));
            }
            else{
                throw new RuntimeException("权限代码不存在");
            }
        }
        if(menuDao.insert(entity)>0){
            if(entity.getParent()!=null && entity.getParent().getId()!=null && entity.getParent().getId()>0){
                Menu parent = menuDao.getWithoutAuthority(entity.getParent().getId());
                entity.setPath(parent.getPath()+entity.getId()+"/");
            }
            else{
                entity.setPath("/"+entity.getId()+"/");
            }
            menuDao.update(entity);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Menu entity) {
        if(entity.getParent()!=null && entity.getParent().getId()!=null && entity.getParent().getId()>0){
            Menu parent = menuDao.getWithoutAuthority(entity.getParent().getId());
            entity.setPath(parent.getPath()+entity.getId()+"/");
        }
        else{
            entity.setPath("/"+entity.getId()+"/");
        }
        if(menuDao.update(entity)>0){
            List<Menu> children = menuDao.getChildren(entity.getId());
            children.forEach(m->{
                Menu p = menuDao.getWithoutAuthority(m.getParent().getId());
                m.setPath(p.getPath()+m.getId()+"/");
                menuDao.update(m);
            });
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if(menuDao.delete(id)>0){
            List<Menu> children = menuDao.getChildren(id);
            children.forEach(m->{
                Menu p = menuDao.getWithoutAuthority(m.getParent().getId());
                if(p!=null){
                    m.setPath(p.getPath()+m.getId()+"/");
                }
                else{
                    m.setParent(null);
                    m.setPath("/"+m.getId()+"/");
                }
                menuDao.update(m);
            });
            return true;
        }
        return false;
    }

    public List<Menu> getMenuTree(QMenu query){
        List<Menu> list = menuDao.queryByUserAndFromType(query);
        Map<Long, Menu> map = new HashMap<Long, Menu>();
        list.forEach(item->{
            map.put(item.getId(), item);
            if(item.getParent().getId()!=null && item.getParent().getId()>0){
                String path = item.getPath();
                String[] arr = path.split("/");
                for(String s : arr){
                    if(!StringUtils.isEmpty(s)){
                        Long k = Long.parseLong(s);
                        Menu v = map.get(k);
                        if(v==null){
                            v = menuDao.getWithoutAuthority(k);
                            if(v!=null)
                                map.put(k, v);
                        }
                    }
                }
            }
        });
        List<Menu> menus = new ArrayList<Menu>();
        menus.addAll(map.values());
        Collections.sort(menus, new Comparator<Menu>(){
            @Override
            public int compare(Menu m1, Menu m2) {
                return m1.getPath().compareTo(m2.getPath());
            }
        });
        List<Menu> tree = new ArrayList<Menu>();
        Iterator<Menu> it = menus.iterator();
        //获取树顶点
        while(it.hasNext()){
            Menu m = it.next();
            if(m.getParent()==null || m.getParent().getId()==null || m.getParent().getId()==0){
                tree.add(m);
                it.remove();
            }
        }
        tree.forEach(item->structureTree(item, menus));
        return tree;
    }

    private void structureTree(Menu parent, List<Menu> menus){
        if(menus.size()==0)
            return;
        Iterator<Menu> it = menus.iterator();
        while(it.hasNext()){
            Menu m = it.next();
            if(m.getParent().getId() == parent.getId()){
                if(parent.getChildren()==null){
                    parent.setChildren(new ArrayList<Menu>());
                }
                parent.getChildren().add(m);
                it.remove();
                structureTree(m, menus);
            }
        }
    }
}
