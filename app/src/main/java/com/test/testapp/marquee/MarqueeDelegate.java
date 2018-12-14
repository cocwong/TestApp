package com.test.testapp.marquee;

import android.view.View;

import java.util.List;

public class MarqueeDelegate {
    private List<String> list;
    private int position;
    private int duration = 1000;
    private MarqueeAdapter adapter;

    void initialAdapter(MarqueeAdapter adapter) {
        if (adapter == null) throw new RuntimeException("adapter cannot be null");
        this.adapter = adapter;
    }

    void setList(List<String> list) {
        this.list = list;
    }

    void setDuration(int duration) {
        this.duration = duration;
    }

    View getNext() {
        if (list != null && !list.isEmpty()) {
            return adapter.getNext(getNextPosition(position));
        }
        return null;
    }

    private int getNextPosition(int position) {
        int length = list.size();
        int newPosition = position + 1;
        if (newPosition == length) {
            return 0;
        }
        return newPosition;
    }
}
