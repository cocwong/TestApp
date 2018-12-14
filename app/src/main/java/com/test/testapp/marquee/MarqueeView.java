package com.test.testapp.marquee;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

public class MarqueeView extends FrameLayout implements ViewFunc {
    private View next;
    private MarqueeDelegate delegate;

    public MarqueeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        delegate = new MarqueeDelegate();
    }

    public void setList(List<String> list) {
        delegate.setList(list);
    }

    @Override
    public void setDuration(int duration) {
        delegate.setDuration(duration);
    }

    @Override
    public void start() {
    }

    @Override
    public void showNext() {
        next = delegate.getNext();
    }

    public void setAdaper(MarqueeAdapter adaper) {
        delegate.initialAdapter(adaper);
    }
}
