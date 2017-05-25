package com.example.lenny.ymme;

import com.example.lenny.ymme.models.YMMEElement;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lenny on 5/22/17.
 */

public class MessageEvent  <T>{
    public String message;
    public T packet;

    public MessageEvent (T s){
         packet = s;

    }

}
