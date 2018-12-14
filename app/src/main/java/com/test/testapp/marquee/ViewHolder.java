package com.test.testapp.marquee;

import android.view.View;

public abstract class ViewHolder {
    private View itemView;

    public ViewHolder(View itemView) {
        if (itemView == null) throw new RuntimeException("itemView cannot be null");
        this.itemView = itemView;
    }
}
