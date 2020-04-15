package com.me.store.service;

import com.me.store.common.ServerResponse;
import com.me.store.pojo.Product;

public interface IProductService {
    ServerResponse SaveOrUpdateProduct(Product product);
    ServerResponse setSaleStatus(Integer productId,Integer status);
    ServerResponse getManageDetailProduct(Integer productId);
    ServerResponse selectList(Integer pageNum,Integer pageSize);
    ServerResponse searchByNameAndId(String productName,Integer productId,Integer pageNum,Integer pageSize);
    ServerResponse getProductDeatil(Integer productId);
    ServerResponse getProductByKeywordCategory(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy);
}
