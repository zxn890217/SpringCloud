package com.example.demo.shiro;

import com.example.demo.entity.User;
import com.example.demo.util.RedisCache;
import org.springframework.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zxn on 2017/11/22.
 */
public class StatelessAuthcFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String clientDigest = httpRequest.getHeader("token");
        if(!StringUtils.isEmpty(clientDigest)){
            Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());
            StatelessToken token = new StatelessToken(params, clientDigest);
            try {
                //委托给Realm进行登录
                getSubject(request, response).login(token);
            } catch (Exception e) {
                e.printStackTrace();
                onLoginFail(response); //登录失败
                return false;
            }
        }
        return false;
    }

    //登录失败时默认返回401状态码
    private void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("login error");
    }
}
