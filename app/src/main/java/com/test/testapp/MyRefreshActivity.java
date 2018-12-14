package com.test.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;

import com.ccb.ecpmobile.ecp.vv.refresh.PullToRefreshBase;
import com.ccb.ecpmobile.ecp.vv.refresh.RefreshScrollView;
import com.test.ScaleActivity;

import java.lang.ref.WeakReference;

public class MyRefreshActivity extends AppCompatActivity {
    private MyFragment fragment;
    private MyFragment2 fragment2;
    private RefreshScrollView refreshView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_refresh);
        refreshView = findViewById(R.id.refresh);
        refreshView.setScrollingWhileRefreshingEnabled(true);
        refreshView.setPullToRefreshOverScrollEnabled(true);
        refreshView.setShowViewWhileRefreshing(true);
        refreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(final PullToRefreshBase<ScrollView> refreshView) {
                MyHandler handler = new MyHandler(MyRefreshActivity.this);
                handler.sendEmptyMessageDelayed(1, 2000);
            }
        });
    }

    public void act(View view) {
        startActivity(new Intent(this, ScaleActivity.class));
    }

    private void complete() {
//        refreshView.onRefreshComplete();
    }

    static class MyHandler extends Handler {
        WeakReference<MyRefreshActivity> reference;

        public MyHandler(MyRefreshActivity aty) {
            reference = new WeakReference<>(aty);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MyRefreshActivity activity = reference.get();
            activity.complete();
        }
    }
}
