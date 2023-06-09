package com.luo.app.setting;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;

import com.luo.app.R;

/**
 * desc :
 * Created by luo
 * on 2023/6/9
 */
public class IpAddressEditDialog extends Dialog {

    public IpAddressEditDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_edit_ip);
    }
}
