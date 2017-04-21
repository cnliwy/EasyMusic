package com.liwy.easymusic.entity;

import android.text.TextUtils;

/**
 * 侧滑菜单实体类
 * Created by liwy on 2017/4/11.
 */

public class SlideItem {
    private static final int NO_ICON = 0;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_NO_ICON = 1;
    public static final int TYPE_SEPARATOR = 2;

    public int type;         // 按钮类型
    public String name;      // 按钮文字
    public int icon;        // 按钮图标资源id


    public SlideItem() {
        this(null);
    }

    public SlideItem(String name) {
        this(NO_ICON, name);
    }

    public SlideItem(int icon, String name) {
        this.icon = icon;
        this.name = name;

        if (icon == NO_ICON && TextUtils.isEmpty(name)) {
            type = TYPE_SEPARATOR;
        } else if (icon == NO_ICON) {
            type = TYPE_NO_ICON;
        } else {
            type = TYPE_NORMAL;
        }

        if (type != TYPE_SEPARATOR && TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("you need set a name for a non-SEPARATOR item");
        }
    }




}
