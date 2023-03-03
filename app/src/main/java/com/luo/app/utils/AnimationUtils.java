package com.luo.app.utils;

import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.OvershootInterpolator;

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

}
