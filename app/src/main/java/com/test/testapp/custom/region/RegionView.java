package com.test.testapp.custom.region;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class RegionView extends View {
    private Region region;
    private Paint mPaint;
    private Path mPath;

    public RegionView(Context context) {
        super(context);
    }

    public RegionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RegionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);

        mPath = new Path();
        RectF rect = new RectF(50, 50, 200, 500);
        mPath.addOval(rect, Path.Direction.CCW);

//        region = new Region();
//        region.setPath(mPath, new Region(50, 50, 200, 200));
        setRegionUnion();
    }

    private void setRegionUnion() {
        region = new Region(10, 10, 200, 100);
        region.union(new Rect(10, 110, 50, 300));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRegion(canvas);
    }

    private void drawRegion(Canvas canvas) {
        RegionIterator iterator = new RegionIterator(region);
        Rect rect = new Rect();
        while (iterator.next(rect)) {
            canvas.drawRect(rect, mPaint);
        }
    }
}
