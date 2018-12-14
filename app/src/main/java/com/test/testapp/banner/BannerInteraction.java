package com.test.testapp.banner;

import java.util.List;

public interface BannerInteraction {
    /**
     * 设置切换间隔时间
     *
     * @param duration 间隔时间
     */
    BannerView setDuration(int duration);

    /**
     * 设置是否可触摸停止
     *
     * @param touchable boolean
     */
    BannerView setTouchable(boolean touchable);


    /**
     * 设置内容
     *
     * @param rseList resId-List
     */
    BannerView setBannerContent(List<String> rseList);

    /**
     * 开始滚动
     */
    void start();
}
