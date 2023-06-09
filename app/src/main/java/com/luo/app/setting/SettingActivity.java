package com.luo.app.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.luo.app.R;
import com.luo.app.base.BaseActivity;

/**
 * desc :
 * Created by luo
 * on 2023/3/3
 */
public class SettingActivity extends BaseActivity {

    private TextView tvIpAddress;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_setting);
        tvIpAddress = findViewById(R.id.tv_ip_address);
        setListener();

        SharedPreferences loader = getSharedPreferences("loader", Context.MODE_PRIVATE);
        String ip = loader.getString("IP", "");
        if(TextUtils.isEmpty(ip)){
            ip = "http://0.0.0.0:0/luo/";
        }
        tvIpAddress.setText(ip);
    }

    private void setListener() {
        tvIpAddress.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                ((TextView)v).setTextColor(getResources().getColor(R.color.black));
            }else{
                ((TextView)v).setTextColor(getResources().getColor(R.color.white));
            }
        });
        tvIpAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
