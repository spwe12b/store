package com.me.store.controller.backend;

import com.me.store.common.Const;
import com.me.store.common.RedisShardedPool;
import com.me.store.common.ServerResponse;
import com.me.store.pojo.User;
import com.me.store.service.IUserService;
import com.me.store.util.CookieUtil;
import com.me.store.util.JsonUtil;
import com.me.store.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/user/")
public class UserManageController {
    @Autowired
    private IUserService iUserService;
    @RequestMapping(value = "login.do" ,method = RequestMethod.POST)
    public ServerResponse login(String username, String password, HttpServletRequest request,
                                HttpServletResponse response, HttpSession session){
        ServerResponse<User> serverResponse=iUserService.login(username,password);
        if(serverResponse.isSuccess()){
             User user=serverResponse.getData();
             if(user.getRole().intValue()== Const.Role.ROLE_ADMIN){
                   CookieUtil.writeLoginToken(response,session.getId());
                 RedisShardedPoolUtil.setEx(session.getId(),JsonUtil.obj2String(user),Const.RedisCacheExtime.REDIS_SESSION_TIME);
                 return serverResponse;
             }else{
                  return ServerResponse.createByErrorMessage("不是管理员，无法登录");
             }
        }
        return serverResponse;
    }
}
