package com.me.store.controller.common.filter;

import com.me.store.common.Const;
import com.me.store.pojo.User;
import com.me.store.util.CookieUtil;
import com.me.store.util.JsonUtil;
import com.me.store.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter(filterName = "sessionExpireFilter",urlPatterns = "/*")
public class SessionExpireFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;
        String loginToken=CookieUtil.readLoginToken(httpServletRequest);
        System.out.println("监听器启动");
        if(StringUtils.isNotEmpty(loginToken)) {
            String userJsonStr = RedisShardedPoolUtil.get(loginToken);
            User user= JsonUtil.string2Obj(userJsonStr,User.class);
            if(user!=null){
                RedisShardedPoolUtil.expire(loginToken, Const.RedisCacheExtime.REDIS_SESSION_TIME);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
