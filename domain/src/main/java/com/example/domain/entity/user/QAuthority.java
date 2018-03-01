package com.example.domain.entity.user;

import com.example.domain.base.QueryBase;
import org.apache.ibatis.type.Alias;

/**
 * Created by zxn on 2018/2/8.
 */
public class QAuthority extends QueryBase {
    //权限ID
    private Long id;
    //权限名称
    private String name;
    //权限代码
    private String code;

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
}
