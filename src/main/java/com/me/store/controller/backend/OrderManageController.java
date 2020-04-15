package com.me.store.controller.backend;

import com.me.store.common.Const;
import com.me.store.common.ResponseCode;
import com.me.store.common.ServerResponse;
import com.me.store.pojo.User;
import com.me.store.service.IOrderService;
import com.me.store.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController("/manage/order/")
public class OrderManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderService iOrderService;
    @RequestMapping("list.do")
    public ServerResponse orderList(HttpSession session,
                                    @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                    @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){

        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"需要登陆");
        }
        if(iUserService.checkAdmin(user).isSuccess()){
          iOrderService.manageList(pageNum,pageSize);
        }
        return ServerResponse.createByErrorMessage("该用户不是管理员");
    }
    @RequestMapping("detail.do")
    public ServerResponse detail(HttpSession session,Long orderNo){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"需要登陆");
        }
        if(iUserService.checkAdmin(user).isSuccess()){
             return iOrderService.manageDetail(orderNo);
        }
        return ServerResponse.createByErrorMessage("该用户不是管理员");
    }
    @RequestMapping("send_goods.do")
    public ServerResponse orderSendGoods(HttpSession session,Long orderNo){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"需要登陆");
        }
        if(iUserService.checkAdmin(user).isSuccess()){
            return iOrderService.manageSendGoods(orderNo);
        }
        return ServerResponse.createByErrorMessage("该用户不是管理员");
    }
}
