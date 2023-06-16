package com.luo.app.setting;

import android.content.Context;
import android.content.DialogInterface;
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

    private IpAddressEditDialog ipAddressEditDialog;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_setting);
        tvIpAddress = findViewById(R.id.tv_ip_address);
        setListener();

        SharedPreferences loader = getSharedPreferences("loader", Context.MODE_PRIVATE);
        String ip = loader.getString("IP", "");
        if(TextUtils.isEmpty(ip)){
            ip = "http://0.0.0.0:0000/luo/";
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
        tvIpAddress.setOnClickListener(v -> {
            if (ipAddressEditDialog == null) {
                ipAddressEditDialog = new IpAddressEditDialog(SettingActivity.this);
                ipAddressEditDialog.setData(tvIpAddress.getText().toString());
                ipAddressEditDialog.setOnDismissListener(new IpAddressEditDialog.OnDismissListener() {
                    @Override
                    public void onDismiss(String ip) {
                        tvIpAddress.setText(ip);
                        SharedPreferences loader = getSharedPreferences("loader", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = loader.edit();
                        edit.putString("IP", ip);
                        edit.apply();
                    }
                });
            }
            if (!ipAddressEditDialog.isShowing()) {
                ipAddressEditDialog.show();
            }
        });
    }
}
