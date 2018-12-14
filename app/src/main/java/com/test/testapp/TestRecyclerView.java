package com.test.testapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class TestRecyclerView<V extends View> extends RecyclerView {
    private int page, maxPage;
    private OnLoadMoreListener loadMoreListener;
    private boolean loadMoreEnable;
    private V loadMoreView;

    public TestRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {

    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        computeLoadMore();
    }

    private void computeLoadMore() {
        if (!loadMoreEnable) return;
        if (loadMoreListener == null) return;
        if (maxPage == page) return;
        LayoutManager manager = getLayoutManager();
        int lastVisibleItemPosition = ((LinearLayoutManager) manager).findLastVisibleItemPosition();
        int itemCount = getAdapter().getItemCount();
        if (lastVisibleItemPosition == itemCount - 1) {
            loadMoreListener.onLoadMore();

        }
    }

    public void setLoadMoreView(V loadMoreView) {
        this.loadMoreView = loadMoreView;
    }

    public void setLoadMoreEnable(boolean loadMoreEnable) {
        this.loadMoreEnable = loadMoreEnable;
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    static abstract class TestAdapter<VH extends ViewHolder> extends RecyclerView.Adapter {
    }
}
