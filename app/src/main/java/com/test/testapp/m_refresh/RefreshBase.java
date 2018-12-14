package com.test.testapp.m_refresh;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public abstract class RefreshBase<T extends View> extends LinearLayout {
    private Mode mMode = Mode.START;/*默认为下拉*/

    private T contentView;/*滑动内容*/
    private LoadingLayout loadingLayout;/*头部和底部*/

    public RefreshBase(Context context) {
        super(context);
        init();
    }

    public RefreshBase(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshBase(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public RefreshBase(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        setOrientation(VERTICAL);

        mMode = createPullMode();
        addContentView();
    }

    private void addContentView() {
        loadingLayout = createLoadingLayout();
        LayoutParams contentParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (mMode != Mode.END) {
            addView(loadingLayout.getHeader());
        }
        addView(contentView = createContentView(getContext()), contentParams);
        if (mMode != Mode.START) {
            addView(loadingLayout.getFooter());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        resetLoadingLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 初始化位置
     */
    private void resetLoadingLayout() {
//        int contentHeight = contentView.getMeasuredHeight();
        switch (mMode) {
            case START:
            case BOTH:
//                int headerHeight = loadingLayout.getHeader().getMeasuredHeight();
//                contentHeight += headerHeight;
//                contentView.getLayoutParams().height = contentHeight;
//                scrollBy(0, -headerHeight);
                break;
        }
    }

    /**
     * 获取滑动contentView
     *
     * @return contentView
     * @see RefreshBase#createContentView(Context)
     */
    protected T getContentView() {
        return contentView;
    }

    //**************abstract******************//

    /**
     * 创建需要滑动的View.
     *
     * @return 目标View
     */
    abstract T createContentView(Context context);

    /**
     * 设置滑动模式
     *
     * @return 滑动模式
     */
    abstract Mode createPullMode();

    abstract LoadingLayout createLoadingLayout();
}
