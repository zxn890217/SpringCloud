package com.example.demo.filter;

import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by zxn on 2017/11/22.
 */
public class StatelessAuthcFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}
