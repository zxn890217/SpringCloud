package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by zxn on 2017/11/22.
 */


/**
 * 类描述：redis操作工具类
 */
@Component
public class RedisCache {
    private static StringRedisTemplate stringRedisTemplate;

    private static RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        RedisCache.stringRedisTemplate = stringRedisTemplate;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        RedisCache.redisTemplate = redisTemplate;
    }

    // 将Object对象以key的形式保存到redis中
    public static void setObject(String key,Object object){
        redisTemplate.opsForValue().set(key,object);
    }

    // 以key的形式从redis中获取Object数据
    public static Object getObject(String key){
        return  Optional.ofNullable(redisTemplate.opsForValue().get(key)).orElse(null);
    }

    // 将String对象以key的形式保存到redis中
    public static void setString(String key,String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }

    // 以key的形式从redis中获取String数据
    public static String getString(String key){
        return Optional.ofNullable(stringRedisTemplate.opsForValue().get(key)).orElse("");
    }

    public static void setString(String key,String value, long timeout){
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    // 将Object对象以key的形式保存到redis中
    public static void setObject(String key, Object object, long timeout){
        redisTemplate.opsForValue().set(key, object, timeout, TimeUnit.SECONDS);
    }
}
