package com.luo.app.splash;

import android.content.Intent;
import android.widget.LinearLayout;

import com.luo.app.R;
import com.luo.app.base.BaseActivity;
import com.luo.app.home.HomeActivity;


/**
 * desc :
 * create by 公子赓
 * on 2023/2/18 21:06
 */
public class SplashActivity extends BaseActivity implements SplashContract.ISplashView {

    private LinearLayout login;

    private SplashContract.ISplashPresenter presenter ;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_splash);
        login = findViewById(R.id.ll_login);
        presenter = new SplashPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.queryPassword();
        login.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            }
        }, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void showPassword(String password) {

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