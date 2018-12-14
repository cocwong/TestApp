package com.ccb.ecpmobile.ecp.vv.refresh.internal;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.ccb.ecpmobile.ecp.vv.refresh.PullToRefreshBase;
import com.ccb.ecpmobile.ecp.vv.refresh.internal.LoadingLayout;

public class CustomLoadingLayout extends LoadingLayout {
    public CustomLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection) {
        super(context, mode, scrollDirection);
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {

    }

    @Override
    protected void onPullImpl(float scaleOfLayout) {
        headerProgressView.updateAngle(scaleOfLayout);
    }

    @Override
    protected void refreshingImpl() {
        headerProgressView.startRefresh();
    }

    @Override
    protected void resetImpl() {
        headerProgressView.stopRefresh();
        headerProgressView.resetAngle();
    }
    @Override
    protected void pullToRefreshImpl() {
    }

    @Override
    protected void releaseToRefreshImpl() {
    }

    @Override
    protected int getDefaultDrawableResId() {
        return 0;
    }
}
