package com.txs;

import com.txs.bean.User;
import com.txs.config.RedisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.DateFormat;
import java.util.Date;

@SpringBootTest
class SpringbootRedisApplicationTests {

   @Autowired
   RedisTemplate redisTemplate;

   @Test
   public void  testRedis(){

//       RedisTemplate中定义了对5种数据结构操作：
//
//       redisTemplate.opsForValue();//操作字符串
//       redisTemplate.opsForHash();//操作hash
//       redisTemplate.opsForList();//操作list
//       redisTemplate.opsForSet();//操作set
//       redisTemplate.opsForZSet();//操作有序set

       redisTemplate.opsForValue().set("txs","hello");
       System.out.println(redisTemplate.opsForValue().get("txs"));
       redisTemplate.opsForValue().set("hi","你好");
       System.out.println(redisTemplate.opsForValue().get("hi"));
//
//
       redisTemplate.opsForValue().set("user",new User(1l,"txs",12,new Date()));
//
       System.out.println(redisTemplate.opsForValue().get("user"));
   }

   @Test
   public void pushmsg()
   {
       redisTemplate.convertAndSend(RedisConfig.TOPIC_ONE,"hello topic");

       redisTemplate.convertAndSend(RedisConfig.TOPIC_TWO,new User(1l,"txs",21,new Date()));
   }

}
