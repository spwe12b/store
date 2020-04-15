package com.me.store.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.me.store.common.ServerResponse;
import com.me.store.dao.CategoryMapper;
import com.me.store.pojo.Category;
import com.me.store.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;


@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {
    private static final Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加分类
     * @param parentId
     * @param categoryName
     * @return
     */
    public ServerResponse<String> addCategory(Integer parentId, String categoryName){
        if(parentId==null|| StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Category category=new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);
        int rowCount=categoryMapper.insert(category);
        if(rowCount>0){
            return ServerResponse.createBySuccessMessage("添加分类成功");
        }
        return ServerResponse.createByErrorMessage("添加分类失败");
    }

    /**
     * 更改分类名字
     * @param categoryId
     * @param categoryName
     * @return
     */
    public  ServerResponse<String> setCategoryName(Integer categoryId,String categoryName){
        if(categoryId==null||StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Category category=new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        int rowCount=categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount>0){
            return ServerResponse.createBySuccessMessage("更新分类名字成功");
        }
        return ServerResponse.createByErrorMessage("更新分类名字失败");
    }

    /**
     *查询该分类ID下所有平级子分类
     * @param categoryId
     * @return
     */
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId){
        List<Category> categoryList=categoryMapper.selectChildrenCategoryById(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);

    }

    /**
     * 返回该节点下所有的子节点（递归）的id集合
     * @param categoryId
     * @return
     */
    public ServerResponse<List<Integer>> getChildrenDeepCategory(Integer categoryId){
        Set<Category> categorySet= Sets.newHashSet();
        findChildCategory(categorySet,categoryId);
        List<Integer> idList= Lists.newArrayList();
        for(Category category:categorySet){
            idList.add(category.getId());
        }
        return ServerResponse.createBySuccess(idList);

    }

    /**
     * 递归函数，查询子节点
     * @param categorySet
     * @param categoryId
     * @return
     */
    private Set<Category> findChildCategory(Set<Category> categorySet,Integer categoryId){
        Category category=categoryMapper.selectByPrimaryKey(categoryId);
        if(category!=null){
            categorySet.add(category);
        }
        List<Category> categoryList=categoryMapper.selectChildrenCategoryById(categoryId);
        for(Category category1:categoryList){
            findChildCategory(categorySet,category1.getId());
        }
        return categorySet;
    }
}
