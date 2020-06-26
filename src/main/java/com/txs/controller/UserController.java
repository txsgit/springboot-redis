package com.txs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping(value = "/{msg}")
    public Object sendmsg(@PathVariable(value = "msg") String msg){
        redisTemplate.opsForValue().set("txs",msg);
        System.out.println(redisTemplate.opsForValue().get("txs"));
        return redisTemplate.opsForValue().get("txs");
    }
}
