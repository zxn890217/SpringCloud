package com.example.domain.entity.user;

import java.util.List;

/**
 * Created by zxn on 2018/2/8.
 */
public class Authority {
    //权限ID
    private Long id;
    //权限名称
    private String name;
    //拥有资源
    private List<Resource> recources;

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

    public List<Resource> getRecources() {
        return recources;
    }

    public void setRecources(List<Resource> recources) {
        this.recources = recources;
    }
}
