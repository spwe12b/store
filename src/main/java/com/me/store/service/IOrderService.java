package com.me.store.service;

import com.github.pagehelper.PageInfo;
import com.me.store.common.ServerResponse;

import java.util.Map;

public interface IOrderService {
    ServerResponse pay(Long orderNo, Integer userId, String path);
    ServerResponse alipayCallback(Map<String,String> params);
    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);
    ServerResponse createOrder(Integer userId,Integer shippingId);
    ServerResponse<String> cancel(Integer userId,Long orderNo);
    ServerResponse getOrderCartProduct(Integer userId);
    ServerResponse getOrderDetail(Integer userId,Long orderNo);
    ServerResponse<PageInfo> getOrderVoList(Integer userId, int pageNum, int pageSize);
    ServerResponse manageList(int pageNum,int pageSize);
    ServerResponse manageDetail(Long orderNo);
    ServerResponse manageSendGoods(Long orderNo);

    void closeOrder(int hour);
}
