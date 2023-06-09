package com.luo.app.login;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luo.app.R;
import com.luo.app.base.BaseActivity;
import com.luo.app.home.HomeActivity;
import com.luo.app.setting.SettingActivity;


/**
 * desc :
 * create by 公子赓
 * on 2023/2/18 21:06
 */
public class LoginActivity extends BaseActivity implements LoginContract.ISplashView {

    private TextView tvLogin;
    //设置按钮
    private LinearLayout llSettingArea;

    private LoginContract.ISplashPresenter presenter ;

    private String mPassWord ;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_splash);
        tvLogin = findViewById(R.id.tv_login);
        llSettingArea = findViewById(R.id.ll_setting_area);
        initListener();
        presenter = new LoginPresenter(this);
    }

    private void initListener() {
        tvLogin.setOnClickListener(v -> {
            if(!TextUtils.isEmpty(mPassWord)){
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }else{
                Toast.makeText(LoginActivity.this, "系统关闭", Toast.LENGTH_LONG).show();
            }
        });
        tvLogin.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                ((TextView)v).setTextColor(getResources().getColor(R.color.black));
            }else{
                ((TextView)v).setTextColor(getResources().getColor(R.color.white));
            }
        });
        llSettingArea.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                ((ImageView)llSettingArea.getChildAt(0)).setImageResource(R.mipmap.setting_icon_focus);
                ((TextView)llSettingArea.getChildAt(1)).setTextColor(getResources().getColor(R.color.black));
            }else{
                ((ImageView)llSettingArea.getChildAt(0)).setImageResource(R.mipmap.setting_icon);
                ((TextView)llSettingArea.getChildAt(1)).setTextColor(getResources().getColor(R.color.white));
            }
        });
        llSettingArea.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SettingActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.queryPassword();
        tvLogin.requestFocus();
    }

    @Override
    public void showPassword(String password) {
        mPassWord = password;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null){
            presenter.releaseView();
            presenter = null;
        }
    }
}