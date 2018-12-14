package com.test.testapp.custom;

import android.animation.IntArrayEvaluator;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.ArgbEvaluator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import com.test.testapp.R;

public class ColorFilterView extends View {
    private Bitmap mBitmap;
    private Paint mPaint;
    private ColorMatrixColorFilter filter;

    public ColorFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
        filter = new ColorMatrixColorFilter(new float[]{
                1/2f, 1/2f, 1/2f, 0, 0,
                1/3f, 1/3f, 1/3f, 0, 0,
                1/4f, 1/4f, 1/4f, 0, 0,
                0, 0, 0, 1, 0
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        canvas.translate(0, mBitmap.getHeight());
        mPaint.setColorFilter(filter);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }
}
