package com.luo.app.player;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.luo.app.R;

/**
 * desc :
 * create by 公子赓
 * on 2023/2/19 13:42
 */
public class LuoPlayerScreen extends FrameLayout implements ILuoPlayerListener{

    public static final String SMALL = "0";

    public static final String FULL = "1";
    //屏幕状态 full:全屏  small:小窗口
    private String mScreenState;
    //保存小屏状态的layoutParams
    private ViewGroup.LayoutParams mSmallScreenLayoutParams;
    //保存小屏状态下布局在父布局中的位置信息
    private int mSmallScreenLayoutIndex;
    //小屏时候的父布局
    private ViewGroup mParentView;

//    private LuoPlayer luoPlayer;
    //是否支持大小屏无缝切换，默认支持
    private boolean mSwitchScreenAvailable = true ;

    private View mOutFocusView ;

    public LuoPlayerScreen(@NonNull Context context) {
        this(context, null);
    }

    public LuoPlayerScreen(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("CustomViewStyleable")
    public LuoPlayerScreen(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.player_screen);
        mScreenState = typedArray.getString(R.styleable.player_screen_screen_state);
        typedArray.recycle();
        initView();
    }

    private void initView() {
//        luoPlayer = new LuoPlayer(this);
//        luoPlayer.setPlayerListener(this);
        //小窗状态的根布局
        View smallRootView = LayoutInflater.from(getContext()).inflate(R.layout.player_screen_small, this);
        //全屏状态的根布局
        View fullRootView = LayoutInflater.from(getContext()).inflate(R.layout.player_screen_small, this);
    }

//    public void setDataSource(String url){
//        luoPlayer.setDataSource(url);
//    }

    public void setSwitchScreenAvailable(boolean available){
        this.mSwitchScreenAvailable = available;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (SMALL.equals(mScreenState)) {
            //小视频窗不处理按键事件
            return super.dispatchKeyEvent(event);
        } else {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
                    //切换到小屏幕的状态
                    switchScreenToSmall(true);
                    return true;
                }
                if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN){
                    //调节屏幕亮度功能
                    return true;
                }
                if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT){
                    //快进快退功能
                    return true;
                }
                if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER){
                    //暂停/恢复功能
                    return true;
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 切换大小屏状态 Suspension
     */
    public void switchScreenToSmall(boolean needResume) {
        if (!mSwitchScreenAvailable) {
            return;
        }
        if (SMALL.equals(mScreenState)) {
            return;
        }
        mScreenState = SMALL;

        clearFocus();
        setFocusable(false);
        if(mOutFocusView != null){
            mOutFocusView.requestFocus();
        }

        ViewGroup decorView = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
        decorView.removeView(this);

        if (null != mParentView) {
            mParentView.addView(this, mSmallScreenLayoutIndex, mSmallScreenLayoutParams);
        }
        //恢复到小屏状态下视频恢复播放
//        if(needResume){
//            luoPlayerCore.start();
//        }
    }

//    public void pause(){
//        luoPlayer.pause();
//    }

//    public void release(){
//        if(LuoPlayerScreen.FULL.equals(mScreenState)){
//            switchScreenToSmall(false);
//        }
//        luoPlayer.release();
//    }

    public void switchScreenToFull(View outFocusView) {
        if (!mSwitchScreenAvailable) {
            return;
        }
        if (FULL.equals(mScreenState)) {
            return;
        }

        mScreenState = FULL;
        setFocusable(true);
        mOutFocusView = outFocusView;

        ViewGroup decorView = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
        decorView.removeView(this);

        mSmallScreenLayoutParams = this.getLayoutParams();
        mParentView = (ViewGroup) this.getParent();
        mSmallScreenLayoutIndex = mParentView.indexOfChild(this);
        mParentView.removeView(this);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        decorView.addView(this, lp);
        requestFocus();
    }

    @Override
    public void onPlaySuccess() {

    }

    @Override
    public void onPlayComplete() {

    }

    @Override
    public void onPlayError(int what, int extra) {

    }

    @Override
    public void upDateProgress(long currentPlayPosition, long duration) {

    }
}
