package com.stkj.jpushdemo;

import android.util.Log;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by jarrah on 2015/5/26.
 */
public class Application extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("", "app on create");
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
