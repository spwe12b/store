package com.me.store.controller.portal;

import com.me.store.common.Const;
import com.me.store.common.ServerResponse;
import com.me.store.pojo.User;
import com.me.store.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    private ICartService iCartService;
    @RequestMapping("add.do")
    public ServerResponse add(HttpSession session,Integer productId,Integer count){
           User user=(User)session.getAttribute(Const.CURRENT_USER);
           if(user==null){
               return ServerResponse.createByErrorMessage("用户未登陆");
           }
           return iCartService.add(user.getId(),productId,count);
    }
    @RequestMapping("update.do")
    public ServerResponse update(HttpSession session,Integer productId,Integer count){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
        return iCartService.update(user.getId(),productId,count);
    }
    @RequestMapping("delete.do")
    public ServerResponse delete(HttpSession session,String productIds){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
        return iCartService.delete(user.getId(),productIds);
    }

    /**
     * 单个商品选择
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping("check.do")
    public ServerResponse check(HttpSession session,Integer productId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
        return iCartService.checkOrUnCheck(user.getId(),productId,Const.Cart.ON_CHECKED);
    }

    /**
     * 单个商品反选
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping("un_check.do")
    public ServerResponse unCheck(HttpSession session,Integer productId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
       return iCartService.checkOrUnCheck(user.getId(),productId,Const.Cart.UN_CHECKED);
    }

    /**
     * 全选
     * @param session
     * @return
     */
    @RequestMapping("check_all.do")
    public ServerResponse checkAll(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
        return iCartService.checkOrUnCheck(user.getId(),null,Const.Cart.ON_CHECKED);
    }

    /**
     * 全反选
     * @param session
     * @return
     */
    @RequestMapping("un_check_all.do")
    public ServerResponse unCheckAll(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
      return iCartService.checkOrUnCheck(user.getId(),null,Const.Cart.UN_CHECKED);
    }

    /**
     * 获取购物车商品的总数量
     * @param session
     * @return
     */
    @RequestMapping("get_product_count.do")
    public ServerResponse getProductCount(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
        return iCartService.getProductCount(user.getId());
    }

    public static void main(String[] args) {
        Integer integer=new Integer("123");
        Integer integer1=new Integer("123");
        Integer i=127;
        Integer i1=127;
        System.out.println(i==i1);
        User user=new User();

    }
}
