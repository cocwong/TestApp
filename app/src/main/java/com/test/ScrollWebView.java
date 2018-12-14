package com.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;

public class ScrollWebView extends WebView {


    public ScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private OnScrollWebListener onScrollWebListener;
    public void setScrollWebListener(OnScrollWebListener onScrollWebListener) {
        this.onScrollWebListener = onScrollWebListener;
    }
    public interface OnScrollWebListener {
        void onScroll(int dx, int dy);
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollWebListener != null) {
            onScrollWebListener.onScroll(l - oldl, t - oldt);
        }
    }
}
