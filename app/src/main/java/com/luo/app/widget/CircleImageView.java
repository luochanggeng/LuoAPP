package com.luo.app.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * desc :
 * create by 公子赓
 * on 2023/2/21 20:08
 */
@SuppressLint("AppCompatCustomView")
public class CircleImageView extends ImageView {

    //控件宽高
    private float mViewWidth = 0, mViewHeight = 0;
    //圆形半径
    private float radius;
    //画图
    private final Paint paint;
    //图矩阵
    private final Matrix matrix;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        //设置抗锯齿
        paint.setAntiAlias(true);
        //初始化缩放矩阵
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            super.onDraw(canvas);
            return;
        }
        if (drawable instanceof BitmapDrawable) {
            // 将着色器设置给画笔
            paint.setShader(initBitmapShader((BitmapDrawable) drawable));
            // 使用画笔在画布上画圆
            canvas.drawCircle(mViewWidth / 2, mViewHeight / 2, radius, paint);
        }else {
            super.onDraw(canvas);
        }
    }

    /**
     * 获取ImageView中资源图片的Bitmap，利用Bitmap初始化图片着色器,
     * 通过缩放矩阵将原资源图片缩放到铺满整个绘制区域，避免边界填充
     */
    private BitmapShader initBitmapShader(BitmapDrawable drawable) {
        Bitmap bitmap = drawable.getBitmap();
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = Math.max(mViewWidth / bitmap.getWidth(), mViewHeight / bitmap.getHeight());
        //将图片宽高等比例缩放，避免拉伸
        matrix.setScale(scale, scale);
        bitmapShader.setLocalMatrix(matrix);
        return bitmapShader;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        if (mViewWidth == 0) {
            mViewWidth = getWidth();
            mViewHeight = getHeight();
            if (mViewWidth > 0) {
                radius = Math.min(mViewWidth, mViewHeight) / 2;
            }
        }
    }
}
