package com.me.store.dao;

import com.me.store.pojo.Cart;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectByUserIdProductId(Integer userId,Integer productId);

    List<Cart> selectByUserId(Integer userId);

    int deleteByUserIdProductId(Integer userId,List<String> productIdList);

    int checkOrUnCheck(Integer userId,Integer productId,Integer checked);

    int getProductCountByUserId(Integer userId);

    int isAllChecked(Integer userId);

    List<Cart> selectCheckedCartByUserId(Integer userId);
}