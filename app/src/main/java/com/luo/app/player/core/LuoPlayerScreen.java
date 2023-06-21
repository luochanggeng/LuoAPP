package com.luo.app.player.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.luo.app.R;
import com.luo.app.utils.AnimationUtils;

/**
 * desc :
 * create by 公子赓
 * on 2023/2/19 13:42
 */
public class LuoPlayerScreen extends FrameLayout {

    public static final String SMALL = "0";

    public static final String FULL = "1";

    public static final int LOADING = 0;

    public static final int PLAYING = 1;

    public static final int PAUSE = 2;

    public static final int ERROR = 3;

    //屏幕状态 full:全屏  small:小窗口
    private String mScreenState;
    //保存小屏状态的layoutParams
    private ViewGroup.LayoutParams mSmallScreenLayoutParams;
    //保存小屏状态下布局在父布局中的位置信息
    private int mSmallScreenLayoutIndex;
    //小屏时候的父布局
    private ViewGroup mParentView;
    //是否支持大小屏无缝切换，默认支持
    private boolean mSwitchScreenAvailable = true;

    private View mOutFocusView;

    private View smallRootView;
    private LinearLayout smallPlayerInfo;
    private ImageView smallPlayerIcon;
    private TextView smallPlayerText;
    private ProgressBar smallProgressBar;

    private View fullRootView;
    private LinearLayout fullPlayerInfo;
    private ImageView fullPlayerIcon;
    private TextView fullPlayerText;
    private ImageView fullPauseIcon;
    private LinearLayout fullProgressArea;
    private SeekBar fullProgressSeekbar;

    private int playerState = LOADING;

    private String waringInfo;

