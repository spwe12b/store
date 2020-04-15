package com.me.store.util;

import com.me.store.common.RedisShardedPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

public class RedisShardedPoolUtil {
    private static final Logger logger=LoggerFactory.getLogger(RedisShardedPoolUtil.class);
    public static String set(String key,String value){
        ShardedJedis jedis=null;
        String result=null;
        try{
        jedis= RedisShardedPool.getJedis();
        result=jedis.set(key,value);
    }catch (Exception e){
            logger.error("set key:{} value{}",key,value,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }
    public static String get(String key){
        ShardedJedis jedis=null;
        String result=null;
        try{
            jedis= RedisShardedPool.getJedis();
            result=jedis.get(key);
        }catch (Exception e){
            logger.error("get key:{} error",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }
    //exTime单位是秒
    public static String setEx(String key,String value,int exTime){
        ShardedJedis jedis=null;
        String result=null;
        try{
            jedis= RedisShardedPool.getJedis();
            result=jedis.setex(key,exTime,value);
        }catch (Exception e){
            logger.error("setex key:{} value{} error",key,value,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }
    public static Long setNx(String key,String value){
        ShardedJedis jedis=null;
        Long result=null;
        try{
            jedis= RedisShardedPool.getJedis();
            result=jedis.setnx(key,value);
        }catch (Exception e){
            logger.error("setnx key:{} value{} error",key,value,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }
    public static String getSet(String key,String value){
        ShardedJedis jedis=null;
        String result=null;
        try{
            jedis= RedisShardedPool.getJedis();
            result=jedis.getSet(key,value);
        }catch (Exception e){
            logger.error("getset key:{} value{} error",key,value,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }
    //设置key的有效期
    public static Long expire(String key,int exTime){
        ShardedJedis jedis=null;
        Long result=null;
        try{
            jedis= RedisShardedPool.getJedis();
            result=jedis.expire(key,exTime);
        }catch (Exception e){
            logger.error("expire key:{} error",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }
    public static Long del(String key){
        ShardedJedis jedis=null;
        Long result=null;
        try{
            jedis= RedisShardedPool.getJedis();
            result=jedis.del(key);
        }catch (Exception e){
            logger.error("del key:{} error",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisShardedPool.returnResource(jedis);
        return result;
    }
}
