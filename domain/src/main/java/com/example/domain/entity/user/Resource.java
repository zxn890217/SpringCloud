package com.example.domain.entity.user;

import java.io.Serializable;

/**
 * Created by ZXN on 2018/2/21.
 */
public class Resource implements Serializable {
    //资源ID
    private Long id;
    //类型（1：一般资源路径；2：无权限控制路径；3：登录权限控制路径；）
    private Integer type;
    //资源内容
    private String content;
    //资源可用状态
    private Boolean enable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
