package com.example.usercenter.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zxn on 2017/10/23.
 */
@Configuration
@MapperScan("com.example.usercenter.dao")
public class MybatisConfig {

}
