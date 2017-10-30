package com.example.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zxn on 2017/10/30.
 */
@Service
public class UserService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "queryError")
    public String query() {
        return restTemplate.getForObject("http://usercenter/user", String.class);
    }

    public String queryError() {
        return "对不起，服务器被外星人劫持到火星上去了";
    }
}
