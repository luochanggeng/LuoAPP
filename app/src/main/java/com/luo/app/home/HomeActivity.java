package com.luo.app.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luo.app.R;
import com.luo.app.base.BaseActivity;
import com.luo.app.network.resultBean.Folder;
import com.luo.app.network.resultBean.FolderInfo;
import com.luo.app.player.LuoPlayer;
import com.luo.app.player.LuoPlayerScreen;
import com.luo.app.setting.SettingActivity;
import com.luo.app.utils.AnimationUtils;
import com.luo.app.utils.CommonUtils;
import com.luo.app.utils.ImageLoader;
import com.luo.app.widget.FocusFlashImageView;

import java.util.List;

/**
 * desc :
 * create by 公子赓
 * on 2023/2/18 22:22
 */
public class HomeActivity extends BaseActivity implements
        View.OnFocusChangeListener, View.OnClickListener, HomeContract.IHomeView {

    private ImageView ivLogo;
    //网络状态图标
    private ImageView ivNetState;
    //日期
    private TextView tvDate;
    //星期
    private TextView tvWeek;
    //时间
    private TextView tvTime;
    //设置按钮
    private LinearLayout llSettingArea;
    //播放器小视频窗
    private RelativeLayout rlPlayer;
    private LuoPlayerScreen luoPlayerScreen;
    private ImageView ivPlayerMask;
    //首页展示栏目1
    private ImageView ivFolder1;
    //首页展示栏目2
    private ImageView ivFolder2;
    //首页展示栏目3
    private ImageView ivFolder3;
    //首页展示栏目4
    private ImageView ivFolder4;
    //首页展示栏目5
    private ImageView ivFolder5;
    //首页展示栏目6
    private ImageView ivFolder6;

    private LuoPlayer luoPlayer;
    private HomeContract.IHomePresenter presenter;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_home);

        ivLogo = findViewById(R.id.iv_logo);

        ivNetState = findViewById(R.id.iv_net_state);
        tvDate = findViewById(R.id.tv_date);
        tvWeek = findViewById(R.id.tv_week);
        tvTime = findViewById(R.id.tv_time);

        llSettingArea = findViewById(R.id.ll_setting_area);
        llSettingArea.setOnFocusChangeListener(this);
        llSettingArea.setOnClickListener(this);

        rlPlayer = findViewById(R.id.rl_player);
        rlPlayer.setOnFocusChangeListener(this);
        rlPlayer.setOnClickListener(this);
        ivPlayerMask = findViewById(R.id.iv_player_mask);
        luoPlayerScreen = findViewById(R.id.luoPlayerScreen);
        luoPlayerScreen.setSwitchScreenAvailable(true);

        ivFolder1 = findViewById(R.id.iv_folder1);
        ivFolder1.setOnFocusChangeListener(this);
        ivFolder1.setOnClickListener(this);

        ivFolder2 = findViewById(R.id.iv_folder2);
        ivFolder2.setOnFocusChangeListener(this);
        ivFolder2.setOnClickListener(this);

        ivFolder3 = findViewById(R.id.iv_folder3);
        ivFolder3.setOnFocusChangeListener(this);
        ivFolder3.setOnClickListener(this);

        ivFolder4 = findViewById(R.id.iv_folder4);
        ivFolder4.setOnFocusChangeListener(this);
        ivFolder4.setOnClickListener(this);

        ivFolder5 = findViewById(R.id.iv_folder5);
        ivFolder5.setOnFocusChangeListener(this);
        ivFolder5.setOnClickListener(this);

        ivFolder6 = findViewById(R.id.iv_folder6);
        ivFolder6.setOnFocusChangeListener(this);
        ivFolder6.setOnClickListener(this);

        presenter = new HomePresenter(this);

        updateTime();

        initPlayer();
    }

    private void initPlayer() {
        luoPlayer = new LuoPlayer();
        luoPlayer.setScreenView(luoPlayerScreen);
    }

    @Override
    protected void updateNetWorkState(boolean isHaveNetwork) {
        ivNetState.setImageResource(isHaveNetwork ? R.mipmap.net_connect_icon : R.mipmap.net_disconnect_icon);
    }

    @Override
    protected void updateTime() {
        tvWeek.setText(CommonUtils.getWeekInfo());
        String date = CommonUtils.getDate();
        if(!TextUtils.isEmpty(date)){
            if(date.contains(" ")){
                String[] split = date.split(" ");
                if(split != null && split.length >= 2){
                    tvDate.setText(split[0]);
                    tvTime.setText(split[1]);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.queryFolderInfo();
        luoPlayer.setDataSource("");
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            if(v.getId() == R.id.ll_setting_area){
                ((ImageView)llSettingArea.getChildAt(0)).setImageResource(R.mipmap.setting_icon_focus);
                ((TextView)llSettingArea.getChildAt(1)).setTextColor(getResources().getColor(R.color.black));
            }else if(v.getId() == R.id.rl_player){
                ivPlayerMask.setVisibility(View.VISIBLE);
            }else{
                AnimationUtils.showOnFocusAnimation(v);
                if(v instanceof FocusFlashImageView){
                    ((FocusFlashImageView)v).startShimmer();
                }
            }
        }else{
            if(v.getId() == R.id.ll_setting_area){
                ((ImageView)llSettingArea.getChildAt(0)).setImageResource(R.mipmap.setting_icon);
                ((TextView)llSettingArea.getChildAt(1)).setTextColor(getResources().getColor(R.color.white));
            }else if(v.getId() == R.id.rl_player){
                ivPlayerMask.setVisibility(View.INVISIBLE);
            }else {
                AnimationUtils.showLooseFocusAnimation(v);
                if(v instanceof FocusFlashImageView){
                    ((FocusFlashImageView)v).stopShimmer();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        luoPlayer.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.releaseView();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_setting_area:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.rl_player:
                luoPlayerScreen.switchScreenToFull(rlPlayer);
                break;
            default:
                break;
        }
    }

    @Override
    public void showFolderInfo(FolderInfo folderInfo) {
        if(folderInfo == null){
            return;
        }
        ImageLoader.loadImage(this, folderInfo.getFolderLogo(), ivLogo, R.color.trans);

        List<Folder> folderList = folderInfo.getFolderList();

        if(folderList == null || folderList.isEmpty()){
            return;
        }
        ImageLoader.loadImage(this, folderList.get(0).getFolderImage(), ivFolder1, R.mipmap.default_bg);
        ImageLoader.loadImage(this, folderList.get(1).getFolderImage(), ivFolder2, R.mipmap.default_bg);
        ImageLoader.loadImage(this, folderList.get(2).getFolderImage(), ivFolder3, R.mipmap.default_bg);
        ImageLoader.loadImage(this, folderList.get(3).getFolderImage(), ivFolder4, R.mipmap.default_bg);
        ImageLoader.loadImage(this, folderList.get(4).getFolderImage(), ivFolder5, R.mipmap.default_bg);
        ImageLoader.loadImage(this, folderList.get(5).getFolderImage(), ivFolder6, R.mipmap.default_bg);
    }
}
