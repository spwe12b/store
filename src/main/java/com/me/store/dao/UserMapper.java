package com.me.store.dao;

import com.me.store.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    User selectLogin(@Param("username") String username,@Param("password") String password);

    int checkEmail(String email);

    int checkPhone(String phone);

    String selectQuestion(String username);

    int checkAnswer(@Param("username")String username,@Param("question")String question,
                    @Param("answer")String answer);

    int updatePasswordByUsername(String userName,String password);

    int checkPassword(String username,String password);


}