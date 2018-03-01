package com.example.usercenter.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zxn on 2018/2/28.
 */
@Component
public class GenericDao {
    @Autowired
    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init(){
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    /**
     * 插入
     * @param tableName
     * @param pk 自增主键
     * @param entity
     * @return int
     * */
    public int insert(String tableName, String pk, Object entity){
        String insertSQL = makeInsertSQL(tableName, pk, entity);
        return this.update(insertSQL, entity);
    }

    /**
     * 根据表名批量更新
     * @param tableName
     * @param pk 自增主键
     * @param list
     * @return int[]
     * */
    public int[] batchInsert(String tableName, String pk, List<?> list){
        String insertSQL = makeInsertSQL(tableName, pk, list.get(0));
        return batchUpdate(insertSQL, list);
    }

    /**
     * 生成插入SQL
     * @param tableName
     * @param pk 自增主键
     * @param entity
     * @return String
     * */
    private String makeInsertSQL(String tableName, String pk, Object entity){
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(tableName);
        sb.append(" (");
        Field[] fields = entity.getClass().getDeclaredFields();
        for(Field f : fields){
            if(f.getName().equals(pk))
                continue;
            sb.append(f.getName());
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(") values(");
        for(Field f : fields){
            if(f.getName().equals(pk))
                continue;
            sb.append(":");
            sb.append(f.getName());
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 根据ParameterSQL更新
     * @param sql
     * @param entity
     * @return int
     * @see SQL示例：insert into test(name) values(:name)
     * */
    public int update(String sql, Object entity){
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(entity);
        return jdbcTemplate.update(sql, paramSource);
    }

    /**
     * 根据ParameterSQL更新
     * @param sql
     * @param paramMap
     * @return int
     * @see SQL示例：insert into test(name) values(:name)
     * */
    public int update(String sql, Map<String, Object> paramMap){
        return jdbcTemplate.update(sql, paramMap);
    }
    /**
     * 根据ParameterSQL批量更新
     * @param sql
     * @param list
     * @return int[]
     * @see SQL示例：insert into test(name) values(:name)
     * */
    public int[] batchUpdate(String sql, List<?> list){
        SqlParameterSource[] batchArgs = SqlParameterSourceUtils.createBatch(list.toArray());
        return namedParameterJdbcTemplate.batchUpdate(sql, batchArgs);
    }

    /**
     * 根据ParameterSQL批量更新
     * @param sql
     * @param batchArgs
     * @return int[]
     * @see SQL示例：insert into test(name) values(:name)
     * */
    public int[] batchUpdate(String sql, Map<String, Object>[] batchArgs){
        return namedParameterJdbcTemplate.batchUpdate(sql, batchArgs);
    }
    /**
     * 根据数组id执行批量删除SQL
     * @param sql
     * @param ids
     * @return int[]
     * @see SQL示例：delete from test where id=?
     * */
    public int[] batchDeleteById(String sql, final int[] ids){
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
            @Override
            public int getBatchSize() {
                // TODO Auto-generated method stub
                return ids.length;
            }
            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {
                // TODO Auto-generated method stub
                ps.setInt(1, ids[index]);
            }
        });
    }
    /**
     * 根据ID执行删除SQL
     * @param sql
     * @param id
     * @return int
     * @see SQL示例：delete from test where id=?
     * */
    public int delete(String sql, long id){
        return jdbcTemplate.update(sql, new Object[]{id});
    }

    /**
     * 查询
     * @param sql
     * @param paramMap
     * @param entityClass
     * @return List<?>
     * */
    public List<?> query(String sql, Map<String, Object> paramMap, Class<?> entityClass){
        if(paramMap != null)
            return namedParameterJdbcTemplate.query(sql, paramMap,  new BeanPropertyRowMapper<>(entityClass));
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(entityClass));
    }
    /**
     * 根据ParameterSQL批量保存一对多关系
     * @param sql
     * @param oneKey 一的键
     * @param oneValue 一的值
     * @param manyKey 多的键
     * @param many 多的值
     * @param attrName 多的元素属性名称
     * @return int[]
     * @see SQL示例：insert into test(name) values(:name)
     * */
    @SuppressWarnings("unchecked")
    public void saveManyToOne(String sql, String oneKey, Object oneValue, String manyKey, List<?> many, String attrName){
        if(StringUtils.isNotEmpty(oneKey) && StringUtils.isNotEmpty(manyKey) && many!=null && many.size()>0){
            List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
            Object e = many.get(0);
            if(StringUtils.isEmpty(attrName)){
                for(Object el : many){
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put(oneKey, oneValue);
                    param.put(manyKey, el);
                    params.add(param);
                }
            }
            else{
                String firstLetter = attrName.substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + attrName.substring(1);
                try {
                    Method method = e.getClass().getMethod(getter, new Class[] {});
                    for(Object el : many){
                        Object value = method.invoke(el, new Object[] {});
                        Map<String, Object> param = new HashMap<String, Object>();
                        param.put(oneKey, oneValue);
                        param.put(manyKey, value);
                        params.add(param);
                    }
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            if(params.size()>0){
                Map<String, Object>[] arr = new Map[params.size()];
                params.toArray(arr);
                this.batchUpdate(sql, arr);
                return;
            }
        }
        throw new RuntimeException("未找到可输入参数，批量保存失败");
    }
}
