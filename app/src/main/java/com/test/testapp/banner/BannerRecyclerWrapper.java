package com.test.testapp.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BannerRecyclerWrapper {
    private Context context;
    private int mDuration = 3000;
    private BannerRecyclerView recycler;
    private PagerSnapHelper mSnapHelper;
    private RecyclerView.LayoutManager layoutManager;

    BannerRecyclerWrapper(Context context, ViewGroup parent) {
        this.context = context;
        parent.addView(recycler = new BannerRecyclerView(context));
        layoutManager = recycler.getLayoutManager();
        mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(recycler);
    }

    void setContentList(List<String> list) {
        if (list == null) throw new RuntimeException("ContentList不能为null");
        recycler.setList(list);
    }

    void setDuration(int duration) {
        if (duration <= 0) throw new RuntimeException("duration必须大于0");
        mDuration = duration;
    }

    void setTouchable(boolean touchable) {

    }

    void start() {
        int itemCount = recycler.getAdapter().getItemCount();
        if (itemCount == 0) return;
        mHandler.sendEmptyMessageDelayed(0, mDuration);
    }

    void stop() {
        mHandler.removeMessages(0);
    }

    /**
     * 获取当前view的位置
     *
     * @return position
     */
    int getPosition() {
        View snapView = mSnapHelper.findSnapView(layoutManager);
        if (snapView == null) return RecyclerView.NO_POSITION;
        return layoutManager.getPosition(snapView);
    }

    /**
     * 获取下个view的位置
     *
     * @return nextPosition
     */
    int getNextPosition() {
        int positionCurrent = getPosition();
        int itemCount = recycler.getAdapter().getItemCount();
        return ++positionCurrent == itemCount ? 0 : positionCurrent;
    }

    /**
     * 滚到下个位置
     */
    private void scrollToNext() {
        layoutManager.scrollToPosition(getNextPosition());
    }

    /**
     * 移除消息
     */
    void clear() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                scrollToNext();
                mHandler.sendEmptyMessageDelayed(0, mDuration);
            }
            return false;
        }
    });
}
