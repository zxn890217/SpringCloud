package com.example.feign.service;

import org.springframework.stereotype.Component;

/**
 * Created by zxn on 2017/10/30.
 */
@Component
public class SchedualServiceHiHystric implements UserService{
    @Override
    public String query() {
        return "对不起，服务器被外星人劫持去火星了";
    }
}
