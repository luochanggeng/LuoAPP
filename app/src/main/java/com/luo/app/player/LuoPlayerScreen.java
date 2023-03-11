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
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.luo.app.R;

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
    private ImageView smallPlayerIcon;
    private TextView smallPlayerText;
    private ProgressBar smallProgressBar;

    private View fullRootView;
    private ImageView fullPlayerIcon;
    private TextView fullPlayerText;
    private ImageView fullPauseIcon;
    private LinearLayout fullProgressArea;

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
        typedArray.recycle();
        initView();
    }

    private void initView() {
        //小窗状态的根布局
        smallRootView = LayoutInflater.from(getContext()).inflate(R.layout.player_screen_small, this);
        smallPlayerIcon = smallRootView.findViewById(R.id.small_player_icon);
        smallPlayerText = smallRootView.findViewById(R.id.small_player_text);
        smallProgressBar = smallRootView.findViewById(R.id.small_progress_bar);
        //全屏状态的根布局
        fullRootView = LayoutInflater.from(getContext()).inflate(R.layout.player_screen_full, this);
        fullPlayerIcon = fullRootView.findViewById(R.id.full_player_icon);
        fullPlayerText = fullRootView.findViewById(R.id.full_player_text);
        fullPauseIcon = fullRootView.findViewById(R.id.full_pause_icon);
        fullProgressArea = fullRootView.findViewById(R.id.full_progress_area);

        updatePlayerScreenView();
    }

    public void setLuoPlayer(LuoPlayer luoPlayer){
        mLuoPlayer = luoPlayer;
    }

    public void setSwitchScreenAvailable(boolean available) {
        this.mSwitchScreenAvailable = available;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (SMALL.equals(mScreenState)) {
            //小视频窗不处理按键事件
            return super.dispatchKeyEvent(event);
        } else {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    //切换到小屏幕的状态
                    switchScreenToSmall();
                    return true;
                }
                if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
                    //调节屏幕亮度功能
                    return true;
                }
                if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    if(playerState == PLAYING){

                    }
                    //快进快退功能
                    return true;
                }
                if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
                    //暂停/恢复功能
                    if(playerState == PLAYING){
                        playerState = PAUSE ;
                        if(mLuoPlayer != null){
                            mLuoPlayer.pause();
                        }
                    }else if(playerState == PAUSE){
                        if(mLuoPlayer != null){
                            mLuoPlayer.resume();
                        }
                    }
                    return true;
                }
            }
        }
        return super.dispatchKeyEvent(event);
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

        updatePlayerScreenView();

        requestFocus();
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

    public void upDateProgress(long currentPlayPosition, long duration) {
        if (SMALL.equals(mScreenState)) {
            if (smallProgressBar.getVisibility() != VISIBLE) {
                smallProgressBar.setMax((int) duration);
                smallProgressBar.setVisibility(VISIBLE);
            }
            smallProgressBar.setProgress((int) currentPlayPosition);
        }
    }

    private void updatePlayerScreenView(){
        if(FULL.equals(mScreenState)){
            smallPlayerIcon.clearAnimation();
            smallRootView.setVisibility(INVISIBLE);
            fullRootView.setVisibility(VISIBLE);
        }else{
            fullPlayerIcon.clearAnimation();
            fullRootView.setVisibility(INVISIBLE);
            smallRootView.setVisibility(VISIBLE);
        }

        switch (playerState){
            case LOADING:
                smallProgressBar.setVisibility(INVISIBLE);
                fullPauseIcon.setVisibility(INVISIBLE);
                fullProgressArea.setVisibility(INVISIBLE);

                fullPlayerIcon.setImageResource(R.mipmap.loading);
                fullPlayerIcon.setVisibility(VISIBLE);
                fullPlayerText.setText("加载中...");
                fullPlayerText.setVisibility(VISIBLE);

                smallPlayerIcon.setImageResource(R.mipmap.loading);
                smallPlayerIcon.setVisibility(VISIBLE);
                smallPlayerText.setText("加载中...");
                smallPlayerText.setVisibility(VISIBLE);

                startAnimation(FULL.equals(mScreenState) ? fullPlayerIcon : smallPlayerIcon);
                break;
            case PLAYING:
                fullPlayerIcon.setVisibility(INVISIBLE);
                fullPlayerText.setVisibility(INVISIBLE);
                smallPlayerIcon.setVisibility(INVISIBLE);
                smallPlayerText.setVisibility(INVISIBLE);
                fullPauseIcon.setVisibility(INVISIBLE);
                fullProgressArea.setVisibility(INVISIBLE);
                break;
            case PAUSE:
                fullPlayerIcon.setVisibility(INVISIBLE);
                fullPlayerText.setVisibility(INVISIBLE);
                smallPlayerIcon.setVisibility(INVISIBLE);
                smallPlayerText.setVisibility(INVISIBLE);
                fullPauseIcon.setVisibility(VISIBLE);
                fullProgressArea.setVisibility(INVISIBLE);
                break;
            case ERROR:
                smallProgressBar.setVisibility(INVISIBLE);
                fullPauseIcon.setVisibility(INVISIBLE);
                fullProgressArea.setVisibility(INVISIBLE);

                fullPlayerIcon.setImageResource(R.mipmap.refresh);
                fullPlayerIcon.setVisibility(VISIBLE);
                fullPlayerText.setText(waringInfo);
                fullPlayerText.setVisibility(VISIBLE);

                smallPlayerIcon.setImageResource(R.mipmap.refresh);
                smallPlayerIcon.setVisibility(VISIBLE);
                smallPlayerText.setText(waringInfo);
                smallPlayerText.setVisibility(VISIBLE);
                break;
            default:
                break;
        }
    }

    private void startAnimation(View view) {
        RotateAnimation animation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(-1);
        view.startAnimation(animation);
    }
}
