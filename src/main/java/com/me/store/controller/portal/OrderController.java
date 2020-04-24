package com.me.store.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.me.store.common.Const;
import com.me.store.common.ServerResponse;
import com.me.store.pojo.User;
import com.me.store.service.IOrderService;
import com.me.store.util.CookieUtil;
import com.me.store.util.JsonUtil;
import com.me.store.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/order/")
public class OrderController {
    private Logger logger= LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private IOrderService iOrderService;

    @RequestMapping(value = "list.do",method = RequestMethod.POST)
    public ServerResponse list(HttpServletRequest request,
                               @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        String loginToken= CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user= JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iOrderService.getOrderVoList(user.getId(),pageNum,pageSize);
    }
    /**
     * 创建订单
     * @param shippingId
     * @return
     */

    @RequestMapping(value = "create.do",method = RequestMethod.POST)
        public ServerResponse create(HttpServletRequest request,Integer shippingId){
        String loginToken=CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user=JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
      return iOrderService.createOrder(user.getId(),shippingId);
    }

    /**
     * 获取订单详情接口
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "detail.do",method = RequestMethod.POST)
    public ServerResponse detail(HttpServletRequest request,Long orderNo){
        String loginToken=CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user=JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iOrderService.getOrderDetail(user.getId(),orderNo);
    }

    /**
     * 取消订单
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "cancel.do",method = RequestMethod.POST)
    public ServerResponse cancel(HttpServletRequest request,Long orderNo){
        String loginToken=CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user=JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iOrderService.cancel(user.getId(),orderNo);
    }

    /**
     * 订单页面购物车商品展示
     * @return
     */
    @RequestMapping(value = "get_order_cart_product.do",method = RequestMethod.POST)
    public ServerResponse getOrderCartProduct(HttpServletRequest request){
        String loginToken=CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user=JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iOrderService.getOrderCartProduct(user.getId());
    }

    /**
     * 支付订单
     * @param orderNo
     * @param request
     * @return
     */
    @RequestMapping(value = "pay.do",method = RequestMethod.POST)
    public ServerResponse pay(Long orderNo, HttpServletRequest request){
        String loginToken=CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user=JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String path=request.getServletContext().getRealPath("upload");
        System.out.println(request.getContextPath());
        System.out.println(path);
        return iOrderService.pay(orderNo,user.getId(),path);
    }

    /**
     * 支付宝回调的接口
     * @param request
     * @return
     */
    @RequestMapping(value = "alipay_callback.do")
    public Object alipayCallback(HttpServletRequest request){
        System.out.println("支付宝回调");
           Map<String,String> params= Maps.newHashMap();
           Map requestParams=request.getParameterMap();
           for(Iterator iterator=requestParams.keySet().iterator();iterator.hasNext();){
               String name=(String)iterator.next();
               String[] values=(String[])requestParams.get(name);
               StringBuilder valueStr=new StringBuilder();
               for(int i=0;i<values.length;i++){
                   valueStr=(i==values.length-1)?valueStr.append(values[i])
                           :valueStr.append(values[i]).append(",");
               }
              params.put(name,valueStr.toString());
           }
           logger.info("支付宝回调，sign:{},trade_status:{},参数:{}",params.get("sign"),
                   params.get("trade_status"),params.toString());
           //验证回调的正确性
        params.remove("sign_type");
        try{
            //验证是否是支付宝发的
        boolean alipayRSACheckedV2= AlipaySignature.rsaCheckV2(params,Configs.getAlipayPublicKey(),
                "utf-8",Configs.getSignType());
        if(!alipayRSACheckedV2){
            return ServerResponse.createByErrorMessage("非法请求，验证不通过");
        }
         }catch (AlipayApiException e){
            logger.error("支付宝验证回调异常",e);
         }
        ServerResponse serverResponse=iOrderService.alipayCallback(params);
        if(serverResponse.isSuccess()){
            return Const.AliCallback.RESPONSE_SUCCESS;
        }
        return Const.AliCallback.RESPONSE_FAILED;
    }
    //查询订单是否支付成功
    @RequestMapping(value = "query_order_pay_status.do",method = RequestMethod.POST)
    public ServerResponse queryOrderPayStatus(HttpServletRequest request, Long orderNo){
        String loginToken=CookieUtil.readLoginToken(request);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        String userJsonStr= RedisShardedPoolUtil.get(loginToken);
        User user=JsonUtil.string2Obj(userJsonStr,User.class);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        ServerResponse serverResponse=iOrderService.queryOrderPayStatus(user.getId(),orderNo);
        if(serverResponse.isSuccess()){
            return ServerResponse.createBySuccess(true);
        }
        return ServerResponse.createBySuccess(false);
    }

}
