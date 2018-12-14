package com.test.testapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import java.util.List;

public class TestItemDecoration extends RecyclerView.ItemDecoration {
    private int itemHeight;//高度
    private Paint mPaint;
    private Context context;
    private Rect bounds;
    private List<String> letters;

    public TestItemDecoration(Context context, List<String> letters) {
        this.context = context;
        bounds = new Rect();
        itemHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, context.getResources().getDisplayMetrics());
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(14);
        mPaint.setColor(Color.WHITE);
        this.letters = letters;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
//        for (int i = 0; i < parent.getChildCount(); i++) {
//            View child = parent.getChildAt(i);
//            int position = parent.getChildAdapterPosition(child);
//            if (isFirstInGroup(position)) {
//                c.drawColor(context.getResources().getColor(R.color.grey));
//                String text = letters.get(position);
//                mPaint.getTextBounds(text, 0, text.length(), bounds);
//                float baseX = 20;
//                Paint.FontMetrics metrics = mPaint.getFontMetrics();
//                float baseY = itemHeight / 2 - (metrics.top + metrics.bottom) / 2;
//                c.drawText(letters.get(position), baseX, baseY, mPaint);
//            }
//
//        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (isFirstInGroup(position)) {
            outRect.top = itemHeight;
        }
    }

    private boolean isFirstInGroup(int position) {

        return false;
    }
}
