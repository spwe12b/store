package com.me.store.service;

import com.me.store.common.ServerResponse;
import com.me.store.pojo.Category;

import java.util.List;

public interface ICategoryService {
    ServerResponse<String> addCategory(Integer parentId, String categoryName);
    ServerResponse<String> setCategoryName(Integer categoryId,String categoryName);
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
    ServerResponse<List<Integer>> getChildrenDeepCategory(Integer categoryId);
}
