package com.me.store.dao;

import com.me.store.pojo.Product;
import com.me.store.vo.ProductListVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectList();

    List<Product> searchByNameAndId(String productName,Integer productId);

    List<Product> searchByCategoryIdAndName(String keyword,List<Integer> categoryIdList);

    Integer selectStockByProductId(Integer productId);
}