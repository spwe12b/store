package com.me.store.service.impl;

import com.github.pagehelper.PageHelper;
import com.me.store.common.ServerResponse;
import com.me.store.dao.ShippingMapper;
import com.me.store.pojo.Shipping;
import com.me.store.service.IShippingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {
    @Autowired
    public ShippingMapper shippingMapper;

    /**
     * 新增地址
     * @param userId
     * @param shipping
     * @return
     */
    public ServerResponse add(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        int resultCount=shippingMapper.insert(shipping);
        if(resultCount>0){
            return ServerResponse.createBySuccessMessage("新增地址成功");
        }
        return ServerResponse.createByErrorMessage("新增地址失败");
    }

    /**
     * 删除地址
     * @param userId
     * @param shippingId
     * @return
     */
    public ServerResponse delete(Integer userId,Integer shippingId){
        int result=shippingMapper.deleteByUserIdShippingId(userId,shippingId);
        if(result>0){
            return ServerResponse.createBySuccessMessage("删除地址成功");
        }
        return ServerResponse.createByErrorMessage("删除地址失败");
    }

    /**
     * 更新地址
     * @param userId
     * @param shipping
     * @return
     */
    public ServerResponse update(Integer userId,Shipping shipping){
        int resultCount=shippingMapper.updateByUserId(userId,shipping);
        if(resultCount>0){
            return ServerResponse.createBySuccessMessage("更新地址成功");
        }
        return ServerResponse.createByErrorMessage("更新地址失败");
    }

    /**
     * 查询地址
     * @param userId
     * @param shippingId
     * @return
     */
    public ServerResponse select(Integer userId,Integer shippingId){
        Shipping shipping=shippingMapper.selectByUserIdShippingId(userId,shippingId);
        if(shipping!=null){
            return ServerResponse.createBySuccess(shipping);
        }
        return ServerResponse.createByErrorMessage("该地址不存在");
    }

    /**
     * 查询用户所有的地址
     * @param userId
     * @return
     */
    public ServerResponse list(Integer userId){
        List<Shipping> shippingList=shippingMapper.selectByUserId(userId);
        return ServerResponse.createBySuccess(shippingList);
    }



}