    private LuoPlayer mLuoPlayer ;

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
        Log.i("zhang", "mScreenState = " + mScreenState);
        typedArray.recycle();
        initView();
    }

    private void initView() {
        //小窗状态的根布局
        smallRootView = LayoutInflater.from(getContext()).inflate(R.layout.player_screen_small, null);
        this.addView(smallRootView);

        smallPlayerInfo = smallRootView.findViewById(R.id.small_player_info);
        smallPlayerIcon = smallRootView.findViewById(R.id.small_player_icon);
        smallPlayerText = smallRootView.findViewById(R.id.small_player_text);
        smallProgressBar = smallRootView.findViewById(R.id.small_progress_bar);
        //全屏状态的根布局
        fullRootView = LayoutInflater.from(getContext()).inflate(R.layout.player_screen_full, null);
        this.addView(fullRootView);

        fullPlayerInfo = fullRootView.findViewById(R.id.full_player_info);
        fullPlayerIcon = fullRootView.findViewById(R.id.full_player_icon);
        fullPlayerText = fullRootView.findViewById(R.id.full_player_text);
        fullPauseIcon = fullRootView.findViewById(R.id.full_pause_icon);
        fullProgressArea = fullRootView.findViewById(R.id.full_progress_area);
        fullProgressSeekbar = fullRootView.findViewById(R.id.full_progress_seekbar);

        updatePlayerScreenView();
    }

    public void setLuoPlayer(LuoPlayer luoPlayer){
        mLuoPlayer = luoPlayer;
    }

    public void setSwitchScreenAvailable(boolean available) {
        this.mSwitchScreenAvailable = available;
    }

    private boolean isAnimationRunning = false ;

    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            isAnimationRunning = true;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            isAnimationRunning = false;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (SMALL.equals(mScreenState)) {
            //小视频窗不处理按键事件
            return super.dispatchKeyEvent(event);
        } else {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if(isAnimationRunning){
                    return true;
                }
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    if(fullProgressArea.getVisibility() == VISIBLE){
                        Animation animation = AnimationUtils.hideViewFromBottom();
                        animation.setAnimationListener(animationListener);
                        fullProgressArea.startAnimation(animation);
                        //动画现实进度条区域
                        fullProgressArea.setVisibility(GONE);
                        return true;
                    }
                    //切换到小屏幕的状态
                    switchScreenToSmall();
                    return true;
                }
                if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
                    //调节屏幕亮度功能
                    return true;
                }
                if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    Log.i("zhang", "playerState = " + playerState);
                    if(playerState == PLAYING){
                        controlProgress(event.getKeyCode());
                    }
                    //快进快退功能
                    return true;
                }
                if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
                    //暂停/恢复功能
                    if(playerState == PLAYING){
                        if(mLuoPlayer != null){
                            playerState = PAUSE ;
                            mLuoPlayer.pause();
                            fullProgressArea.setVisibility(GONE);
                            fullPauseIcon.setVisibility(VISIBLE);
                        }
                    }else if(playerState == PAUSE){
                        if(mLuoPlayer != null){
                            playerState = PLAYING ;
                            mLuoPlayer.resume();
                            fullPauseIcon.setVisibility(GONE);
                        }
                    }
                    return true;
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    private void controlProgress(int keyCode) {
        if(fullProgressArea.getVisibility() != VISIBLE){
            Animation animation = AnimationUtils.showViewFromBottom();
            animation.setDuration(500);
            animation.setAnimationListener(animationListener);
            fullProgressArea.startAnimation(animation);
            //动画现实进度条区域
            fullProgressArea.setVisibility(VISIBLE);
        }


    }

    /**
     * 切换大小屏状态 Suspension
     */
    public void switchScreenToSmall() {
        if (!mSwitchScreenAvailable) {
            return;
        }
        if (SMALL.equals(mScreenState)) {
            return;
        }
        if(playerState == PAUSE){
            if(mLuoPlayer != null){
                mLuoPlayer.resume();
                playerState = PLAYING ;
            }
        }
        mScreenState = SMALL;
        clearFocus();
        setFocusable(false);
        if (mOutFocusView != null) {
            mOutFocusView.requestFocus();
        }

        ViewGroup decorView = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
        decorView.removeView(this);

        if (null != mParentView) {
            mParentView.addView(this, mSmallScreenLayoutIndex, mSmallScreenLayoutParams);
        }
        updatePlayerScreenView();
    }

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
        updatePlayerScreenView();
    }

    public void onPlaySuccess() {
        playerState = PLAYING;
        updatePlayerScreenView();
    }

    public boolean onPlayComplete() {
        playerState = LOADING;
        updatePlayerScreenView();
        return mSwitchScreenAvailable;
    }

    public void onPlayError(int what, int extra) {
        playerState = ERROR;
        waringInfo = "[Error(" + what + ", " + extra + ")]按菜单键刷新";
        updatePlayerScreenView();
    }

    private boolean isInitProgressMaxValue = false;

    public void upDateProgress(long currentPlayPosition, long duration) {
        if(!isInitProgressMaxValue){
            isInitProgressMaxValue = true;
            smallProgressBar.setMax((int) duration);
            fullProgressSeekbar.setMax((int) duration);
        }
        smallProgressBar.setProgress((int) currentPlayPosition);
        fullProgressSeekbar.setProgress((int) currentPlayPosition);
    }

    private void updatePlayerScreenView(){
        if(FULL.equals(mScreenState)){
            smallPlayerIcon.clearAnimation();
            fullPlayerIcon.clearAnimation();
            smallRootView.setVisibility(GONE);
            fullRootView.setVisibility(VISIBLE);
        }else{
            smallPlayerIcon.clearAnimation();
            fullPlayerIcon.clearAnimation();
            fullRootView.setVisibility(GONE);
            smallRootView.setVisibility(VISIBLE);
        }
        switch (playerState){
            case LOADING:
                if(FULL.equals(mScreenState)){
                    fullPlayerIcon.setImageResource(R.mipmap.loading);
                    fullPlayerIcon.startAnimation(getRotateAnimation());
                    fullPlayerText.setText("加载中...");
                    fullPlayerInfo.setVisibility(VISIBLE);
                }else{
                    smallPlayerIcon.setImageResource(R.mipmap.loading);
                    smallPlayerIcon.startAnimation(getRotateAnimation());
                    smallPlayerText.setText("加载中...");
                    smallPlayerInfo.setVisibility(VISIBLE);
                }
                break;
            case PLAYING:
                if(FULL.equals(mScreenState)){
                    fullPlayerInfo.setVisibility(GONE);
                    fullPauseIcon.setVisibility(GONE);
                }else{
                    fullPauseIcon.setVisibility(GONE);
                    smallPlayerInfo.setVisibility(GONE);
                    smallProgressBar.setVisibility(VISIBLE);
                }
                break;
            case PAUSE:
                if(FULL.equals(mScreenState)){
                    fullPlayerInfo.setVisibility(GONE);
                    fullPauseIcon.setVisibility(VISIBLE);
                    fullProgressArea.setVisibility(GONE);
                }
                break;
            case ERROR:
                if(FULL.equals(mScreenState)){
                    fullPlayerIcon.setImageResource(R.mipmap.refresh);
                    fullPlayerText.setText(waringInfo);
                    fullPlayerInfo.setVisibility(VISIBLE);
                }else{
                    smallPlayerIcon.setImageResource(R.mipmap.refresh);
                    smallPlayerText.setText(waringInfo);
                    smallPlayerInfo.setVisibility(VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    private RotateAnimation animation ;

    private RotateAnimation getRotateAnimation() {
        if(animation == null){
            animation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(1000);
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatCount(-1);
        }
        return animation;
    }
}
