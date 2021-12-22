package com.cyscm;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * 1:创建JedisPoolConfig配置对象，指定最大空闲连接10、最小空闲连接5、最大等待时间为3000毫秒、最大连接数50
 * 2:创建JedisPool
 * 3:使用@Test注解，编写测试用例，查看redis中所有key
 *     a）从Redis连接池获取Redis连接
 *     b）调用keys方法获取所有key
 *     c）遍历打印所有key
 * @author zhoutao
 */
public class RedisTest {

    private JedisPool jedisPool;

    @BeforeTest
    public void beforeTest(){
        //1:创建JedisPoolConfig配置对象，指定最大空闲连接10、最小空闲连接5、最大等待时间为3000毫秒、最大连接数50
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMinIdle(5);
        config.setMaxWaitMillis(3000);
        config.setMaxTotal(50);
        //2:创建JedisPool
        jedisPool = new JedisPool(config, "1.12.248.64", 6379, null, "qaz123WSXQWE");
    }

    @Test
    public void test(){
        //从Redis连接池获取Redis连接
        Jedis jedis = jedisPool.getResource();
        //调用keys方法获取所有key
        Set<String> keys = jedis.keys("*");
        //遍历打印所有key
        for (String key : keys) {
            System.out.println(key);
        }
    }

    @AfterTest
    public void afterTest(){
        //关闭连接
        jedisPool.close();
    }
}