package com.liwy.easymusic.views.easyrecycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by liwy on 2017/3/27.
 */

public class EasyHolder extends RecyclerView.ViewHolder {
    private View mConvertView;
    private SparseArray<View> mViews;
    private int mPosition;

    public EasyHolder(View itemView,int position) {
        super(itemView);
        this.mPosition = position;
        mConvertView = itemView;
        this.mViews = new SparseArray<View>();
    }

    public static EasyHolder get(final Context context, ViewGroup parent, int layoutId, int position) {
        View view = null;
        // 如果使用了滑动菜单功能，则加载菜单
        EasyRecyclerView recyclerView = (EasyRecyclerView)parent;
        if (recyclerView.isSwipeMenuEnable()){
            SwipMenuCreator swipMenuCreator = ((EasyRecyclerView)parent).getSwipMenuCreator();
            List<SwipMenu> menus = swipMenuCreator.addMenu(context);

            //父布局，用来包裹item布局和菜单布局
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            // item布局
            view = LayoutInflater.from(context).inflate(layoutId, linearLayout, false);
            linearLayout.addView(view);
            //菜单布局
            LinearLayout menuLayout = new LinearLayout(context);
            menuLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            menuLayout.setBackgroundColor(Color.GRAY);
            menuLayout.setOrientation(LinearLayout.HORIZONTAL);
            int menuWidth = 0;
            for (int i = 0; i < menus.size(); i++){
                SwipMenu menu = menus.get(i);
                menu.setTag(i);
                menuWidth += menu.getMenuWidth();
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(menu.getMenuWidth(),menu.getMenuHeight());
                if (menu.getMenuWeight() != 0){
                    params.weight = menu.getMenuWeight();
                }
                menu.setLayoutParams(params);
                menuLayout.addView(menu);
            }
            menuLayout.setLayoutParams(new LinearLayout.LayoutParams(menuWidth, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout.addView(menuLayout);
            return new EasyHolder(linearLayout,position);
        }else{
            // 未使用滑动菜单功能，直接加载item布局
            view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        }
        return new EasyHolder(view,position);
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置文字
     *
     * @param viewId
     * @param text
     * @return
     */
    public EasyHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public EasyHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public EasyHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }


}
