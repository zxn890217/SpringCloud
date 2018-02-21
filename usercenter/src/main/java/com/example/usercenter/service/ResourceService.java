package com.example.usercenter.service;

import com.example.domain.base.BaseDao;
import com.example.domain.base.BaseService;
import com.example.domain.entity.user.QResource;
import com.example.domain.entity.user.Resource;
import com.example.usercenter.dao.ResourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZXN on 2018/2/21.
 */
@Service
public class ResourceService extends BaseService<Resource, QResource> {
    @Autowired
    private ResourceDao resourceDao;
    @Override
    protected BaseDao<Resource, QResource> getDao() {
        return resourceDao;
    }
}
