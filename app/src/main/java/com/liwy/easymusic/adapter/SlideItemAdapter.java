package com.liwy.easymusic.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.TextViewCompat;
import android.widget.TextView;

import com.liwy.easymusic.R;
import com.liwy.easymusic.base.easyrecycler.EasyListAdapter;
import com.liwy.easymusic.entity.SlideItem;

import java.util.List;


/**
 * Created by liwy on 2017/4/11.
 */

public class SlideItemAdapter extends EasyListAdapter<SlideItem> {
    // 图片尺寸
    private final int mIconSize;

    public SlideItemAdapter(Context context, List<SlideItem> mDatas) {
        super(context, mDatas);
        mIconSize = context.getResources().getDimensionPixelSize(R.dimen.slide_menu_icon);//32dp
    }

    @Override
    public ViewHolder initView(ViewHolder viewHolder, int position) {
        SlideItem item = mDatas.get(position);
        TextView itemView = (TextView) viewHolder.getmConvertView();
        itemView.setText(item.name);
        Drawable icon = mContext.getResources().getDrawable(item.icon);
        // setIconColor(icon);
        if (icon != null) {
            icon.setBounds(0, 0, mIconSize, mIconSize);
            TextViewCompat.setCompoundDrawablesRelative(itemView, icon, null, null, null);
        }
        return viewHolder;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_slide_menu;
    }
}
