package com.neusoft.sheng.BdPush;

import android.content.Context;

import com.baidu.android.pushservice.PushMessageReceiver;

import java.util.List;

/**
 * Created by sheng on 2015/8/6.
 */
public class BdPushReceiver extends PushMessageReceiver {


    @Override
    public void onBind(Context context, int errorCode, String appid, String userId, String channelId, String requestId) {

        System.out.println(errorCode+"\n"+appid+"\n"+userId+"\n"+channelId+"\n"+requestId);


    }

    @Override
    public void onUnbind(Context context, int i, String s) {

    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {

    }

    @Override
    public void onMessage(Context context, String s, String s1) {

        System.out.println("推送消息"+s+"\n"+s1);
    }

    @Override
    public void onNotificationClicked(Context context, String s, String s1, String s2) {


        System.out.println("你点击了推送消息");

    }

    @Override
    public void onNotificationArrived(Context context, String s, String s1, String s2) {

    }
}
