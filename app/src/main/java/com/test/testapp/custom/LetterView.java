package com.test.testapp.custom;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class LetterView extends View implements View.OnClickListener {
    private char data;
    private Paint mPaint;

    public LetterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        float sp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        mPaint.setTextSize(sp);
        mPaint.setColor(Color.DKGRAY);
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(String.valueOf(data), getWidth() / 2, getHeight() / 2, mPaint);
    }

    @Override
    public void onClick(View v) {
        ValueAnimator animator = ValueAnimator.ofObject(new MyEvaluator(), 'A', 'Z');
        animator.setDuration(5000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                data = (char) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    class MyEvaluator implements TypeEvaluator<Character> {

        @Override
        public Character evaluate(float fraction, Character startValue, Character endValue) {
            int char1 = (int) (startValue + fraction * (endValue - startValue));
            return (char) char1;
        }
    }
}
