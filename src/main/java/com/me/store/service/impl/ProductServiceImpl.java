package com.me.store.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.me.store.common.Const;
import com.me.store.common.ServerResponse;
import com.me.store.dao.CategoryMapper;
import com.me.store.dao.ProductMapper;
import com.me.store.pojo.Category;
import com.me.store.pojo.Product;
import com.me.store.service.ICategoryService;
import com.me.store.service.IProductService;
import com.me.store.util.DateTimeUtil;
import com.me.store.util.PropertiesUtil;
import com.me.store.vo.ProductDetailVo;
import com.me.store.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iProductService")
public class ProductServiceImpl implements IProductService {
    @Autowired
    public ICategoryService iCategoryService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * 如果传进来的product的ID为空则为新增，如果有ID则为更新
     * @param product
     * @return
     */
    public ServerResponse SaveOrUpdateProduct(Product product){
        if(product!=null){
            //取产品子图中的第一个图片为主图
            if(StringUtils.isNotBlank(product.getSubImages())){
                //图片地址之间用逗号分开
                String images[]=product.getSubImages().split(",");
                product.setMainImage(images[0]);
            }
            if(product.getId()==null){
                //新增产品
                int resultCount=productMapper.insert(product);
                if(resultCount>0){
                    return ServerResponse.createBySuccessMessage("产品新增成功");
                }
                return ServerResponse.createByErrorMessage("产品新增失败");
            }else {
                //更新产品
                int resultCount=productMapper.updateByPrimaryKey(product);
                if(resultCount>0){
                    return ServerResponse.createBySuccessMessage("更新产品成功");
                }
                    return ServerResponse.createByErrorMessage("更新产品失败");
            }
        }
        return ServerResponse.createBySuccessMessage("参数错误");
    }
    public ServerResponse setSaleStatus(Integer productId,Integer status){
        if(productId==null||status==null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
         Product product=new Product();
        product.setId(productId);
        product.setStatus(status);
        int resultCount=productMapper.updateByPrimaryKeySelective(product);
        if(resultCount>0){
            return ServerResponse.createBySuccessMessage("更新产品销售状态成功");
        }
        return ServerResponse.createByErrorMessage("更新产品销售状态失败");
    }
    public ServerResponse getManageDetailProduct(Integer productId){
        if(productId==null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Product product=productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.createByErrorMessage("产品不存在");
        }
                ProductDetailVo productDetailVo=assembleProductDetailVo(product);
                return ServerResponse.createBySuccess(productDetailVo);
    }

    public ServerResponse selectList(Integer pageNum,Integer pageSize){
        //开始分页的配置
          PageHelper.startPage(pageNum,pageSize);
          List<Product> productList=productMapper.selectList();
          List<ProductListVo> productListVoList= Lists.newArrayList();
          //如果是LinkedList就不要用for循环，效率很低，而用iterator
          for(Product product:productList){
              ProductListVo productListVo=assembleProductListVo(product);
              productListVoList.add(productListVo);
          }
          PageInfo pageInfo=new PageInfo<>(productList);
          pageInfo.setList(productListVoList);
          return ServerResponse.createBySuccess(pageInfo);
    }
    public ServerResponse searchByNameAndId(String productName,Integer productId,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        productName=new StringBuilder().append("%").append(productName).append("%").toString();
        List<Product> productList=productMapper.searchByNameAndId(productName,productId);
        List<ProductListVo> productListVoList= Lists.newArrayList();
        //如果是LinkedList就不要用for循环，效率很低，而用iterator
        for(Product product:productList){
            ProductListVo productListVo=assembleProductListVo(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo=new PageInfo<>(productListVoList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse getProductDeatil(Integer productId){
            if(productId==null){
                return ServerResponse.createByErrorMessage("参数错误");
            }
            Product product=productMapper.selectByPrimaryKey(productId);
            if(product==null){
                return ServerResponse.createByErrorMessage("产品不存在");
            }
            if(!(product.getStatus()== Const.ProductStatus.ON_SALE)){
                return ServerResponse.createByErrorMessage("商品已下架或删除");
            }
            ProductDetailVo productDetailVo=assembleProductDetailVo(product);
            return ServerResponse.createBySuccess(productDetailVo);
        }

    /**
     * 通过关键字和分类id查询商品,通过orderBy排序
     * @param keyword
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    public ServerResponse getProductByKeywordCategory(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy){
        if(StringUtils.isBlank(keyword)&&categoryId==null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        List<Integer> categoryIdList=Lists.newArrayList();
        if(categoryId!=null){
            Category category=categoryMapper.selectByPrimaryKey(categoryId);
            if(category==null&&StringUtils.isBlank(keyword)){
                //关键字为空，该分类也不存在，返回一个空集合
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVo> productListVoList=Lists.newArrayList();
                PageInfo resultPage=new PageInfo(productListVoList);
                return ServerResponse.createBySuccess(resultPage);
            }
            //查询该分类下所有的子分类的ID集合
            ServerResponse<List<Integer>> serverResponse=iCategoryService.getChildrenDeepCategory(categoryId);
            categoryIdList=serverResponse.getData();
        }
        if(StringUtils.isNotBlank(keyword)){
            keyword=new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        PageHelper.startPage(pageNum,pageSize);
        //排序处理
        if(StringUtils.isNotBlank(orderBy)){
            if(Const.ORDER_BY_SET.contains(orderBy)){
                String[] orderByArray=orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        List<Product> productList=productMapper.searchByCategoryIdAndName
                (keyword,categoryIdList.size()==0?null:categoryIdList);//防止有keyword但id集合内容为空而查不到商品
        List<ProductListVo> productListVoList=Lists.newArrayList();
        for(Product product:productList){
            productListVoList.add(assembleProductListVo(product));
        }
        PageInfo resultPage=new PageInfo(productList);
        resultPage.setList(productListVoList);
        return ServerResponse.createBySuccess(resultPage);
    }

    /**
     * pojo和vo的转换
     * @param product
     * @return
     */
    private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.store.com/"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }

    /**
     * pojo和vo的转换
     * @param product
     * @return
     */
    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.store.com/"));

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            productDetailVo.setParentCategoryId(0);//默认根节点
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }
}
