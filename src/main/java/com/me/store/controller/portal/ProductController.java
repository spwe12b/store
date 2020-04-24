package com.me.store.controller.portal;

import com.github.pagehelper.PageInfo;
import com.me.store.common.ServerResponse;
import com.me.store.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product/")
public class ProductController {
    @Autowired
    private IProductService iProductService;
    @RequestMapping(value = "detail.do",method = RequestMethod.GET)
    public ServerResponse detail(Integer productId){
          return iProductService.getProductDeatil(productId);
    }

    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword",required = false)String keyword,
                                         @RequestParam(value = "categoryId",required = false)Integer categoryId,
                                         @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy",defaultValue = "") String orderBy){

        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
    }


}
