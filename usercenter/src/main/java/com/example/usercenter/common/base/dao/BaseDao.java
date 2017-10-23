package com.example.usercenter.common.base.dao;

import com.example.usercenter.common.base.model.QueryModel;

import java.util.List;

/**
 * Created by zxn on 2017/10/23.
 */
public interface BaseDao<T, Q extends QueryModel> {
    /**
     * 分页列表查询
     * */
    public List<T> findByPage(Q queryModel);
    /**
     * 统计
     * */
    public long count(Q queryModel);
    /**
     * 列表查询
     * */
    public List<T> query(Q queryModel);
}
