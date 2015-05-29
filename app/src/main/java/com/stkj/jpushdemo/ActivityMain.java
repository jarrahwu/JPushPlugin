package com.stkj.jpushdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;


public class ActivityMain extends ActionBarActivity {

    public static boolean isForeground;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JPushInterface.init(getApplicationContext());
        JPushInterface.setAliasAndTags(getApplicationContext(), "abcdef", null, null);
        //test log info
        setContentView(R.layout.activity_main);
        Log();
    }

    private void Log() {
        tv = (TextView) findViewById(R.id.log);
        String udid =  JPushUtil.getImei(getApplicationContext(), "");
        String appKey = JPushUtil.getAppKey(getApplicationContext());
        if (null == appKey) appKey = "AppKey error";

        String packageName =  getPackageName();

        String versionName =  JPushUtil.GetVersion(getApplicationContext());

        tv.append(udid);
        tv.append("\n");
        tv.append(appKey);
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        registerMessageReceiver();
        JPushInterface.onResume(this);

    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
        unregisterReceiver(mMessageReceiver);
        JPushInterface.onPause(this);
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!JPushUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                tv.append("\n");
                tv.append(showMsg);
            }
        }
    }
}
