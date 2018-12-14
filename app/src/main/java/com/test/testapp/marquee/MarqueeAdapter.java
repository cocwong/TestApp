package com.test.testapp.marquee;

import android.content.Context;
import android.view.View;

public abstract class MarqueeAdapter {
    abstract ViewHolder initial(Context context);

    abstract void bindView(int position);

    View getNext(int position) {
        return null;
    }

}
