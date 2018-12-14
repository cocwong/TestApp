package com.test.testapp.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyCircleAnimView extends View implements View.OnClickListener {
    private Paint mPaint;
    private Path dstPath, srcPath;
    private PathMeasure measure;
    private ValueAnimator animator;
    private float value;

    public MyCircleAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);

        dstPath = new Path();
        srcPath = new Path();
        srcPath.addCircle(100, 100, 50, Path.Direction.CCW);

        measure = new PathMeasure(srcPath, true);

        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float length = measure.getLength();
        float stopValue = length * value;
        float startValue = (float) (stopValue - (0.5 - Math.abs(value - 0.5)) * length);
        dstPath.reset();
        boolean segment = measure.getSegment(startValue, stopValue, dstPath, true);
        if (segment) {
            canvas.drawPath(dstPath, mPaint);
        }
    }

    @Override
    public void onClick(View v) {
        animator.start();
        System.out.println("isEquals:" + (null instanceof String));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.cancel();
    }
}
