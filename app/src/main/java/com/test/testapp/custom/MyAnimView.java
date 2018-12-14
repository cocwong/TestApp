package com.test.testapp.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.test.testapp.R;

public class MyAnimView extends View {
    private Path mPath;
    private int itemHeight;
    private int itemWidth;
    private RectF rect;
    private Paint mPaint;
    private Bitmap mBitmap;

    public MyAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPath = new Path();
        rect = new RectF();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        itemHeight = getHeight() / 20;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        itemWidth += 5;
        int i = 0;
        while (i * itemHeight <= getHeight()) {
            if (i % 2 == 1) {
                rect.set(0, itemHeight * i, itemWidth, itemHeight * i + itemHeight);
            } else {
                rect.set(getWidth() - itemWidth, itemHeight * i, getWidth(), itemHeight * i + itemHeight);
            }
            mPath.addRect(rect, Path.Direction.CCW);
            i++;
        }

        canvas.clipPath(mPath);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        if (itemWidth < getWidth()) {
            invalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mBitmap.recycle();
    }
}
