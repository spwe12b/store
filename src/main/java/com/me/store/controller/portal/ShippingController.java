package com.me.store.controller.portal;

import com.me.store.common.Const;
import com.me.store.common.ServerResponse;
import com.me.store.pojo.Shipping;
import com.me.store.pojo.User;
import com.me.store.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shipping/")
public class ShippingController {
    @Autowired
    public IShippingService iShippingService;
    @RequestMapping(value = "add.do",method = RequestMethod.POST)
    public ServerResponse add(HttpSession session, Shipping shipping){
          User user=(User)session.getAttribute(Const.CURRENT_USER);
          if(user==null){
              return ServerResponse.createByErrorMessage("用户未登陆");
          }
          return iShippingService.add(user.getId(),shipping);
    }
    @RequestMapping(value = "delete.do",method = RequestMethod.POST)
    public ServerResponse add(HttpSession session,Integer shippingId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
        return iShippingService.delete(user.getId(),shippingId);
    }
    @RequestMapping(value = "update.do",method = RequestMethod.POST)
    public ServerResponse update(HttpSession session,Shipping shipping){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
        return iShippingService.update(user.getId(),shipping);
    }
    @RequestMapping(value = "select.do",method = RequestMethod.POST)
    public ServerResponse select(HttpSession session,Integer shippingId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
        return iShippingService.select(user.getId(),shippingId);
    }
    @RequestMapping(value = "list.do",method = RequestMethod.POST)
    public ServerResponse list(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
        return iShippingService.list(user.getId());
    }
}
