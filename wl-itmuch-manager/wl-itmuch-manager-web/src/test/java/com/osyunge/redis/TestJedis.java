package com.osyunge.redis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class TestJedis {
    @Test
    public void testMethod(){
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.122.129",6379);
        jedis.set("key1","jedis test1");
        String key1 = jedis.get("key1");
        System.out.println(key1);
        jedis.close();
    }
    @Test
    public void testMethod2(){
        //创建Jedis对象
        JedisPool pool = new JedisPool("192.168.122.129",6379);
        Jedis jedis = pool.getResource();
        jedis.set("key1","jedis test1");
        String key1 = jedis.get("key1");
        System.out.println(key1);
        jedis.close();
    }
    @Test
    public void testSpring(){
        ApplicationContext context =  new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisPool jedisPool = (JedisPool) context.getBean("redisClient");
        Jedis jedis = jedisPool.getResource();
        String a1 = jedis.get("a1");
        System.out.println(a1);
    }
}
