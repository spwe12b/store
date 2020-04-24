package com.me.store.controller.portal;


import com.me.store.common.ServerResponse;
import com.me.store.pojo.Shipping;
import com.me.store.pojo.User;
import com.me.store.service.IShippingService;
import com.me.store.util.CookieUtil;
import com.me.store.util.JsonUtil;
import com.me.store.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shipping/")
public class ShippingController {
    @Autowired
    public IShippingService iShippingService;
    @RequestMapping(value = "add.do",method = RequestMethod.POST)
    public ServerResponse add(HttpServletRequest request, Shipping shipping){
        String loginToken= CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user= JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
          return iShippingService.add(user.getId(),shipping);
    }
    @RequestMapping(value = "delete.do",method = RequestMethod.POST)
    public ServerResponse add(HttpServletRequest request,Integer shippingId){
        String loginToken= CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user= JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iShippingService.delete(user.getId(),shippingId);
    }
    @RequestMapping(value = "update.do",method = RequestMethod.POST)
    public ServerResponse update(HttpServletRequest request,Shipping shipping){
        String loginToken= CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user= JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iShippingService.update(user.getId(),shipping);
    }
    @RequestMapping(value = "select.do",method = RequestMethod.POST)
    public ServerResponse select(HttpServletRequest request,Integer shippingId){
        String loginToken= CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user= JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iShippingService.select(user.getId(),shippingId);
    }
    @RequestMapping(value = "list.do",method = RequestMethod.POST)
    public ServerResponse list(HttpServletRequest request,
                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        String loginToken= CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user= JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iShippingService.list(user.getId(),pageNum,pageSize);
    }
}
