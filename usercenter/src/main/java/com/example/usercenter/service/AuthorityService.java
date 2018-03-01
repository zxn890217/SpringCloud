package com.example.usercenter.service;

import com.example.domain.base.BaseDao;
import com.example.domain.base.BaseService;
import com.example.domain.entity.user.Authority;
import com.example.domain.entity.user.QAuthority;
import com.example.usercenter.dao.AuthorityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZXN on 2018/2/21.
 */
@Service
public class AuthorityService extends BaseService<Authority, QAuthority> {
    @Autowired
    private AuthorityDao authorityDao;
    @Override
    protected BaseDao<Authority, QAuthority> getDao() {
        return authorityDao;
    }

    @Override
    public boolean insert(Authority entity) {
        if(authorityDao.insert(entity)>0){
            if(entity.getResources()!=null && entity.getResources().size()>0)
                authorityDao.saveAuthorityResource(entity);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Authority entity) {
        authorityDao.deleteAuthorityResource(entity.getId());
        if(entity.getResources()!=null && entity.getResources().size()>0)
            authorityDao.saveAuthorityResource(entity);
        return super.update(entity);
    }

    @Override
    public boolean sensitiveUpdate(Authority entity) {
        return super.sensitiveUpdate(entity);
    }

    @Override
    public boolean delete(Long id) {
        authorityDao.deleteAuthorityResource(id);
        return super.delete(id);
    }
}
