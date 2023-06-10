package com.luo.app.player.core;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.luo.app.network.NetWorkUtil;

import java.io.IOException;

/**
 * desc :
 * create by 公子赓
 * on 2023/2/19 13:42
 */
public class LuoPlayer implements SurfaceHolder.Callback,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener, Handler.Callback {

    private static final int MESSAGE_PLAY_SUCCESS = 0x01;

    private static final int MESSAGE_PLAY_ERROR = 0x02;

    private static final int MESSAGE_PLAY_COMPLETE = 0x03;

    private static final int MESSAGE_UPDATE_PROGRESS = 0x04;

    private LuoPlayerScreen luoPlayerScreen ;

    private ILuoPlayerListener mLuoPlayerListener;

    private MediaPlayer mediaPlayer;

    private boolean isSurfaceCreated = false;

    private boolean isPrepareAsync = false;

    private String mPlayUrl ;

    private long mPlayerDuration ;

    private Handler mUIHandler ;

    private boolean isPlaying = false;

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        switch (msg.what){
            case MESSAGE_PLAY_SUCCESS:
                //播放成功
                if(luoPlayerScreen != null){
                    luoPlayerScreen.onPlaySuccess();
                }
                break;
            case MESSAGE_UPDATE_PROGRESS:
                if(luoPlayerScreen != null){
                    luoPlayerScreen.upDateProgress(getPlayerPosition(), getPlayerDuration());
                }
                if(isPlaying){
                    mUIHandler.sendEmptyMessageDelayed(MESSAGE_UPDATE_PROGRESS, 1000);
                }
                break;
            case MESSAGE_PLAY_ERROR:
                if(luoPlayerScreen != null){
                    luoPlayerScreen.onPlayError(msg.arg1, msg.arg2);
                }
                break;
            case MESSAGE_PLAY_COMPLETE:
                //播放完成
                if(luoPlayerScreen != null){
                    boolean b = luoPlayerScreen.onPlayComplete();
                    if(b && mLuoPlayerListener != null){
                        mLuoPlayerListener.onPlayComplete();
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }

    public LuoPlayer(){
        mUIHandler = new Handler(Looper.getMainLooper(), this);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //设置播放的时候视频常亮
        mediaPlayer.setScreenOnWhilePlaying(true);

        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mPlayerDuration = 0L ;
    }

    public void setScreenView(@NonNull LuoPlayerScreen screen){
        luoPlayerScreen = screen;
        luoPlayerScreen.setLuoPlayer(this);
        SurfaceView surfaceView = new SurfaceView(screen.getContext());
        ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        screen.addView(surfaceView, 0, layoutParams);

        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);
    }

    public void setLuoPlayerListener(ILuoPlayerListener luoPlayerListener){
        mLuoPlayerListener = luoPlayerListener;
    }

    public void setDataSource(String url){
        mPlayUrl = NetWorkUtil.getInstance().getIPAddress() + url ;
        //mPlayUrl = NetWorkUtil.getInstance().getIPAddress() + "a/a.m3u8" ;
        Log.i("zhang", "mPlayUrl = " + mPlayUrl);
        mediaPlayer.reset();
        isPrepareAsync = false;
        isPlaying = false;
        mPlayerDuration = 0L ;

        if(isSurfaceCreated){
            try {
                mediaPlayer.setDataSource(mPlayUrl);
                mediaPlayer.prepareAsync();
                isPrepareAsync = true;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void start(){
        mediaPlayer.start();
        isPlaying = true;
        mPlayerDuration = mediaPlayer.getDuration() ;
        if(mUIHandler != null){
            mUIHandler.removeMessages(MESSAGE_PLAY_SUCCESS);
            mUIHandler.sendEmptyMessage(MESSAGE_PLAY_SUCCESS);

            mUIHandler.removeMessages(MESSAGE_UPDATE_PROGRESS);
            mUIHandler.sendEmptyMessageDelayed(MESSAGE_UPDATE_PROGRESS, 1000);
        }
    }

    public void pause(){
        mediaPlayer.pause();
        isPlaying = false;
        if(mUIHandler != null){
            mUIHandler.removeMessages(MESSAGE_UPDATE_PROGRESS);
        }
    }

    public void resume(){
        if(mediaPlayer != null){
            mediaPlayer.start();
            isPlaying = true;
            if(mUIHandler != null){
                mUIHandler.removeMessages(MESSAGE_UPDATE_PROGRESS);
                mUIHandler.sendEmptyMessageDelayed(MESSAGE_UPDATE_PROGRESS, 1000);
            }
        }
    }

    public void seekTo(int time){
        mediaPlayer.seekTo(time);
    }

    public void release(){
        isPlaying = false;
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if(mUIHandler != null){
            mUIHandler.removeCallbacksAndMessages(null);
            mUIHandler = null;
        }
        luoPlayerScreen.setLuoPlayer(null);
    }

    public long getPlayerPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    public long getPlayerDuration(){
        return mPlayerDuration ;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("zhang", "surfaceCreated");
        mediaPlayer.setDisplay(holder);
        isSurfaceCreated = true;
        if(!isPrepareAsync && !TextUtils.isEmpty(mPlayUrl)){
            try {
                mediaPlayer.setDataSource(mPlayUrl);
                mediaPlayer.prepareAsync();
                isPrepareAsync = true;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }else{
            start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("zhang", "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isSurfaceCreated = false;
        //pause();
        Log.i("zhang", "surfaceDestroyed");
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        isPlaying = false;
        if(mUIHandler != null){
            mUIHandler.removeMessages(MESSAGE_UPDATE_PROGRESS);
            mUIHandler.removeMessages(MESSAGE_PLAY_COMPLETE);
            mUIHandler.sendEmptyMessage(MESSAGE_PLAY_COMPLETE);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        isPlaying = false;
        if(mUIHandler != null){
            mUIHandler.removeMessages(MESSAGE_UPDATE_PROGRESS);
            mUIHandler.removeMessages(MESSAGE_PLAY_ERROR);
            mUIHandler.sendMessage(Message.obtain(mUIHandler, MESSAGE_PLAY_ERROR, what, extra));
        }
        return false;
    }
}
