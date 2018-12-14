package com.test.testapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TestView extends View {
    private Paint mPaintAxis, mPaintCircle, mPaintLine, mPaintRect;
    private int strokeWidth = 14;
    private boolean shouldDrawDown, shouldDrawMove;
    private int circleRadius = 30;

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaintAxis = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAxis.setColor(Color.BLUE);
        mPaintAxis.setStrokeWidth(strokeWidth);
        mPaintAxis.setStyle(Paint.Style.STROKE);

        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircle.setColor(Color.GREEN);

        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setStrokeWidth(20);
        mPaintLine.setColor(Color.RED);

        mPaintRect = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintRect.setStyle(Paint.Style.STROKE);
    }

    private float downX, downY, moveX, moveY;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAxis(canvas);
        drawCircle(canvas);
        if (shouldDrawDown) {
            drawDown(canvas);
        }
        if (shouldDrawMove) {
            drawMove(canvas);
        }
    }

    private void drawMove(Canvas canvas) {
        mPaintCircle.setColor(Color.YELLOW);
        canvas.drawCircle(moveX, moveY, circleRadius, mPaintCircle);
        mPaintLine.setColor(Color.RED);
        canvas.drawLine(getWidth() / 2, getHeight() / 2, moveX, moveY, mPaintLine);

        mPaintLine.setColor(Color.GREEN);
        canvas.drawLine(moveX, moveY, downX, downY, mPaintLine);
    }

    private void drawDown(Canvas canvas) {
        System.out.println("drawDown");
        mPaintCircle.setColor(Color.RED);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, circleRadius, mPaintCircle);
        mPaintCircle.setColor(Color.YELLOW);
        canvas.drawCircle(downX, downY, circleRadius, mPaintCircle);
        mPaintLine.setColor(Color.RED);
        canvas.drawLine(getWidth() / 2, getHeight() / 2, downX, downY, mPaintLine);
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mPaintAxis);
    }

    private void drawAxis(Canvas canvas) {
        canvas.drawLines(new float[]{
                0, getHeight() / 2, getWidth(), getHeight() / 2
                , getWidth() / 2, 0, getWidth() / 2, getHeight()}, mPaintAxis);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = (int) event.getY();
                if (isInCircle(x, y)) {
                    shouldDrawDown = true;
                    downX = x;
                    downY = y;
                    invalidate();
//                    shouldDrawDown = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                if (isInCircle(x, y)) {
                    shouldDrawMove = true;
                    moveX = x;
                    moveY = y;
                    invalidate();
//                    shouldDrawMove = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                clear();
//                invalidate();
                break;
        }
        return true;
    }

    private void clear() {
        shouldDrawDown = false;
        shouldDrawMove = false;
        invalidate();
    }

    private boolean isInCircle(float x, float y) {
        float dx = Math.abs(x - getWidth() / 2);
        float dy = Math.abs(y - getHeight() / 2);
        double dl = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        return dl <= getWidth() / 2;
    }
}
