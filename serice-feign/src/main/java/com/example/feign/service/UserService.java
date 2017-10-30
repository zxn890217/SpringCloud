package com.example.feign.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by zxn on 2017/10/30.
 */
@FeignClient(value = "usercenter",fallback = SchedualServiceHiHystric.class)
public interface UserService {
    @RequestMapping(value="/user", method = RequestMethod.GET)
    String query();
}
