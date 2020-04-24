package com.me.store.dao;

import com.me.store.pojo.Shipping;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleteByUserIdShippingId(Integer userId,Integer shippingId);

    int updateByUserId(Shipping shipping);

    Shipping selectByUserIdShippingId(Integer userId,Integer shippingId);

    List<Shipping> selectByUserId(Integer userId);
}