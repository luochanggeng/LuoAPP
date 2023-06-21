package com.luo.app.utils;

import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;

import com.luo.app.R;
import com.luo.app.base.MyApplication;

/**
 * desc :
 * Created by luo
 * on 2023/3/2
 */
public class AnimationUtils {

    private final static float scale_ratio = 1.1F;

    public static void showOnFocusAnimation(View view) {
        ViewPropertyAnimator propertyAnimation = view.animate();
        propertyAnimation.setDuration(300L);
        propertyAnimation.scaleX(scale_ratio);
        propertyAnimation.scaleY(scale_ratio);
        propertyAnimation.setInterpolator(new OvershootInterpolator());
        propertyAnimation.start();
    }


    public static void showLooseFocusAnimation(View view) {
        ViewPropertyAnimator propertyAnimation = view.animate();
        propertyAnimation.setDuration(180L);
        propertyAnimation.scaleX(1.0F);
        propertyAnimation.scaleY(1.0F);
        propertyAnimation.translationX(0.0F);
        propertyAnimation.translationY(0.0F);
        propertyAnimation.start();
    }

    public static Animation showViewFromBottom() {
        return android.view.animation.AnimationUtils.makeInChildBottomAnimation(MyApplication.getInstance());
    }

    public static Animation hideViewFromBottom() {
        Animation a;
        a = android.view.animation.AnimationUtils.loadAnimation(MyApplication.getInstance(), R.anim.slide_out_bottom);
        a.setInterpolator(new AccelerateInterpolator());
        a.setStartTime(android.view.animation.AnimationUtils.currentAnimationTimeMillis());
        return a;
    }

}
