package com.example.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Created by zxn on 2017/10/30.
 */
@Component
public class MyFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(MyFilter.class);
    /**
     * Filter Type
     * */
    /**路由之前*/
    private static final String PRE = "pre";
    /**路由之时*/
    private static final String ROUTING = "routing";
    /**路由之后*/
    private static final String POST = "post";
    /**发送错误调用*/
    private static final String ERROR = "error";
    /**过滤的顺序*/
    private static final String FILTERORDER = "filterOrder";
    /**这里可以写逻辑判断，是否要过滤，本文true,永远过滤*/
    private static final String SHOULDFILTER = "shouldFilter";
    /**过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问*/
    private static final String RUN = "run";

    @Override
    public String filterType() {
        return PRE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        /*log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getHeader("token");
        if(accessToken == null) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            }catch (Exception e){}

            return null;
        }*/
        log.info("ok");
        return null;
    }
}
