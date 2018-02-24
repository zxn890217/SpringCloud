package com.example.domain.entity.user;

import com.example.domain.base.QueryBase;
import org.apache.ibatis.type.Alias;

/**
 * Created by zxn on 2018/2/8.
 */
public class QRole extends QueryBase {
    //角色ID
    private Long id;
    //角色名称
    private String name;

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
}
