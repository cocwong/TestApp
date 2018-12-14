package com.test.testapp;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class MyPop extends PopupWindow implements View.OnClickListener {
    private Activity context;

    public MyPop(Activity context, int resId) {
        super(context,null,R.style.Pop);
        this.context = context;
        init(resId);
    }

    private void init(int resId) {
        setContentView(LayoutInflater.from(context).inflate(resId, null, false));
        setOutsideTouchable(true);
        setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        getContentView().findViewById(R.id.back).setOnClickListener(this);
        setWidth(context.getWindow().getAttributes().width);
        setHeight(context.getWindow().getAttributes().height);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        Window window = context.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = 0.3f;
        window.setAttributes(params);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        Window window = context.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = 1;
        window.setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                dismiss();
                break;
        }
    }
}
