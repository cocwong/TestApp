package com.ccb.ecpmobile.ecp.vv.refresh;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import java.lang.ref.WeakReference;

/**
 * 头部转圈view
 */
public class HeaderProgressView extends View {
    private Paint mPaint;
    private float centerX, centerY, radius;
    private int strokeWidth = 8;
    private float startAngle;
    private RectF frontRectF;
    private RotateHandler handler;

    public HeaderProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
        handler = new RotateHandler(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (modeWidth == MeasureSpec.AT_MOST) {
            if (modeHeight == MeasureSpec.AT_MOST) {
                sizeHeight = sizeWidth = 100;
            } else {
                sizeWidth = sizeHeight;
            }
        } else if (modeHeight == MeasureSpec.AT_MOST) {
            sizeHeight = sizeWidth;
        }
        int newSize = Math.min(sizeWidth, sizeHeight);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(newSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(newSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        radius = getHeight() / 2 - strokeWidth;
        frontRectF = new RectF(strokeWidth, strokeWidth, getWidth() - strokeWidth, getHeight() - strokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("draw");
        drawBackgroundCircle(canvas);
        drawFrontCircle(canvas);
    }

    private void drawFrontCircle(Canvas canvas) {
        mPaint.setColor(0xFFFCA609);
        canvas.drawArc(frontRectF, startAngle, 90, false, mPaint);
    }

    private void drawBackgroundCircle(Canvas canvas) {
        mPaint.setColor(Color.GRAY);
        canvas.drawCircle(centerX, centerY, radius, mPaint);
    }

    public void updateAngle(float angle) {
        startAngle = 90 * angle;
        invalidate();
    }

    public void resetAngle() {
        startAngle = 0;
        clearAnimation();
    }

    public void startRefresh() {
        isRefresh = true;
        updateView();
    }

    public void stopRefresh() {
        isRefresh = false;
        resetAngle();
    }

    private Animation mRotateAnimation;

    private void updateView() {
        handler.sendEmptyMessage(0);
//        if (mRotateAnimation == null) {
//            initAnimator();
//        }
//        startAnimation(mRotateAnimation);
    }

    boolean isRefresh;

    private void rotate() {
        startAngle += 6;
        invalidate();
    }

    private void initAnimator() {
        mRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(1200);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setRepeatMode(Animation.RESTART);
        mRotateAnimation.setFillAfter(true);
    }

    private static class RotateHandler extends Handler {
        WeakReference<HeaderProgressView> view;
        HeaderProgressView headerProgressView;

        RotateHandler(HeaderProgressView header) {
            view = new WeakReference<>(header);
            headerProgressView = view.get();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            headerProgressView.rotate();
            if (headerProgressView.isRefresh) {
                sendEmptyMessageDelayed(0, 10);
            }
        }
    }
}
