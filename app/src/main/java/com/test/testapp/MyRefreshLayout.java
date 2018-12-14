package com.test.testapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.TextView;


public class MyRefreshLayout extends ViewGroup {
    private float mLastY;
    private TextView header;
    private State state = State.HIDE;
    private Scroller mScroller;
    private HeaderAct headerAct;

    public MyRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        headerAct = new HeaderAct() {
            @Override
            public void hide() {
                header.setText("隐藏");
            }

            @Override
            public void pull() {
                header.setText("下拉刷新");
            }

            @Override
            public void push() {
                header.setText("下拉刷新");
            }

            @Override
            public void ready() {
                header.setText("释放以刷新");
            }

            @Override
            public void refresh() {
                header.setText("正在刷新");
            }

            @Override
            public void release() {
                header.setText("正在隐藏");
            }
        };
        addHeader(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View header = getChildAt(0);
        header.layout(l, t - header.getMeasuredHeight(), r, b - header.getMeasuredHeight());
        View content = getChildAt(1);
        content.layout(l, t, r, b);
    }

    private void addHeader(Context context) {
        header = new TextView(context);
        header.setTextSize(30);
        header.setText("header");
        header.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(header, 0, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventY = event.getY();
        System.out.println(eventY);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (state == State.RELEASE || state == State.REFRESH) return false;
                mLastY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (state == State.RELEASE || state == State.REFRESH) return false;
                float deltaY = eventY - mLastY;
                mLastY = eventY;
                System.out.println(isOnTop()+",,,");
                if (state == State.HIDE) {
                    if (deltaY > 0 && isOnTop()) {
                        computeScroll((int) deltaY);
                        break;
                    }
                } else if (state == State.PULL || state == State.PUSH) {
                    computeScroll((int) deltaY);
                    break;
                }

            case MotionEvent.ACTION_UP:
                if (state == State.READY) {
                    refresh();
                } else {
                    release();
                }
                break;
        }
        return true;
    }

    private void refresh() {
        setState(State.REFRESH);
        mScroller.startScroll(0, getScrollY(), 0, 100 - getScrollY(), 300);
        invalidate();
    }

    private void release() {
        setState(State.RELEASE);
        mScroller.startScroll(0, getScrollY(), 0, -getScrollY(), 300);
        invalidate();
    }

    private void computeScroll(int deltaY) {
        deltaY /= 2;
        if ((getScrollY() - deltaY) >= 0) {
            scrollTo(0, 0);
            setState(State.HIDE);
            return;
        }
        System.err.println(deltaY);
        scrollBy(0, -deltaY);
        if (getScrollY() >= 100) {
            setState(State.READY);
            return;
        }
        if (deltaY > 0) {
            setState(State.PULL);
        } else if (deltaY < 0) {
            setState(State.PUSH);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        } else {
            if (state == State.RELEASE) {
                state = State.HIDE;
            }
        }
    }

    public void setRefreshComplete() {
        release();
    }

    private boolean isOnTop() {
        View child = getChildAt(1);
        return child == null || !canScrollVertically(1);
    }


    private enum State {
        HIDE,
        PULL,
        PUSH,
        READY,
        REFRESH,
        RELEASE
    }

    private synchronized void setState(State state) {
        this.state = state;
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
