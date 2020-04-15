package com.me.store.controller.common.interceptor;

import com.me.store.common.Const;
import com.me.store.common.ServerResponse;
import com.me.store.pojo.User;
import com.me.store.util.CookieUtil;
import com.me.store.util.JsonUtil;
import com.me.store.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component

public class AuthorityInterceptor implements HandlerInterceptor {
    public static void main(String[] args) {

    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String loginToken=CookieUtil.readLoginToken(request);
        User user=null;
        if(StringUtils.isNotEmpty(loginToken)){
         String userJsonStr= RedisShardedPoolUtil.get(loginToken);
         user= JsonUtil.string2Obj(userJsonStr,User.class);
        }
        if(user==null||user.getRole().intValue()!= Const.Role.ROLE_ADMIN){
           response.reset();
           response.setCharacterEncoding("UTF-8");
           response.setContentType("application/json;charset=UTF-8");
            PrintWriter out=response.getWriter();
            if(user==null){
                out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截，用户未登录")));
            }else{
                out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("用户不是管理员")));
            }
            out.flush();
            out.close();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
