package com.me.store.service;

import com.me.store.common.ServerResponse;
import com.me.store.pojo.User;

public interface IUserService {
    ServerResponse login(String username,String password);
    ServerResponse register(User user);
    ServerResponse checkValid(String str,String type);
    ServerResponse selectQuestion(String username);
    ServerResponse checkAnswer(String username,String question,String answer);
    ServerResponse checkAdmin(User user);
    ServerResponse forgetResetPassword(String username,String passwordNew,String forgetToken);
    ServerResponse resetPassword(String username,String password,String passwordNew);
    ServerResponse getInformation(Integer userId);

}
