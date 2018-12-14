package com.test.testapp.m_refresh;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class RefreshView extends RefreshBase<TextView> {
    public RefreshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    TextView createContentView(Context context) {
        TextView tv = new TextView(context);
        tv.setText("G");
        tv.setTextSize(80);
        tv.setTextColor(Color.YELLOW);
        tv.setBackgroundColor(Color.DKGRAY);
        return tv;
    }

    @Override
    Mode createPullMode() {
        return Mode.START;
    }

    @Override
    LoadingLayout createLoadingLayout() {
        return new LoadingLayout(getContext()) {
            @Override
            View createLoadingHeader() {
                return null;
            }

            @Override
            View createLoadingFooter() {
                return null;
            }
        };
    }
}
