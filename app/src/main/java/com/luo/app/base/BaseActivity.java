package com.luo.app.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.luo.app.utils.ActivityStackUtils;

/**
 * desc :
 * create by 公子赓
 * on 2023/2/18 22:22
 */
public abstract class BaseActivity extends Activity {

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(Intent.ACTION_TIME_TICK.equals(action)){
                updateTime();
            }
            boolean isHaveNetwork = false;
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectMgr != null) {
                    //新版本调用方法获取网络状态
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Network[] networks = connectMgr.getAllNetworks();
                        NetworkInfo networkInfo;
                        for (Network mNetwork : networks) {
                            networkInfo = connectMgr.getNetworkInfo(mNetwork);
                            if (networkInfo != null && networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                                isHaveNetwork = true;
                                break;
                            }
                        }
                    } else {
                        //否则调用旧版本方法
                        NetworkInfo[] info = connectMgr.getAllNetworkInfo();
                        for (NetworkInfo anInfo : info) {
                            if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                                isHaveNetwork = true;
                                break;
                            }
                        }
                    }
                }
                updateNetWorkState(isHaveNetwork);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置禁用软键盘
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        initIntent();
        initLayout();
    }

    protected abstract void initLayout();

    protected void initIntent(){}

    protected void updateTime(){}

    protected void updateNetWorkState(boolean isHaveNetwork){}

    @Override
    protected void onResume() {
        super.onResume();
        ActivityStackUtils.getInstance().add(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver, intentFilter);
    }


    @Override
    protected void onPause() {
        super.onPause();
        ActivityStackUtils.getInstance().remove(this);
        unregisterReceiver(receiver);
    }
}
