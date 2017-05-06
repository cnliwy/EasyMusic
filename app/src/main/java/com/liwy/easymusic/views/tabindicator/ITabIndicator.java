package com.liwy.easymusic.views.tabindicator;

import android.support.v4.view.ViewPager;

import java.util.List;

/**
 * Created by liwy on 16/7/19.
 */
public interface ITabIndicator extends ViewPager.OnPageChangeListener{
    void setTabAndViewPager(List<TabBean> list, ViewPager view);

    void setTabs(List<TabBean> list);

    void setCurrentItem(int item);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);

    void notifyDataSetChanged();
}
