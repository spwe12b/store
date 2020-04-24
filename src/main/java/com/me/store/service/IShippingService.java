package com.me.store.service;

import com.me.store.common.ServerResponse;
import com.me.store.pojo.Shipping;

public interface IShippingService {
    ServerResponse add(Integer userId, Shipping shipping);
    ServerResponse delete(Integer userId,Integer shippingId);
    ServerResponse update(Integer userId,Shipping shipping);
    ServerResponse select(Integer userId,Integer shippingId);
    ServerResponse list(Integer userId, int pageNum, int pageSize);
}
