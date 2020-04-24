package com.me.store.controller.backend;

import com.me.store.common.Const;
import com.me.store.common.ResponseCode;
import com.me.store.common.ServerResponse;
import com.me.store.pojo.User;
import com.me.store.service.ICategoryService;
import com.me.store.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/category/")
public class CategoryManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 添加分类
     * @param parentId
     * @param categoryName
     * @param session
     * @return
     */
    @RequestMapping(value = "add_category.do.do",method = RequestMethod.POST)
    public ServerResponse<String> addCategory(Integer parentId, String categoryName, HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"需要登陆");
        }
        if(iUserService.checkAdmin(user).isSuccess()){
            return iCategoryService.addCategory(parentId,categoryName);
        }
        return ServerResponse.createByErrorMessage("该用户不是管理员");
    }

    /**
     * 更改分类名称
     * @param session
     * @param categoryId
     * @param categoryName
     * @return
     */
    @RequestMapping(value = "set_category_name.do",method = RequestMethod.POST)
    public ServerResponse setCategoryName(HttpSession session,Integer categoryId,String categoryName){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"需要登陆");
        }
        if(iUserService.checkAdmin(user).isSuccess()){
          return iCategoryService.setCategoryName(categoryId,categoryName);
        }
        return ServerResponse.createByErrorMessage("该用户不是管理员");

    }

    /**
     * 获取该分类下所有平级子节点
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_children_parallel_category.do",method = RequestMethod.POST)
    public ServerResponse getChildrenParallelCategory(HttpSession session,Integer categoryId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"需要登陆");
        }
        if(iUserService.checkAdmin(user).isSuccess()){
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }
        return ServerResponse.createByErrorMessage("该用户不是管理员");
    }

    /**
     * 获取该分类下所有的子节点（递归）
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_children_deep_category.do",method = RequestMethod.POST)
    public ServerResponse getChildrenDeepCategory(HttpSession session,Integer categoryId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"需要登陆");
        }
        if(iUserService.checkAdmin(user).isSuccess()){
              return iCategoryService.getChildrenDeepCategory(categoryId);
        }
        return ServerResponse.createByErrorMessage("该用户不是管理员");
    }

}
