package com.txs.receiver;

import com.txs.bean.User;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 消息订阅者
 */
public class UserReceiver extends MessageListenerAdapter {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("user message:  "+message);
        String channel=new String(message.getChannel());
        System.out.println("user message channel:  "+channel);
        String body=new String(message.getBody());
        System.out.println("user message body:  "+body);
        //反序列化
       User user=(User)new GenericJackson2JsonRedisSerializer().deserialize(message.getBody());
        System.out.println("user name:"+user.getName());
        System.out.println(user);
    }
}
