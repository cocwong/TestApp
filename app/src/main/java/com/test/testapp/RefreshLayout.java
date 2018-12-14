package com.test.testapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;


public class RefreshLayout extends FrameLayout {
    private boolean refreshable = true;
    private RefreshState refreshState = RefreshState.HIDE;
    private HeaderAct headerAct;
    private TextView headerView;//头
    private Scroller mScroller;
    private float contentOffset;

    public RefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        initHeader();
        headerAct = new HeaderAct() {
            @Override
            public void hide() {

            }

            @Override
            public void pull() {

            }

            @Override
            public void push() {

            }

            @Override
            public void ready() {

            }

            @Override
            public void refresh() {

            }

            @Override
            public void release() {

            }
        };
    }

    /**
     * 初始化头
     */
    private void initHeader() {
        headerView = new TextView(getContext());
        headerView.setBackgroundColor(Color.RED);
        headerView.setTextSize(40);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        addView(headerView, params);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        headerView.scrollBy(0, -headerView.getHeight());
    }

    private float mLastDownY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!refreshable) return super.dispatchTouchEvent(ev);//设置不可下拉刷新时，直接分发事件
//        System.out.println(ev.getAction());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float touchY = ev.getY();
                float deltaY = touchY - mLastDownY;
                mLastDownY = touchY;
                System.out.println(deltaY + "," + refreshState);
                if (refreshState == RefreshState.HIDE) {
                    if (deltaY > 0 && isOnTop()) {
                        computeScrollY(deltaY);
                    }
                    break;
                } else if (refreshState == RefreshState.PULL) {
                    computeScrollY(deltaY);
                    return true;
                } else if (refreshState == RefreshState.PUSH) {
                    computeScrollY(deltaY);
                    break;
                } else if (refreshState == RefreshState.READY) {
                    computeScrollY(deltaY);
                    break;
                } else if (refreshState == RefreshState.REFRESH) {
                    break;
                } else if (refreshState == RefreshState.RELEASE) {
                    break;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                if (refreshState == RefreshState.READY) {
                    setRefresh();
                } else {
                    reset();
                }
                break;
        }
        return true;
    }

    private void reset() {
        mScroller.startScroll(0, getScrollY(), 0, 0, 800);
        setRefreshState(RefreshState.RELEASE);
        invalidate();
    }

    private void setRefresh() {
        mScroller.startScroll(0, getScrollY(), 0, 100, 300);
        setRefreshState(RefreshState.REFRESH);
        invalidate();
    }

    private void computeScrollY(float deltaY) {
        deltaY /= 3f;
        if ((contentOffset += deltaY) < 0) {
            deltaY = -contentOffset;
        }
        scrollBy(0, (int) -deltaY);
        contentOffset += deltaY;
        if (deltaY > 0) {
            if (getScrollY() > 100) {
                setRefreshState(RefreshState.READY);
            } else
                setRefreshState(RefreshState.PULL);
        } else if (deltaY < 0) {
            if (getScrollY() > 100) {
                setRefreshState(RefreshState.READY);
            } else
                setRefreshState(RefreshState.PUSH);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollBy(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        } else {
            if (refreshState == RefreshState.RELEASE) {
                setRefreshState(RefreshState.HIDE);
            }
        }
    }

    /**
     * 刷新完成
     */
    public void setRefreshComplete() {
        mScroller.startScroll(0, getScrollY(), 0, 0, 300);
        setRefreshState(RefreshState.HIDE);
        invalidate();
    }

    /**
     * @return 是否在顶部
     */
    private boolean isOnTop() {
        View child = getChildAt(0);
        if (child == null) return true;
        //向下滑动
        return !child.canScrollVertically(1);
    }

    /**
     * @param refreshable 是否支持下拉刷新 默认true
     */
    public void setRefreshable(boolean refreshable) {
        this.refreshable = refreshable;
    }


    enum RefreshState {
        HIDE,
        PULL,
        PUSH,
        READY,
        REFRESH,
        RELEASE
    }

    private void setRefreshState(RefreshState state) {
        this.refreshState = state;
        switch (state) {
            case HIDE:
                headerAct.hide();
                break;
            case PULL:
                headerAct.pull();
                break;
            case PUSH:
                headerAct.push();
                break;
            case READY:
                headerAct.ready();
                break;
            case REFRESH:
                headerAct.refresh();
                break;
            case RELEASE:
                headerAct.release();
                break;
        }
    }

    private interface HeaderAct {
        void hide();

        void pull();

        void push();

        void ready();

        void refresh();

        void release();
    }
}
