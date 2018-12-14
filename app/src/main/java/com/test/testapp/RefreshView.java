package com.test.testapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class RefreshView extends FrameLayout {
    private static final String TAG = "RefreshView";
    private RecyclerView recyclerView;
    private float preY, proY;

    public RefreshView(@NonNull Context context) {
        super(context);
        init();
    }

    public RefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        recyclerView = new RecyclerView(getContext());
        addView(recyclerView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        recyclerView.setLayoutManager(manager);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                proY = ev.getY();
                float dy = proY - preY;
                Log.e(TAG, "onInterceptTouchEvent: " + dy + isRecyclerViewOnTop());
                if (dy > 0 && isRecyclerViewOnTop()) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent: ");
        return super.onTouchEvent(event);
    }

    private boolean isRecyclerViewOnTop() {
        return recyclerView.getLayoutManager().getChildAt(0).getTop() == 0;
    }

    private boolean isRecyclerViewOnBottom() {
        return recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1).getBottom() == 0;
    }
}
