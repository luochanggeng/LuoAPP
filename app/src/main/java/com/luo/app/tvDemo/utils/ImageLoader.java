package com.luo.app.tvDemo.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luo.sdk.network.NetWorkUtil;

/**
 * desc :
 * Created by luo
 * on 2023/3/2
 */
public class ImageLoader {

    /**
     * 加载网络图片
     */
    public static void loadImage(Context context, String url, ImageView imageView, int defaultLogo) {
        if (verificationContextAvailability(imageView)) {
            return;
        }
        url = NetWorkUtil.getInstance().getIPAddress() + url;
        Glide.with(context)
                .asBitmap()
                .load(url)
                .error(defaultLogo)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .skipMemoryCache(false)
                .placeholder(defaultLogo)
                .into(imageView);
    }

    public static void loadLoadingGif(Context context, String url, ImageView imageView, int defaultLogo) {
        if (verificationContextAvailability(imageView)) {
            return;
        }
        Glide.with(context)
                .asGif()
                .load(url)
                .placeholder(defaultLogo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(false)
                .into(imageView);
    }

    /**
     * 加载网络图片,指定加载的宽高
     */
    public static void loadImageWithSize(Context context, String url, ImageView imageView, int width, int height, int defaultLogo) {
        if (verificationContextAvailability(imageView)) {
            return;
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .error(defaultLogo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(false)
                .placeholder(defaultLogo)
                .override(width, height)
                .into(imageView);
    }

    /**
     * 验证view中含带的context是否可用，
     * 防止Glide加载报IllegalArgumentException("You cannot start a load for a destroyed activity")
     *
     * @param view 当前装载图片的控件
     * @return false表示可用；true表示不可用
     */
    private static boolean verificationContextAvailability(View view) {
        if (view == null || view.getContext() == null) {
            return true;
        }
        if (view.getContext() instanceof Activity || view.getContext() instanceof FragmentActivity) {
            return ((Activity) view.getContext()).isDestroyed();
        }
        return false;
    }
}
