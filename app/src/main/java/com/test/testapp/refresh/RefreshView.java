package com.test.testapp.refresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.test.testapp.R;

public class RefreshView extends RecyclerView {
    private int refreshHeaderHeight;
    private boolean enableLoadMore, enableRefresh;
    private View viewRefresh, viewLoadMore;
    private volatile State state = State.STATE_IDLE;

    public RefreshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RefreshView);
        refreshHeaderHeight = array.getDimensionPixelSize(R.styleable.RefreshView_refreshHeight, 100);
        array.recycle();
    }

    private float lastX, lastY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {//按state状态处理
        if (!enableRefresh) return super.onTouchEvent(e);
        float x = e.getX();
        float y = e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
//                return true;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = (int) (y - lastY);
                if (dy > 0) {
                    scroll(dy);
                    if (!(state == State.STATE_REFRESH)) {
                        setState(State.STATE_DRAG);
                    }
//                    return true;
                } else if (dy < 0 && state == State.STATE_DRAG) {
                    scroll(dy);
//                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (state == State.STATE_REFRESH) {
                    scrollRefresh();
//                    return true;
                } else if (state == State.STATE_DRAG) {
                    if (shouldRefresh(y)) {
                        scrollRefresh();
                        setState(State.STATE_REFRESH);
                    } else {
                        scrollReset();
//                        flagRefresh = false;
                    }
//                    return true;
                }
                break;
        }
        return super.onTouchEvent(e);
    }

    /**
     * 滑到刷新位置
     */
    private void scrollRefresh() {
        scrollTo(0, refreshHeaderHeight);
    }

    private boolean shouldRefresh(float distanceY) {
        return distanceY >= refreshHeaderHeight;
    }

    private void scrollReset() {
        scrollTo(0, 0);
    }

    private void scroll(int distance) {
        scrollBy(0, distance);
    }

    private synchronized void setState(State state) {
        this.state = state;
    }

    public void setLoadMoreEnable(boolean enableLoadMore) {
        this.enableLoadMore = enableLoadMore;
    }

    public void setRefreshEnable(boolean enableRefresh) {
        this.enableRefresh = enableRefresh;
    }

    public void setRefreshView(View viewRefresh) {
        this.viewRefresh = viewRefresh;
    }

    public void setLoadMoreView(View viewLoadMore) {
        this.viewLoadMore = viewLoadMore;
    }

    enum State {
        STATE_IDLE,
        STATE_DRAG,
        STATE_REFRESH
    }
}
