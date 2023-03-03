package com.luo.app.player;

/**
 * desc :
 * create by 公子赓
 * on 2023/2/19 13:42
 */
public interface ILuoPlayerListener {

    void onPlaySuccess();

    void onPlayComplete();

    void onPlayError(int what, int extra);

    void upDateProgress(long currentPlayPosition, long duration);
}
