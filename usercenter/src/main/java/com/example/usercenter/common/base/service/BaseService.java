package com.example.usercenter.common.base.service;

import com.example.usercenter.common.base.dao.BaseDao;
import com.example.usercenter.common.base.model.Page;
import com.example.usercenter.common.base.model.QueryModel;
import com.example.usercenter.common.base.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 基础Service
 * Created by zxn on 2017/10/23.
 */
public abstract class BaseService<T, Q extends QueryModel> {
    //实体对象类型
    private Class<T> entityClass;
    @Autowired
    protected BaseRepository repository;

    protected abstract BaseDao<T, Q> getDao();

    /**
     * 获取实体对象类型
     * */
    protected void getEntityClass(){
        if(entityClass==null){
            Type genType = getClass().getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            entityClass =  (Class)params[0];
        }
    }

    /**
     * 根据主键值获取对象
     * @param id
     * */
    @Transactional
    public T get(Object id){
        getEntityClass();
        return repository.getById(entityClass, id);
    }

    /**
     * 保存
     * @param entity
     * */
    @Transactional
    public void save(T entity){
        repository.save(entity);
    }

    /**
     * 修改
     * @param entity
     * */
    @Transactional
    public void update(T entity){
        repository.update(entity);
    }

    /**
     * 删除
     * @param id
     * */
    @Transactional
    public void delete(Object id){
        getEntityClass();
        repository.delete(entityClass, id);
    }

    /**
     * 批量删除
     * @param ids
     * */
    @Transactional
    public void delete(Object[] ids){
        getEntityClass();
        repository.delete(entityClass, ids);
    }

    /**
     * 分页查询
     * @param queryModel 查询条件
     *  */
    public Page findByPage(Q queryModel){
        List<T> list =  getDao().findByPage(queryModel);
        long count = getDao().count(queryModel);
        return new Page(list, count);
    }

    /**
     * 统计
     * @param queryModel 查询条件
     * @return int
     * */
    public long count(Q queryModel){
        return getDao().count(queryModel);
    }

    /**
     * 查询
     * @param queryModel 查询条件
     *  */
    public List<T> query(Q queryModel){
        return getDao().query(queryModel);
    }
}
