package com.example.domain.entity.user;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zxn on 2018/2/8.
 */
public class Authority implements Serializable {
    //权限ID
    private Long id;
    //权限名称
    private String name;
    //权限代码
    private String code;
    //拥有资源
    private List<Resource> resources;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
