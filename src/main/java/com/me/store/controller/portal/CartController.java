package com.me.store.controller.portal;

import com.me.store.common.Const;
import com.me.store.common.ServerResponse;
import com.me.store.pojo.User;
import com.me.store.service.ICartService;
import com.me.store.util.CookieUtil;
import com.me.store.util.JsonUtil;
import com.me.store.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    private ICartService iCartService;
    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    public ServerResponse list(HttpServletRequest request){
        String loginToken= CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user= JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iCartService.list(user.getId());
    }
    @RequestMapping(value = "add.do",method = RequestMethod.GET)
    public ServerResponse add(HttpServletRequest request, Integer productId, Integer count){
        String loginToken= CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user= JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
           return iCartService.add(user.getId(),productId,count);
    }
    @RequestMapping(value = "update.do",method = RequestMethod.GET)
    public ServerResponse update(HttpServletRequest request,Integer productId,Integer count){
        String loginToken= CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user= JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iCartService.update(user.getId(),productId,count);
    }
    @RequestMapping(value = "delete.do",method = RequestMethod.GET)
    public ServerResponse delete(HttpServletRequest request,String productIds){
        String loginToken= CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user= JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iCartService.delete(user.getId(),productIds);
    }

    /**
     * 单个商品选择
     * @param productId
     * @return
     */
    @RequestMapping(value = "check.do",method = RequestMethod.GET)
    public ServerResponse check(HttpServletRequest request,Integer productId){
        String loginToken= CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user= JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iCartService.checkOrUnCheck(user.getId(),productId,Const.Cart.ON_CHECKED);
    }

    /**
     * 单个商品反选
     * @param productId
     * @return
     */
    @RequestMapping(value = "un_check.do",method = RequestMethod.GET)
    public ServerResponse unCheck(HttpServletRequest request,Integer productId){
        String loginToken= CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user= JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
       return iCartService.checkOrUnCheck(user.getId(),productId,Const.Cart.UN_CHECKED);
    }

    /**
     * 全选
     * @return
     */
    @RequestMapping(value = "check_all.do",method = RequestMethod.GET)
    public ServerResponse checkAll(HttpServletRequest request){
        String loginToken= CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user= JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iCartService.checkOrUnCheck(user.getId(),null,Const.Cart.ON_CHECKED);
    }

    /**
     * 全反选
     * @return
     */
    @RequestMapping(value = "un_check_all.do",method = RequestMethod.GET)
    public ServerResponse unCheckAll(HttpServletRequest request){
        String loginToken= CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user= JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
      return iCartService.checkOrUnCheck(user.getId(),null,Const.Cart.UN_CHECKED);
    }

    /**
     * 获取购物车商品的总数量
     * @return
     */
    @RequestMapping(value = "get_cart_product_count.do",method = RequestMethod.GET)
    public ServerResponse getProductCount(HttpServletRequest request) {
        String loginToken= CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user= JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iCartService.getProductCount(user.getId());
    }
}
