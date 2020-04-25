# store商城后端
### 整体架构
    springboot+mybatis+mysql作整体构件，前后端分离
    redis集群做缓存服务器、分布式锁，分布式部署，nginx负载均衡
### 数据表
    cart 单个商品的购物车
    category 商品分类
    order 订单表
    order_item 订单详情
    pay_info 支付信息
    product 商品
    shipping 收货地址
    user 用户
### 高复用响应对象 
    ServerResponse，含有三个属性，data-泛型，存放响应数据 
    msg-响应消息  status-响应状态
### 全局异常处理
    ExceptionResolver，避免后端信息泄露和更友好的提示
### 全局常量
    Const，包含了订单状态，支付宝回调等各种常量
### redis
    采用了redis集群做高可用，实现了分布式登陆，分布式锁定时关单
### 文件服务器
    采用了vsftpd做文件服务器 
### 用户模块
     采用了redis做缓存解决分布式session共享问题，用户登录，注册，登录状态的重置密码，忘记密码的重置密码等
### 商品模块
     商品新增，删除，详情，搜索等
### 购物车模块
     购物车新增，列表，详情等，采用了Bigdecimal解决浮点运算丢失精度的问题
### 收货地址模块
     新增，删除地址等
### 订单模块
     创建订单，取消订单，支付订单等，目前采用了支付宝当面付API，预留了其他支付平台，采用分布式锁做了定时关单

     
