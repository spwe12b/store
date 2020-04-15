package com.me.store.service;

import com.me.store.common.ServerResponse;

public interface ICartService {
     ServerResponse add(Integer userId,Integer productId,Integer count);
     ServerResponse update(Integer userId,Integer productId,Integer count);
     ServerResponse delete(Integer userId,String productIds);
     ServerResponse checkOrUnCheck(Integer userId,Integer productId,Integer checked);
     ServerResponse getProductCount(Integer userId);
}
