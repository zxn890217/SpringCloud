package com.example.usercenter.common.base.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by zxn on 2017/10/23.
 */
@Repository
public class BaseRepository {
    @PersistenceContext
    protected EntityManager em;


    public <T> void delete(Class<T> clazz, Object id) {
        T entity = em.find(clazz, id);
        em.remove(entity);
    }

    public <T> void delete(Class<T> clazz, Object[] ids) {
        T entity = null;
        for(Object id : ids) {
            entity = em.find(clazz, id);
            em.remove(entity);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getAll(Class<T> clazz) {
        String className = clazz.getSimpleName();
        StringBuffer jpql = new StringBuffer("select o from ");
        jpql.append(className).append(" o ");
        return em.createQuery(jpql.toString()).getResultList();
    }

    public <T> T getById(Class<T> clazz, Object id) {
        return em.find(clazz, id);
    }

    public void save(Object entity) {
        em.persist(entity);
    }

    public void update(Object entity) {
        em.merge(entity);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getByJpql(String jpql, Object... objects) {
        Query query = em.createQuery(jpql);
        if(objects != null) {
            if (objects != null) {
                for(int i = 0 ; i < objects.length ; i ++){
                    query.setParameter(i, objects[i]);
                }
            }
        }
        return query.getResultList();
    }

    public int executeJpql(String jpql,Object...objects) {
        Query query = em.createQuery(jpql);
        if (objects != null) {
            for(int i = 0 ; i < objects.length ; i ++){
                query.setParameter(i, objects[i]);
            }
        }
        return query.executeUpdate();
    }

    public Object getUniqueResultByJpql(String jpql, Object... objects) {
        Query query = em.createQuery(jpql);
        if (objects != null) {
            for(int i = 0 ; i < objects.length ; i ++){
                query.setParameter(i, objects[i]);
            }
        }
        return query.getSingleResult();
    }
}
