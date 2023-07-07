package com.luo.app.tvDemo.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * desc :
 * create by 公子赓
 * on 2023/7/1 11:55
 */
public class ActivityStackUtils implements Handler.Callback {

    private final List<Activity> activityStackList ;

    private final List<String> activityNameList ;

    private static ActivityStackUtils instance ;

    private final Handler handler ;

    private ActivityStackUtils(){
        HandlerThread a = new HandlerThread("a");
        a.start();
        handler = new Handler(a.getLooper(), this);
        activityStackList = new ArrayList<>();
        activityNameList = new ArrayList<>();
    }

    public static ActivityStackUtils getInstance(){
        if(instance == null){
            instance = new ActivityStackUtils();
        }
        return instance ;
    }

    public void add(Activity activity){
        if(!activityNameList.contains(activity.getClass().getName())){
            activityNameList.add(activity.getClass().getName());
        }
        if(!activityStackList.contains(activity)){
            activityStackList.add(activity);
        }
    }

    public void remove(Activity activity){
        activityNameList.remove(activity.getClass().getName());
        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public boolean handleMessage(@NonNull @NotNull Message message) {
        if(message.what == 0){
            if(activityNameList.size() <= 0){
                for(Activity activity : activityStackList){
                    activity.finish();
                }
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
        return false;
    }
}
