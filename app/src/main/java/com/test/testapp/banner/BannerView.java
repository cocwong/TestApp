package com.test.testapp.banner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.List;

public class BannerView extends FrameLayout implements BannerInteraction {
    private BannerRecyclerWrapper wrapper;

    public BannerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        wrapper.clear();
    }

    private void init(Context context) {
        wrapper = new BannerRecyclerWrapper(context, this);
    }

    //-----------------interaction--------------------//
    @Override
    public BannerView setDuration(int duration) {
        wrapper.setDuration(duration);
        return this;
    }

    @Override
    public BannerView setTouchable(boolean touchable) {
        wrapper.setTouchable(touchable);
        return this;
    }

    @Override
    public BannerView setBannerContent(List<String> rseList) {
        wrapper.setContentList(rseList);
        return this;
    }

    @Override
    public void start() {
        wrapper.start();
    }
}
