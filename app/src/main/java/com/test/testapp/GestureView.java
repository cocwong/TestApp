package com.test.testapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

public class GestureView extends ImageView {
    public GestureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    private GestureDetectorCompat gestureDetectorCompat;
}
