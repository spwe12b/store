package com.me.store.common;

import com.google.common.collect.Lists;
import com.me.store.util.PropertiesUtil;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.List;

public class RedisShardedPool {
    private static ShardedJedisPool pool;
    private static Integer maxTotal=Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20"));//最大连接数
    private static Integer maxIdle=Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","10"));//最大idle(空闲）状态的jedis实例个数
    private static Integer minIdle=Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","2"));//最小idle(空闲）状态的jedis实例个数
    private static Boolean testOnBorrow=Boolean.getBoolean(PropertiesUtil.getProperty("redis.test.borrow","false"));
    private static Boolean testOnReturn=Boolean.getBoolean(PropertiesUtil.getProperty("redis.test.return","false"));
    private static String redisIp_1=PropertiesUtil.getProperty("redis1.ip");
    private static Integer redisPort_1=Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));
    private static String redisIp_2=PropertiesUtil.getProperty("redis2.ip");
    private static Integer redisPort_2=Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));
    private static void initPool(){
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestOnReturn(testOnReturn);
        jedisPoolConfig.setBlockWhenExhausted(true);//连接耗尽时是否阻塞，true会阻塞直到超时,false会抛出异常,默认为true
        JedisShardInfo info1=new JedisShardInfo(redisIp_1,redisPort_1,1000*2);//两秒
        JedisShardInfo info2=new JedisShardInfo(redisIp_2,redisPort_2,1000*2);
        List<JedisShardInfo> jedisShardInfoList= Lists.newArrayList();
        jedisShardInfoList.add(info1);
        jedisShardInfoList.add(info2);
        //一致性hash算法
        pool=new ShardedJedisPool(jedisPoolConfig,jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }
    static{
        initPool();
    }
    public static ShardedJedis getJedis(){
        return pool.getResource();
    }
    public static void returnResource(ShardedJedis jedis){
        pool.returnResource(jedis);
    }
    public static void returnBrokenResource(ShardedJedis jedis){
        pool.returnBrokenResource(jedis);
    }

    public static void main(String[] args) {
         ShardedJedis shardedJedis=getJedis();
         for(int i=0;i<10;i++){
             shardedJedis.set("key"+i,"value"+i);
         }
    }

}
