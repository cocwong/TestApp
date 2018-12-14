package com.test.testapp;

import android.view.MotionEvent;
import android.view.View;

public class ArcScrollHelper {
    private View targetView;

    private ArcScrollHelper(View targetView) {
        this.targetView = targetView;
        resetTouchEvent();
    }

    private void resetTouchEvent() {
        targetView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    public static ArcScrollHelper create(View targetView) {
        return new ArcScrollHelper(targetView);
    }

}
