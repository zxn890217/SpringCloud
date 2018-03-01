package com.example.domain.base;

import java.util.List;

/**
 * Created by zxn on 2018/1/11.
 */
public abstract class BaseService<T, Q extends QueryBase> {

    protected abstract BaseDao<T, Q> getDao();

    public T get(Long id){
        return getDao().get(id);
    }

    public boolean insert(T entity){
        return getDao().insert(entity)>0;
    }

    public boolean update(T entity){
        return getDao().update(entity)>0;
    }

    public boolean sensitiveUpdate(T entity){
        return getDao().sensitiveUpdate(entity)>0;
    }

    public boolean delete(Long id){
        return getDao().delete(id)>0;
    }

    public boolean exists(T entity){
        return getDao().exists(entity)>0;
    }

    public Page findByPage(Q query){
        return new Page<T>(getDao().query(query), getDao().count(query));
    }

    public int count(Q query){
        return getDao().count(query);
    }

    public List<T> query(Q query){
        return getDao().query(query);
    }
}
