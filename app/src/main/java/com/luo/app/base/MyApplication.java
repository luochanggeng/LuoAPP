package com.luo.app.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.luo.app.network.NetWorkUtil;

/**
 * desc :
 * create by 公子赓
 * on 2023/2/18 21:06
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initNetWork();
    }

    private void initNetWork() {
        SharedPreferences loader = getSharedPreferences("loader", Context.MODE_PRIVATE);
        String ip = loader.getString("IP", "");
        NetWorkUtil.getInstance().setIpAddress(ip);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
