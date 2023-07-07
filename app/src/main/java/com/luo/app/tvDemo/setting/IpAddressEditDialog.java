package com.luo.app.tvDemo.setting;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.luo.app.R;

/**
 * desc :
 * Created by luo
 * on 2023/6/9
 */
public class IpAddressEditDialog extends Dialog implements View.OnKeyListener, View.OnFocusChangeListener {

    private final Context mContext;

    String[] dataArray = new String[5];
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;
    private TextView tv10;
    private TextView tv11;
    private TextView tv12;
    private TextView tv13;
    private TextView tv14;
    private TextView tv15;
    private TextView tv16;

    public IpAddressEditDialog(@NonNull Context context) {
        super(context, R.style.ipAddressEditDialog);

        mContext = context;

        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_edit_ip, null);
        setContentView(inflate);
        initView(inflate);
    }

    @SuppressLint("SetTextI18n")
    public void setData(String url) {
        String ip = "";
        if (!TextUtils.isEmpty(url)) {
            if (url.contains("/")) {
                String[] split = url.split("/");
                if (split != null && split.length > 2) {
                    ip = split[2];
                }
            }
        }
        if (!TextUtils.isEmpty(ip)) {
            if (ip.contains(".")) {
                String[] split = ip.split("\\.");

                for (int i = 0; i < split.length; i++) {
                    String splitString = split[i];
                    if (i == split.length - 1) {
                        if (splitString.contains(":")) {
                            String[] split1 = splitString.split(":");
                            String splitString1 = split1[0];
                            if (splitString1.length() <= 1) {
                                splitString1 = "00" + splitString1;
                            } else if (splitString1.length() <= 2) {
                                splitString1 = "0" + splitString1;
                            }
                            dataArray[i] = splitString1;
                            dataArray[i + 1] = split1[1];
                        }
                    } else {
                        if (splitString.length() <= 1) {
                            splitString = "00" + splitString;
                        } else if (splitString.length() <= 2) {
                            splitString = "0" + splitString;
                        }
                        dataArray[i] = splitString;
                    }
                }
            }
        }

        String ip1 = dataArray[0];
        tv1.setText(ip1.charAt(0) + "");
        tv2.setText(ip1.charAt(1) + "");
        tv3.setText(ip1.charAt(2) + "");

        String ip2 = dataArray[1];
        tv4.setText(ip2.charAt(0) + "");
        tv5.setText(ip2.charAt(1) + "");
        tv6.setText(ip2.charAt(2) + "");

        String ip3 = dataArray[2];
        tv7.setText(ip3.charAt(0) + "");
        tv8.setText(ip3.charAt(1) + "");
        tv9.setText(ip3.charAt(2) + "");

        String ip4 = dataArray[3];
        tv10.setText(ip4.charAt(0) + "");
        tv11.setText(ip4.charAt(1) + "");
        tv12.setText(ip4.charAt(2) + "");

        String port = dataArray[4];
        tv13.setText(port.charAt(0) + "");
        tv14.setText(port.charAt(1) + "");
        tv15.setText(port.charAt(2) + "");
        tv16.setText(port.charAt(3) + "");
    }

    private void initView(View inflate) {
        tv1 = inflate.findViewById(R.id.tv1);
        tv1.setOnKeyListener(this);
        tv1.setOnFocusChangeListener(this);
        tv2 = inflate.findViewById(R.id.tv2);
        tv2.setOnKeyListener(this);
        tv2.setOnFocusChangeListener(this);
        tv3 = inflate.findViewById(R.id.tv3);
        tv3.setOnKeyListener(this);
        tv3.setOnFocusChangeListener(this);
        tv4 = inflate.findViewById(R.id.tv4);
        tv4.setOnKeyListener(this);
        tv4.setOnFocusChangeListener(this);
        tv5 = inflate.findViewById(R.id.tv5);
        tv5.setOnKeyListener(this);
        tv5.setOnFocusChangeListener(this);
        tv6 = inflate.findViewById(R.id.tv6);
        tv6.setOnKeyListener(this);
        tv6.setOnFocusChangeListener(this);
        tv7 = inflate.findViewById(R.id.tv7);
        tv7.setOnKeyListener(this);
        tv7.setOnFocusChangeListener(this);
        tv8 = inflate.findViewById(R.id.tv8);
        tv8.setOnKeyListener(this);
        tv8.setOnFocusChangeListener(this);
        tv9 = inflate.findViewById(R.id.tv9);
        tv9.setOnKeyListener(this);
        tv9.setOnFocusChangeListener(this);
        tv10 = inflate.findViewById(R.id.tv10);
        tv10.setOnKeyListener(this);
        tv10.setOnFocusChangeListener(this);
        tv11 = inflate.findViewById(R.id.tv11);
        tv11.setOnKeyListener(this);
        tv11.setOnFocusChangeListener(this);
        tv12 = inflate.findViewById(R.id.tv12);
        tv12.setOnKeyListener(this);
        tv12.setOnFocusChangeListener(this);
        tv13 = inflate.findViewById(R.id.tv13);
        tv13.setOnKeyListener(this);
        tv13.setOnFocusChangeListener(this);
        tv14 = inflate.findViewById(R.id.tv14);
        tv14.setOnKeyListener(this);
        tv14.setOnFocusChangeListener(this);
        tv15 = inflate.findViewById(R.id.tv15);
        tv15.setOnKeyListener(this);
        tv15.setOnFocusChangeListener(this);
        tv16 = inflate.findViewById(R.id.tv16);
        tv16.setOnKeyListener(this);
        tv16.setOnFocusChangeListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                String s = ((TextView) v).getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    int integer = Integer.parseInt(s);
                    integer = integer + 1;
                    if (integer >= 10) {
                        integer = 0;
                    }
                    ((TextView) v).setText(integer + "");
                }
            }
            if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                String s = ((TextView) v).getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    int integer = Integer.parseInt(s);
                    integer = integer - 1;
                    if (integer < 0) {
                        integer = 9;
                    }
                    ((TextView) v).setText(integer + "");
                }
            }
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (mOnDismissListener != null) {
                    mOnDismissListener.onDismiss(getSetIPAddress());
                }
                dismiss();
                return true;
            }
        }
        return false;
    }

    private String getSetIPAddress() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://");
        String s1 = tv1.getText().toString();

        boolean first = false;

        if (!"0".equals(s1)) {
            stringBuilder.append(s1);
            first = true;
        }
        String s2 = tv2.getText().toString();
        if (!"0".equals(s2)) {
            first = true;
            stringBuilder.append(s2);
        } else {
            if (first) {
                stringBuilder.append(s2);
            }
        }
        String s3 = tv3.getText().toString();
        stringBuilder.append(s3);

        stringBuilder.append(".");

        boolean second = false;

        String s4 = tv4.getText().toString();
        if (!"0".equals(s4)) {
            stringBuilder.append(s4);
            second = true;
        }
        String s5 = tv5.getText().toString();
        if (!"0".equals(s5)) {
            second = true;
            stringBuilder.append(s5);
        } else {
            if (second) {
                stringBuilder.append(s5);
            }
        }
        String s6 = tv6.getText().toString();
        stringBuilder.append(s6);

        stringBuilder.append(".");

        boolean three = false;

        String s7 = tv7.getText().toString();
        if (!"0".equals(s7)) {
            three = true;
            stringBuilder.append(s7);
        }
        String s8 = tv8.getText().toString();
        if (!"0".equals(s8)) {
            three = true;
            stringBuilder.append(s8);
        } else {
            if (three) {
                stringBuilder.append(s8);
            }
        }
        String s9 = tv9.getText().toString();
        stringBuilder.append(s9);

        stringBuilder.append(".");

        boolean four = false;

        String s10 = tv10.getText().toString();
        if (!"0".equals(s10)) {
            four = true;
            stringBuilder.append(s10);
        }
        String s11 = tv11.getText().toString();
        if (!"0".equals(s11)) {
            four = true;
            stringBuilder.append(s11);
        } else {
            if (four) {
                stringBuilder.append(s11);
            }
        }
        String s12 = tv12.getText().toString();
        stringBuilder.append(s12);

        stringBuilder.append(":");
        String s13 = tv13.getText().toString();
        if (TextUtils.isEmpty(s13)) {
            s13 = "0";
        }
        stringBuilder.append(s13);
        String s14 = tv14.getText().toString();
        if (TextUtils.isEmpty(s14)) {
            s14 = "0";
        }
        stringBuilder.append(s14);
        String s15 = tv15.getText().toString();
        if (TextUtils.isEmpty(s15)) {
            s15 = "0";
        }
        stringBuilder.append(s15);
        String s16 = tv16.getText().toString();
        if (TextUtils.isEmpty(s16)) {
            s16 = "0";
        }
        stringBuilder.append(s16);
        stringBuilder.append("/luo/");

        return stringBuilder.toString();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            ((TextView) v).setTextColor(mContext.getResources().getColor(R.color.black));
            LinearLayout parent = (LinearLayout) v.getParent();
            parent.getChildAt(0).setVisibility(View.VISIBLE);
            parent.getChildAt(2).setVisibility(View.VISIBLE);
        } else {
            ((TextView) v).setTextColor(mContext.getResources().getColor(R.color.white));
            LinearLayout parent = (LinearLayout) v.getParent();
            parent.getChildAt(0).setVisibility(View.INVISIBLE);
            parent.getChildAt(2).setVisibility(View.INVISIBLE);
        }
    }

    interface OnDismissListener {
        void onDismiss(String ip);
    }

    private OnDismissListener mOnDismissListener;

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }
}
