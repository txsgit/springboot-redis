package com.txs.receiver;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * 消息订阅者
 */
public class MsgReceiver extends MessageListenerAdapter {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("msg message:  "+message);
        String channel=new String(message.getChannel());
        System.out.println("msg message channel:  "+channel);
        String body=new String(message.getBody());
        System.out.println("msg message body:  "+body);
    }
}
