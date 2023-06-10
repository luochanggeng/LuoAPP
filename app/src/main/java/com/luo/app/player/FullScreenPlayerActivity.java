package com.luo.app.player;

import android.os.Parcelable;

import com.luo.app.R;
import com.luo.app.base.BaseActivity;
import com.luo.app.network.resultBean.ContentDetail;
import com.luo.app.player.core.ILuoPlayerListener;
import com.luo.app.player.core.LuoPlayer;
import com.luo.app.player.core.LuoPlayerScreen;

/**
 * desc :
 * Created by luo
 * on 2023/3/11
 */
public class FullScreenPlayerActivity extends BaseActivity {

    private LuoPlayer luoPlayer;

    private LuoPlayerScreen luoPlayerScreen;

    private ContentDetail contentDetail;

    @Override
    protected void initIntent() {
        super.initIntent();
        contentDetail = getIntent().getParcelableExtra("contentDetail");
    }

    @Override
    protected void initLayout() {

        setContentView(R.layout.activity_full_screen_player);

        luoPlayerScreen = findViewById(R.id.luoPlayerScreen);
        luoPlayerScreen.setSwitchScreenAvailable(false);
        initPlayer();
    }

    private void initPlayer() {
        luoPlayer = new LuoPlayer();
        luoPlayer.setScreenView(luoPlayerScreen);
        luoPlayer.setLuoPlayerListener(new ILuoPlayerListener() {
            @Override
            public void onPlayComplete() {
                //请求播放下一个资产
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(contentDetail != null){
            luoPlayer.setDataSource(contentDetail.getPlayUrl());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        luoPlayer.release();
        finish();
    }
}
