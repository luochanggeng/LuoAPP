package com.luo.app.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.luo.app.R;
import com.luo.app.base.BaseActivity;
import com.luo.app.home.HomeActivity;


/**
 * desc :
 * create by 公子赓
 * on 2023/2/18 21:06
 */
public class LoginActivity extends BaseActivity implements LoginContract.ISplashView {

    private EditText etInputPassword;

    private LoginContract.ISplashPresenter presenter ;

    private String mPassWord ;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_splash);
        etInputPassword = findViewById(R.id.et_input_password);

        etInputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String trim = s.toString().trim();
                if(!TextUtils.isEmpty(mPassWord) && mPassWord.equals(trim)){
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }else{
                    etInputPassword.setText("");
                }
            }
        });
        etInputPassword.setOnClickListener(v ->
                Toast.makeText(LoginActivity.this, "aaa", Toast.LENGTH_LONG).show());

        presenter = new LoginPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.queryPassword();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
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