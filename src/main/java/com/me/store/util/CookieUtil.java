package com.me.store.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

    public class CookieUtil {
//        129.28.187.226
    private static final String COOKIE_DOMAIN="129.28.187.226";
    private static final String COOKIE_NAME="store_login_token";
    private static final Logger logger=LoggerFactory.getLogger(CookieUtil.class);
    //删除登陆cookie
    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(StringUtils.equals(cookie.getName(),COOKIE_NAME)){
                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);//代表删除此cookie
                    logger.info("del cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
                    response.addCookie(cookie);
                    return;
                }
            }
        }
    }
    //读取登陆cookie
    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(StringUtils.equals(cookie.getName(),COOKIE_NAME)){
                    logger.info("return cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    //写入登陆cookie
    public static void writeLoginToken(HttpServletResponse response,String token){
        Cookie cookie=new Cookie(COOKIE_NAME,token);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/");//代表设置在根目录 todo 测试
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60*24*365);//如果是-1则代表永久,单位是秒，如果不设置则不会存在硬盘上
        logger.info("write cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
        response.addCookie(cookie);
    }
}
