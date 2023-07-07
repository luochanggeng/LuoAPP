package com.luo.app.tvDemo.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.luo.app.R;

/**
 * desc : 获取焦点 闪光ImageView
 * create by 公子赓
 * on 2023/2/21 20:08
 */
@SuppressLint("AppCompatCustomView")
public class FocusFlashImageView extends ImageView {

    private Shader mGradient;
    private Matrix mGradientMatrix;
    private Paint mPaint;
    private Paint maskPaint;
    private Paint zonePaint;
    private int mViewWidth = 0, mViewHeight = 0;
    private float mTranslateX = 0, mTranslateY = 0;
    private boolean mAnimating = false;
    private Rect rect;
    private ValueAnimator valueAnimator;
    private Path mPath;
    private RectF rectF;

    /**
     * 闪光的宽度和控件宽度的比例
     */
    private final float flickerX = 0.8f;

    /**
     * 闪光的角度  0 为 90°
     */
    private final float flickerY = 0.3f;

    /**
     * 圆角
     */
    private final float defaultRadius = getContext().getResources().getDimensionPixelSize(R.dimen.px_8);

    private final int padding = getContext().getResources().getDimensionPixelOffset(R.dimen.px_3);

    public FocusFlashImageView(Context context) {
        super(context);
        init();
    }

    public FocusFlashImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FocusFlashImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        rect = new Rect();
        rectF = new RectF();
        mPaint = new Paint();
        mPath = new Path();
        setScaleType(ScaleType.FIT_XY);
        setImageResource(R.mipmap.default_bg);
        initGradientAnimator();

        maskPaint = new Paint();
        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        zonePaint = new Paint();
        // 抗锯齿
        zonePaint.setAntiAlias(true);
        zonePaint.setColor(Color.WHITE);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setPaddingAndRadius(getPaddingTop(),getPaddingLeft(),getPaddingRight(),getPaddingBottom());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getWidth();
            mViewHeight = getHeight();
            if (mViewWidth > 0) {
                //亮光闪过
                mGradient = new LinearGradient(0, 0, mViewWidth * flickerX, mViewWidth * flickerX * flickerY, new int[]{0x00ffffff, 0xAAFFFFFF, 0x00ffffff}, null, Shader.TileMode.CLAMP);
                mPaint.setShader(mGradient);
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
                mGradientMatrix = new Matrix();
                mGradientMatrix.setTranslate(-mViewWidth * flickerX, -mViewWidth * flickerX * flickerY);
                mGradient.setLocalMatrix(mGradientMatrix);

                setPaddingAndRadius(getPaddingTop(),getPaddingLeft(),getPaddingRight(),getPaddingBottom());
            }
        }
    }


    private void setPaddingAndRadius(int left,int top ,int right,int bottom){
        rect.set(left, top, mViewWidth - right, mViewHeight - bottom);
        rectF.set(left, top, mViewWidth - right, mViewHeight - bottom);
        mPath.reset();
        float[] radiusAarray = {defaultRadius, defaultRadius, defaultRadius, defaultRadius,
                defaultRadius, defaultRadius, defaultRadius, defaultRadius};
        mPath.addRoundRect(rectF, radiusAarray, Path.Direction.CW);
    }


    @Override
    public void draw(Canvas canvas) {
        if (mAnimating) {
            super.draw(canvas);
            return;
        }
        try {
            canvas.save();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.saveLayer(rectF, zonePaint);
                canvas.drawPath(mPath, zonePaint);
                canvas.saveLayer(rectF, maskPaint);
            } else {
                canvas.saveLayer(rectF, zonePaint, Canvas.ALL_SAVE_FLAG);
                canvas.drawPath(mPath, zonePaint);
                canvas.saveLayer(rectF, maskPaint, Canvas.ALL_SAVE_FLAG);
            }

            super.draw(canvas);
            canvas.restore();
            this.setScaleType(ScaleType.FIT_XY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        try {
            if (mAnimating && mGradientMatrix != null) {
                canvas.clipPath(mPath);
                super.onDraw(canvas);
                canvas.drawRect(rect, mPaint);
                this.setScaleType(ScaleType.FIT_XY);
            } else {
                super.onDraw(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initGradientAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(animation -> {
            float v = (Float) animation.getAnimatedValue();
            //❶ 改变每次动画的平移x、y值
            mTranslateX = (1 + 2 * flickerX) * mViewWidth * v - mViewWidth * flickerX;
            mTranslateY = (mViewHeight + 2 * mViewWidth * flickerX * flickerY) * v - mViewWidth * flickerX * flickerY;
            //❷ 平移matrix, 设置平移量
            if (mGradientMatrix != null) {
                mGradientMatrix.setTranslate(mTranslateX, mTranslateY);
            }
            //❸ 设置线性变化的matrix
            if (mGradient != null) {
                mGradient.setLocalMatrix(mGradientMatrix);
            }
            //❹ 重绘
            invalidate();
        });
    }


    /**
     * 停止动画
     */
    private void stopAnimation() {
        if (mAnimating && valueAnimator != null) {
            mAnimating = false;
            valueAnimator.cancel();
            invalidate();
        }
    }

    /**
     * 开始动画
     */
    private void startAnimation() {
        if (valueAnimator != null) {
            mAnimating = true;
            valueAnimator.start();
        }
    }

    /**
     * 关闭闪光并设置透明背景,padding值重置为0
     */
    public void stopShimmer() {
        setBackgroundResource(R.drawable.common_image_bg_normal);
        setPadding(0,0,0,0);
        stopAnimation();
    }

    /**
     * 开启闪光并设置白色边框
     */
    public void startShimmer() {
        setBackgroundResource(R.drawable.common_image_bg_focus);
        setPadding(padding,padding,padding,padding);
        invalidate();
        startAnimation();
    }
}
