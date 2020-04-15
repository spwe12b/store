package com.me.store.dao;

import com.me.store.pojo.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    List<OrderItem> selectByOrderNoUserId(Long orderNo,Integer userId);

    int batchInsert(List<OrderItem> orderItemList);

    List<OrderItem> selectByOrderNo(Long orderNo);


}