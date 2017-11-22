package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zxn on 2017/11/22.
 */
public class User implements Serializable{
    String token;
    String name;
    String password;
    String sex;
    String phoneNum;
    List<String> authorities;

    public User(){}

    public User(String token, String name, String password, String sex, String phoneNum, List<String> authorities) {
        this.token = token;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.phoneNum = phoneNum;
        this.authorities = authorities;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
