package com.example.demo.shiro;

import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

/**
 * Created by zxn on 2017/11/23.
 */
public class StatelessToken implements AuthenticationToken {
    private Map<String, ?> params;
    private String clientDigest;

    public StatelessToken(Map<String, ?> params, String clientDigest) {
        this.params = params;
        this.clientDigest = clientDigest;
    }

    public  Map<String, ?> getParams() {
        return params;
    }

    public void setParams( Map<String, ?> params) {
        this.params = params;
    }

    public String getClientDigest() {
        return clientDigest;
    }

    public void setClientDigest(String clientDigest) {
        this.clientDigest = clientDigest;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return clientDigest;
    }
}
