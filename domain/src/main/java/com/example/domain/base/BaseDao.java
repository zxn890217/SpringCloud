package com.example.domain.base;

import java.util.List;

/**
 * Created by zxn on 2018/1/11.
 */
public interface BaseDao<T, Q extends QueryBase> {
    /**
     * 根据主键获取对象
     * */
    public T get(Long id);
    /**
     * 插入
     * */
    public int insert(T entity);
    /**
     * 根据主键更新
     * */
    public int update(T entity);
    /**
     * 根据主键非空更新
     * */
    public int sensitiveUpdate(T entity);
    /**
     * 根据主键删除
     * */
    public int delete(Long id);
    /**
     * 存在查找
     * */
    public int exists(T entity);
    /**
     * 分页查询列表
     * */
    public List<T> findByPage(Q query);
    /**
     * 统计
     * */
    public int count(Q query);
}
