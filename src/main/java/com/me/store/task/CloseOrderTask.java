package com.me.store.task;

import com.me.store.common.Const;
import com.me.store.service.IOrderService;
import com.me.store.util.RedisShardedPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
//订单定时关单
@Component
public class CloseOrderTask {
    private static final Logger logger= LoggerFactory.getLogger(CloseOrderTask.class);
    @Autowired
    private IOrderService iOrderService;
    @Scheduled(cron="0 0/1 * * * ?")
    public void closeOrderTask(){
        logger.info("定时关单任务开启");
        Integer hour= Const.CLOSE_ORDER_TASK_HOUR;
        //value值是当前时间加上超时时间，为后面超时判断所用
        Long result=RedisShardedPoolUtil.setNx(Const.CLOSE_ORDER_TASK_LOCK,String.valueOf(
                System.currentTimeMillis()+Const.CLOSE_ORDER_TASK_LOCK_TIMEOUT));
        if(result!=null&&result.intValue()==1){
            //返回值是1则代表获取到锁,判断空是防止RedisShardedPoolUtil设置时出现异常
            RedisShardedPoolUtil.expire(Const.CLOSE_ORDER_TASK_LOCK,Const.CLOSE_ORDER_TASK_LOCK_TIMEOUT_SECOND);
            logger.info("成功获取到锁:{}",Const.CLOSE_ORDER_TASK_LOCK);
            iOrderService.closeOrder(hour);
        }else{
            String lockValueStr=RedisShardedPoolUtil.get(Const.CLOSE_ORDER_TASK_LOCK);
            if(lockValueStr!=null&&System.currentTimeMillis()>Long.parseLong(lockValueStr)){
                //该锁已超时，尝试重置锁
                logger.info("尝试获取锁:{}",Const.CLOSE_ORDER_TASK_LOCK);
                String oldLock=RedisShardedPoolUtil.getSet(Const.CLOSE_ORDER_TASK_LOCK,
                        String.valueOf(System.currentTimeMillis()+Const.CLOSE_ORDER_TASK_LOCK_TIMEOUT));
                //再判断一次，确认锁的状态没有改变
                if(lockValueStr.equals(oldLock)){
                    //真正获取到锁
                    RedisShardedPoolUtil.expire(Const.CLOSE_ORDER_TASK_LOCK,Const.CLOSE_ORDER_TASK_LOCK_TIMEOUT_SECOND);
                    logger.info("成功获取到锁:{}",Const.CLOSE_ORDER_TASK_LOCK);
                    iOrderService.closeOrder(hour);
                }else{
                    logger.info("没有获取锁:{}",Const.CLOSE_ORDER_TASK_LOCK);
                }
            }else{
                 logger.info("没有获取锁:{}",Const.CLOSE_ORDER_TASK_LOCK);
            }
        }
        logger.info("定时关单任务关闭");
    }
}
