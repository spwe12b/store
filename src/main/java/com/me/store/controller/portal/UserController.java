package com.me.store.controller.portal;

import com.me.store.common.Const;
import com.me.store.common.ResponseCode;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private IUserService iUserService;
    //用户登录
    @RequestMapping(value = "login.do" ,method = RequestMethod.POST)
    public ServerResponse login(String username, String password, HttpSession session,
                                HttpServletResponse response, HttpServletRequest request){
        ServerResponse<User> serverResponse=iUserService.login(username,password);
        if(serverResponse.isSuccess()){
            CookieUtil.writeLoginToken(response,session.getId());
            RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(serverResponse.getData()),Const.RedisCacheExtime.REDIS_SESSION_TIME);
        }
        return serverResponse;
    }

    /**
     * 用户登出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    public ServerResponse logout(HttpServletRequest request,HttpServletResponse response){
        String loginToken=CookieUtil.readLoginToken(request);
        CookieUtil.delLoginToken(request,response);
        RedisShardedPoolUtil.del(loginToken);
        return ServerResponse.createBySuccessMessage("用户登出成功");
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do" ,method = RequestMethod.POST)
    public ServerResponse register(User user){
        return iUserService.register(user);
    }

    /**
     * 检查用户信息是否冲突
     * @param str   要检查的字段
     * @param type  字段的类型 ：username,email,phone
     * @return
     */
    @RequestMapping(value = "check_valid.do" ,method = RequestMethod.POST)
    public ServerResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }

    /**
     * 获取用户登陆信息
     * @param request
     * @return
     */
    @RequestMapping(value="get_user_info.do",method = RequestMethod.POST)
    public ServerResponse getUserInfo(HttpServletRequest request){
          String loginToken=CookieUtil.readLoginToken(request);
          if(StringUtils.isEmpty(loginToken)){
              return ServerResponse.createByErrorMessage("用户未登录");
          }
          String userJsonStr= RedisShardedPoolUtil.get(loginToken);
          User user=JsonUtil.string2Obj(userJsonStr,User.class);
          if(user==null){
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
        return ServerResponse.createBySuccess(user);
    }

    //获取用户详细信息
    @RequestMapping(value = "get_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> get_information(HttpServletRequest request){
        String loginToken=CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user=JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
        return iUserService.getInformation(user.getId());
    }

    //忘记密码下的获取问题
    @RequestMapping(value ="forget_get_question.do",method = RequestMethod.POST)
    public ServerResponse forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }

    /**
     * 忘记密码下的检查问题答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value ="forget_check_answer.do",method = RequestMethod.POST)
    public ServerResponse forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }

    /**
     * 忘记密码下的重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @RequestMapping(value ="forget_reset_password.do",method = RequestMethod.POST)
    public ServerResponse forgetResetPassword(String username,String passwordNew,String forgetToken){
        return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
    }

    /**
     * 登陆状态下的重置密码
     * @param request
     * @param password
     * @param passwordNew
     * @return
     */
    @RequestMapping(value ="reset_password.do",method = RequestMethod.POST)
    public ServerResponse resetPassword(HttpServletRequest request,String password,String passwordNew){
        String loginToken=CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user=JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(user.getUsername(),password,passwordNew);
    }

    @RequestMapping(value = "update_information.do", method = RequestMethod.POST)
    public ServerResponse<User> update_information(HttpServletRequest request, User user) {
        String loginToken=CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User userLogin=JsonUtil.string2Obj(userJsonStr,User.class);
        if(userLogin==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(userLogin.getId());
        return iUserService.updateInformation(user);
    }
}
