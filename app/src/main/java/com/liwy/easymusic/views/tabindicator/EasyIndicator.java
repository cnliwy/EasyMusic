package com.liwy.easymusic.views.tabindicator;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * 自定义导航栏.可单独使用.也可结合ViewPager+fragment使用.
 * 单独使用时可重写点击事件mOnTabClickListener
 * 结合fragment时可重写页面切换事件mListener和点击事件mOnTabClickListener
 * Created by liwy on 16/7/19.
 */
public class EasyIndicator extends LinearLayout implements ITabIndicator{
    private List<TabBean> tabsList;
    private List<TabView> childViews;
    private ViewPager viewPager;
    private ViewPager.OnPageChangeListener mPageListener;
    private LinearLayout mTabLayout;
    private int currentIndex;
    private OnTabClickListener mOnTabClickListener;
    private TabConfig config;
    // 默认点击事件
    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            TabView tabView = (TabView)v;
            int newIndex = tabView.getIndex();
            if (currentIndex != newIndex){
                setCurrentItem(newIndex);
            }
            if (mOnTabClickListener != null)
                mOnTabClickListener.onClick(tabView);
        }
    };

    public EasyIndicator(Context context) {
        this(context,null);
    }

    public EasyIndicator(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EasyIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(false);
        mTabLayout = new LinearLayout(context,null);
        mTabLayout.setOrientation(LinearLayout.HORIZONTAL);
        mTabLayout.setGravity(Gravity.CENTER_VERTICAL);
        addView(mTabLayout, new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (config == null){
            config = new TabConfig.Builder().builder();
        }
        int heightSize = dip2px(getContext(),config.getTabHeight());
        int newHeightMeasureSpect =  MeasureSpec.makeMeasureSpec(heightSize,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpect);
    }

    // 绑定tabs和viewpager
    @Override
    public void setTabAndViewPager(List<TabBean> list, ViewPager view) {
        if (view == null)
            throw new IllegalStateException("ViewPager is null,however it is necessary");
        viewPager = view;
        viewPager.addOnPageChangeListener(this);
        setTabs(list);
    }
    // 绑定tabls
    @Override
    public void setTabs(List<TabBean> list) {
        if (list == null)
            throw new IllegalStateException("Tab's list is null,however it is necessary");
        tabsList = list;
        childViews = new ArrayList<TabView>(list.size());
        notifyDataSetChanged();
    }

    @Override
    public void setCurrentItem(int item) {
        if (viewPager != null)viewPager.setCurrentItem(item,false);
        currentIndex = item;
        final int tabCount = mTabLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            final TabView child = (TabView)mTabLayout.getChildAt(i);
            final boolean isSelected = (i == item);
            if (config.isShowLine())child.setShowLine(false);
            //设置默认背景色
            if (config.getBgColorNor() != 0) child.setBackgroundColor(getColorIntByResid(config.getBgColorNor()));
            if (tabsList.get(i).getResIconNormal() != 0)
                child.setIcon(tabsList.get(i).getResIconNormal());
            if (config.getTextColorNor() != 0)
                child.setTextColor(getColorIntByResid(config.getTextColorNor()));
            if (isSelected) {
                if (config.isShowLine())child.setShowLine(true);
                //设置选中后的背景色
                if (config.getBgColorSel() != 0)child.setBackgroundColor(getColorIntByResid(config.getBgColorSel()));
                //设置选中后的背景图片
                if (tabsList.get(i).getResIconSelected() != 0)
                    child.setIcon(tabsList.get(i).getResIconSelected());
                if (config.getTextColorSel() != 0)
                    child.setTextColor(getColorIntByResid(config.getTextColorSel()));
                if (config.getLineColor() != 0)child.setLineColor(getColorIntByResid(config.getLineColor()));
            }

        }
    }


    /**
     * 根据资源获取色值
     * @param colorResid
     * @return
     */
    public int getColorIntByResid(int colorResid){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           return getResources().getColor(colorResid, null);
        } else {
            return getResources().getColor(colorResid);
        }
    }


    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mPageListener = listener;
    }

    @Override
    public void notifyDataSetChanged() {
        if (tabsList == null || tabsList.size() < 1)return;
        int childCount = tabsList.size();
        for (int i = 0; i < childCount; i++){
            TabBean bean = tabsList.get(i);
            addTab(i,bean.getName(),bean.getResIconNormal());
        }
        setCurrentItem(0);
        requestLayout();
    }

    private void addTab(int index,CharSequence text, int resId){
        final TabView tabView = new TabView(getContext());
        tabView.setText(text);
        tabView.setIndex(index);
        tabView.setTextSize(config.getTextSize());
        tabView.setOnClickListener(mOnClickListener);
        tabView.setBackgroundColor(config.getBgColorNor());
        tabView.setTextColor(config.getTextColorNor());
        tabView.setLineColor(config.getLineColor());
        tabView.setDistance(config.getDistance());
        tabView.setImgHeight(config.getImgHeight());
        tabView.setImgWidth(config.getImgWidth());
        childViews.add(index,tabView);
        int height = dip2px(getContext(),config.getTabHeight());
        mTabLayout.addView(tabView, new LayoutParams(0, height, 1));
    }



    /**
     * OnPageChangeListener的实现方法,onPageScrolled,onPageSelected,onPageScrollStateChanged
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mPageListener != null){
            mPageListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        setCurrentItem(position);
        if (mPageListener != null){
            mPageListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mPageListener != null){
            mPageListener.onPageScrollStateChanged(state);
        }
    }


    public void setmOnTabClickListener(OnTabClickListener mOnTabClickListener) {
        this.mOnTabClickListener = mOnTabClickListener;
    }

    public void setmOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }


//    public class TabView extends android.support.v7.widget.AppCompatTextView{
//        private int index;
//        private int iconWidth =  dip2px(getContext(), TabConfig.tabImgWidth);
//        private int iconHeight = dip2px(getContext(), TabConfig.tabImgHeight);
//        public TabView(Context context) {
//            this(context,null,0);
//        }
//
//        public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
//            super(context, attrs, defStyleAttr);
//        }
//
//        @Override
//        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//            if (index > 0){
//                super.onMeasure(MeasureSpec.makeMeasureSpec(tabWidth,MeasureSpec.EXACTLY),heightMeasureSpec);
//            }
//        }
//
//        public int getIndex() {
//            return index;
//        }
//
//        public void setIndex(int index) {
//            this.index = index;
//        }
//
//        public void setIcon(int resId){
//            if (resId != 0){
//                Drawable drawable;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    drawable = getResources().getDrawable(resId,null);
//                }else{
//                    drawable = getResources().getDrawable(resId);
//                }
//                int padding = dip2px(getContext(),7);
//                drawable.setBounds(0,padding,iconWidth,iconHeight + padding);
//                setCompoundDrawables(null,drawable,null,null);
//                setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
//                setBackgroundColor(Color.TRANSPARENT);
//            }
//        }
//    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public List<TabView> getChildViews() {
        return childViews;
    }

    public void setConfig(TabConfig config) {
        this.config = config;
    }
}
