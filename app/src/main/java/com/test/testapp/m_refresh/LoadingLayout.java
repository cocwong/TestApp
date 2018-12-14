package com.test.testapp.m_refresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.test.testapp.R;

public abstract class LoadingLayout {
    private View loadingHeader, loadingFooter;
    private Context context;

    public LoadingLayout(Context context) {
        this.context = context;
        loadingHeader = createLoadingHeader();
        loadingFooter = createLoadingFooter();
    }

    @SuppressLint("InflateParams")
    public View getHeader() {
        if (loadingHeader == null)
            loadingHeader = LayoutInflater.from(context).inflate(R.layout.refresh_header, null);
        return loadingHeader;
    }

    @SuppressLint("InflateParams")
    public View getFooter() {
        if (loadingFooter == null)
            loadingFooter = LayoutInflater.from(context).inflate(R.layout.refresh_header, null);
        return loadingFooter;
    }

    public void setListener() {
    }

    public int getLoadingHeight() {
        int loadingHeight = 0;
        if (loadingHeader != null) {
            loadingHeight += loadingHeader.getMeasuredHeight();
        }
        if (loadingFooter != null) {
            loadingHeight += loadingFooter.getMeasuredHeight();
        }
        return loadingHeight;
    }

    abstract View createLoadingHeader();

    abstract View createLoadingFooter();
}
