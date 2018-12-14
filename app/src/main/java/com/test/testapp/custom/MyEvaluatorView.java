package com.test.testapp.custom;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyEvaluatorView extends View implements View.OnClickListener {
    public MyEvaluatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ValueAnimator animator = ValueAnimator.ofInt(0xff342584, 0xffe34a3d);
        animator.setDuration(3000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int color = (int) animation.getAnimatedValue();
                setBackgroundColor(color);
            }
        });
        animator.start();
    }
}
