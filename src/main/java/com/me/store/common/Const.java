package com.me.store.common;

import com.google.common.collect.Sets;

import java.util.Set;
//常量类
public class Const {
    private Const(){}
    public interface RedisCacheExtime{
        int REDIS_SESSION_TIME=60*30;//单位是秒,30分钟
    }
    //产品的排序集合
    public static final Set ORDER_BY_SET=Sets.newHashSet("price_asc","price_desc");

    public interface Limit{
        String LIMITQUANTITY_FALSE="LIMITQUANTITY_FALSE";
        String LIMITQUANTITY_TRUE="LIMITQUANTITY_TRUE";
    }
    public interface Cart{
        int ON_CHECKED=1;
        int UN_CHECKED=0;
    }
    //产品状态
    public interface ProductStatus {
        Integer ON_SALE = 1;
    }
    public static final String IMGPREFIX="ftp.server.http.prefix";
    public static final String CURRENT_USER="currentUser";
    public static final String EMPTY="";
    public static final String EMAIL="email";
    public static final String USERNAME="username";
    public static final String PHONE="phone";
    public static final String TOKEN_PREFIX="token_";
    public static final Integer CLOSE_ORDER_TASK_HOUR=2;
    public static final String CLOSE_ORDER_TASK_LOCK="close_order_task_lock";
    public static final Long CLOSE_ORDER_TASK_LOCK_TIMEOUT=5000L;//单位毫秒
    public static final Integer CLOSE_ORDER_TASK_LOCK_TIMEOUT_SECOND=5;//单位是秒;
    //用户角色
    public interface Role{
        int ROLE_CUSTOMER=1;//普通用户
        int ROLE_ADMIN=0;//管理员
    }
    //支付模块返回给前端的参数
    public static final String ORDER_NO="orderNo";
    public static final String QR_URL="qrUrl";
    //支付宝回调
    public interface AliCallback{
        //支付宝回调参数
        String OUT_TRADE_NO="out_trade_no";
        String TRADE_NO="trade_no";
        String TRADE_STATUS="trade_status";
        String GMT_PAYMENT="gmt_payment";
        //支付宝回调支付状态
        String WAIT_BUYER_PAY="WAIT_BUYER_PAY";
        String TRADE_SUCCESS="TRADE_SUCCESS";
        //返回给支付宝
        String RESPONSE_SUCCESS="success";
        String RESPONSE_FAILED="failed";
    }
    //订单支付状态
    public enum OrderStatus{
        CANCELED(0,"已取消"),
        NO_PAY(10,"待支付"),
        PAID(20,"已支付"),
        SHIPPED(40,"已发货"),
        ORDER_SUCCESS(50,"订单完成"),
        ORDER_CLOSED(60,"订单关闭")
        ;
        public static OrderStatus codeOf(int code){
            for(OrderStatus orderStatus:values()){
                if(orderStatus.getCode()==code){
                    return orderStatus;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
        private OrderStatus(Integer code,String value){
            this.value=value;
            this.code=code;
        }
        private String value;
        private Integer code;

        public String getValue() {
            return value;
        }

        public Integer getCode() {
            return code;
        }
    }
    public enum PayPlatformEnum{
        ALIPAY(1,"支付宝"),
        ;
        private PayPlatformEnum(Integer code,String value){
            this.value=value;
            this.code=code;
        }
        private String value;
        private Integer code;

        public String getValue() {
            return value;
        }

        public Integer getCode() {
            return code;
        }
    }
    public enum PaymentTypeEnum{
        ONLINE_PAY(1,"在线支付"),
        ;
        public static PaymentTypeEnum codeOf(int code){
            for(PaymentTypeEnum paymentTypeEnum:values()){
                if(paymentTypeEnum.getCode()==code){
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
         PaymentTypeEnum(Integer code,String value){
            this.value=value;
            this.code=code;
        }
        private String value;
        private Integer code;

        public String getValue() {
            return value;
        }

        public Integer getCode() {
            return code;
        }
    }

}
