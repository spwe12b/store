package com.me.store.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.me.store.common.Const;
import com.me.store.common.ServerResponse;
import com.me.store.dao.CartMapper;
import com.me.store.dao.ProductMapper;
import com.me.store.pojo.Cart;
import com.me.store.pojo.Product;
import com.me.store.service.ICartService;
import com.me.store.util.BigDecimalUtil;
import com.me.store.util.PropertiesUtil;
import com.me.store.vo.CartProductVo;
import com.me.store.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("iCartService")
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    /**
     * 添加单个商品购物车，并返回总购物车
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    public ServerResponse add(Integer userId, Integer productId,Integer count){
        if(productId==null||count==null||userId==null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Product product=productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.createByErrorMessage("商品不存在");
        }
        if(product.getStatus()!=Const.ProductStatus.ON_SALE){
            return ServerResponse.createByErrorMessage("商品已下架或删除");
        }
        Cart cart=cartMapper.selectByUserIdProductId(userId,productId);
        //该商品尚未添加到购物车
        if(cart==null){
            Cart newCart=new Cart();
            cart.setChecked(Const.Cart.ON_CHECKED);
            cart.setProductId(productId);
            cart.setQuantity(count);
            cart.setUserId(userId);
            int resultCount=cartMapper.insert(cart);
            if(resultCount==0){
                return ServerResponse.createByErrorMessage("新增购物车失败");
            }
        }else{
            //该商品已经添加到购物车
            cart.setQuantity(cart.getQuantity()+count);
            int resultCount=cartMapper.insertSelective(cart);
            if(resultCount==0){
                return ServerResponse.createByErrorMessage("新增购物车失败");
            }
        }
        CartVo cartVo=this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    /**
     * 更新购物车商品数量
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    public ServerResponse update(Integer userId,Integer productId,Integer count){
        if(productId==null||count==null||userId==null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Cart cart=cartMapper.selectByPrimaryKey(productId);
        if(cart==null){
            return ServerResponse.createByErrorMessage("该商品未添加到购物车");
        }
        cart.setQuantity(count);
        cartMapper.insertSelective(cart);
        CartVo cartVo=this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }
    public ServerResponse delete(Integer userId,String productIds){
           if(userId==null||productIds==null){
               return ServerResponse.createByErrorMessage("参数错误");
           }
           List<String> productIdList= Splitter.on(",").splitToList(productIds);
           cartMapper.deleteByUserIdProductId(userId,productIdList);
           CartVo cartVo=this.getCartVoLimit(userId);
           return ServerResponse.createBySuccess(cartVo);
    }
    public ServerResponse checkOrUnCheck(Integer userId,Integer productId,Integer checked){
      cartMapper.checkOrUnCheck(userId,productId,checked);
      return this.list(userId);
    }
    public ServerResponse getProductCount(Integer userId){
        if(userId==null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        int count=cartMapper.getProductCountByUserId(userId);
        return ServerResponse.createBySuccess(count);
    }

    /**
     * 返回购物车集合
     * @param userId
     * @return
     */
    public ServerResponse list(Integer userId){
        if(userId==null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        CartVo cartVo=getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    /**
     * 判断该用户购物车是否全选
     * @param userId
     * @return
     */
     private boolean isAllChecked(Integer userId){
        int resultCount=cartMapper.isAllChecked(userId);
        return resultCount==0;
    }
    /**
     * 返回指定userId的全部购物车
     * @param userId
     * @return
     */
    private CartVo getCartVoLimit(Integer userId){
            CartVo cartVo=new CartVo();
        List<CartProductVo> cartProductVoList= Lists.newArrayList();
        //购物车总价
        BigDecimal totalPrice=new BigDecimal("0");
        List<Cart> cartList=cartMapper.selectByUserId(userId);

        for(Cart cart:cartList){
            Product product=productMapper.selectByPrimaryKey(cart.getProductId());
            //组装单个商品购物车
            CartProductVo cartProductVo=new CartProductVo();
            cartProductVo.setId(cart.getId());
            cartProductVo.setUserId(cart.getUserId());
            cartProductVo.setProductChecked(cart.getChecked());
            cartProductVo.setProductId(cart.getProductId());
            if(product!=null){
            cartProductVo.setProductName(product.getName());
            cartProductVo.setProductPrice(product.getPrice());
            cartProductVo.setProductMainImage(product.getMainImage());
            cartProductVo.setProductStatus(product.getStatus());
            cartProductVo.setProductSubtitle(product.getSubtitle());
            //库存足够
            if(product.getStock()>=cart.getQuantity()){
                cartProductVo.setLimitQuantity(Const.Limit.LIMITQUANTITY_TRUE);
                cartProductVo.setQuantity(cart.getQuantity());
            }else{
                //库存不够的情况，在购物车里更新有效数量
                Cart cart1=new Cart();
                cart1.setQuantity(product.getStock());
                cartMapper.insertSelective(cart1);
                cartProductVo.setQuantity(product.getStock());
                cartProductVo.setLimitQuantity(Const.Limit.LIMITQUANTITY_FALSE);//给前端的提示
            }
            //计算单商品购物车的总价
            cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(
                    cartProductVo.getProductPrice().doubleValue(),cartProductVo.getQuantity()));
            //勾选状态才添加到总价
            if(cartProductVo.getProductChecked()==Const.Cart.ON_CHECKED) {
                totalPrice = BigDecimalUtil.add(totalPrice.doubleValue(), cartProductVo.getProductPrice().doubleValue());
            }
            }
            cartProductVoList.add(cartProductVo);
        }
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setCartTotalPrice(totalPrice);
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        cartVo.setAllChecked(this.isAllChecked(userId));
        return cartVo;
    }
}
