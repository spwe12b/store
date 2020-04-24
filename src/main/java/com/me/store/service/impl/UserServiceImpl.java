package com.me.store.service.impl;

import com.me.store.common.Const;
import com.me.store.common.ServerResponse;
import com.me.store.dao.UserMapper;
import com.me.store.pojo.User;
import com.me.store.service.IUserService;
import com.me.store.util.MD5Util;
import com.me.store.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements IUserService {
    private Logger log= LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;

    public ServerResponse<User> login(String username, String password){
        int resultCount=userMapper.checkUsername(username);
        if(resultCount==0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
          String md5Password= MD5Util.MD5EncodeUtf8(password);
          User user=userMapper.selectLogin(username,md5Password);
          if(user==null){
             return ServerResponse.createByErrorMessage("用户名和密码不匹配");
          }
          user.setPassword(Const.EMPTY);
        return ServerResponse.createBySuccess(user,"登陆成功");

    }
    public ServerResponse register(User user){
        int resultCount=userMapper.checkUsername(user.getUsername());
        if(resultCount>0){
            return ServerResponse.createByErrorMessage("用户名已存在");
        }
         resultCount=userMapper.checkEmail(user.getEmail());
        if(resultCount>0){
            return ServerResponse.createByErrorMessage("邮箱已存在");
        }
        resultCount=userMapper.checkPhone(user.getPhone());
        if(resultCount>0){
            return ServerResponse.createByErrorMessage("电话已存在");
        }
         user.setRole(Const.Role.ROLE_CUSTOMER);
         user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
         resultCount=userMapper.insert(user);
         if(resultCount==0){
             return ServerResponse.createByErrorMessage("注册失败");
         }
         return ServerResponse.createBySuccessMessage("注册成功");
    }
    public ServerResponse checkValid(String str,String type){
        if(type.equals(Const.EMAIL)||type.equals(Const.PHONE)||type.equals(Const.USERNAME)){
        switch (type){
            case Const.EMAIL:
                 int resultCount=userMapper.checkEmail(str);
                if(resultCount>0){
                    return ServerResponse.createByErrorMessage("邮箱已存在");
                }
                break;
            case Const.PHONE:
                resultCount=userMapper.checkPhone(str);
                if(resultCount>0){
                    return ServerResponse.createByErrorMessage("电话已存在");
                }
                break;
            case Const.USERNAME:
                resultCount=userMapper.checkUsername(str);
                if(resultCount>0){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
                break;
        }
        return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("参数错误");
    }
    public ServerResponse selectQuestion(String username){
        String question=userMapper.selectQuestion(username);
        if(question==null||Const.EMPTY.equals(question)){
            return ServerResponse.createByErrorMessage("该用户没有设置问题");
        }
        return ServerResponse.createBySuccess(question);
    }
    public ServerResponse checkAnswer(String username,String question,String answer){
        int resultCount=userMapper.checkAnswer(username,question,answer);
        if(resultCount>0){
            String forgetToken= UUID.randomUUID().toString();
            RedisShardedPoolUtil.setEx(Const.TOKEN_PREFIX+username,forgetToken,60*60);
            return ServerResponse.createBySuccess(forgetToken);
        }
           return ServerResponse.createByErrorMessage("用户答案出错");
    }
    public ServerResponse checkAdmin(User user){
        if(Const.Role.ROLE_ADMIN==user.getRole()){
            return ServerResponse.createBySuccessMessage("该用户是管理员");
        }
        return ServerResponse.createByErrorMessage("该用户不是管理员");
    }
    public ServerResponse forgetResetPassword(String username,String passwordNew,String forgetToken){
        String token= RedisShardedPoolUtil.get(Const.TOKEN_PREFIX+username);
        if(StringUtils.equals(token,forgetToken)){
            String md5Password=MD5Util.MD5EncodeUtf8(passwordNew);
            int resultCount=userMapper.updatePasswordByUsername(username,md5Password);
            if(resultCount>0){
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
            return ServerResponse.createByErrorMessage("修改密码失败");
        }
        return ServerResponse.createByErrorMessage("token不匹配");
    }
    public ServerResponse resetPassword(String username,String password,String passwordNew){
        int resultCount=userMapper.checkPassword(username,MD5Util.MD5EncodeUtf8(password));
        if(resultCount<=0){
            return ServerResponse.createByErrorMessage("用户名和密码不匹配");
        }
        String md5Password=MD5Util.MD5EncodeUtf8(passwordNew);
        resultCount=userMapper.updatePasswordByUsername(username,md5Password);
        if(resultCount>0){
            return ServerResponse.createBySuccessMessage("重置密码成功");
        }
        return ServerResponse.createByErrorMessage("重置密码失败");
    }
    public ServerResponse<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);

    }


}
