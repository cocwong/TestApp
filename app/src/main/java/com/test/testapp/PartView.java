package com.test.testapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PartView extends View {
    public PartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                calculateLocation(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private int total;

    public void setTotal(int total) {
        this.total = total;
    }

    private void calculateLocation(float dx, float dy) {
        float pixels = getWidth() * (dy - 1) + dx;
        float offset = pixels / getWidth() / getHeight() * total;
        if (onScroll != null) {
            onScroll.onScroll(offset);
        }
    }

    private OnScroll onScroll;

    public interface OnScroll {
        void onScroll(float pixels);
    }

    public void setOnScroll(OnScroll onScroll) {
        this.onScroll = onScroll;
    }
}
