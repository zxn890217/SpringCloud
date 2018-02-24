package com.example.usercenter.controller;

import com.example.domain.base.BaseRestController;
import com.example.domain.base.BaseService;
import com.example.domain.entity.user.QResource;
import com.example.domain.entity.user.Resource;
import com.example.usercenter.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ZXN on 2018/2/21.
 */
@RestController
@RequestMapping(value = "/resource")
public class ResourceController extends BaseRestController<Resource, QResource> {
    @Autowired
    private ResourceService resourceService;
    @Override
    protected BaseService<Resource, QResource> getService() {
        return resourceService;
    }
}
