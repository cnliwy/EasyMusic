package com.liwy.easymusic.base.easyrecycler;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.Gravity;

/**
 * Recycler滑动item的菜单按钮
 * Created by liwy on 2017/4/18.
 */

public class SwipMenu extends AppCompatButton {
    private int menuWidth;
    private int menuHeight;
    private int menuWeight;

    public SwipMenu(Context context) {
        super(context);
        // 设置默认参数
        this.setTextColor(Color.RED);
        this.setBackgroundColor(Color.WHITE);
        this.setGravity(Gravity.CENTER);
    }

    /**
     * 根据builder构造器构建实体类
     * @param builder
     */
    public SwipMenu(Builder builder) {
        super(builder.getContext());
        if (!TextUtils.isEmpty(builder.getText())){
            this.setText(builder.getText());
        }
        if (builder.getGravity() != 0){
            this.setGravity(builder.getGravity());
        }
        if (builder.getWidth() != 0){
            this.setMenuWidth(builder.getWidth());

        }
        if (builder.getHeight() != 0){
            this.setMenuHeight(builder.getHeight());
        }
        if (builder.getBackgroundColor() != 0){
            this.setBackgroundColor(builder.getBackgroundColor());
        }
        if (builder.getTextColor() != 0){
            this.setTextColor(builder.getTextColor());
        }
        if (builder.getWeight() != 0){
            this.setMenuWeight(builder.getWeight());
        }
    }

    public int getMenuWidth() {
        return menuWidth;
    }

    public void setMenuWidth(int menuWidth) {
        this.menuWidth = menuWidth;
    }

    public int getMenuHeight() {
        return menuHeight;
    }

    public void setMenuHeight(int menuHeight) {
        this.menuHeight = menuHeight;
    }

    public int getMenuWeight() {
        return menuWeight;
    }

    public void setMenuWeight(int menuWeight) {
        this.menuWeight = menuWeight;
    }

    /**
     * item滑动菜单按钮builder构造器
     */
    public static class Builder{
        private Context context;
        private String text;
        private int backgroundColor;
        private int gravity;
        private int textColor;
        private int height;
        private int width;
        private int weight;

        public SwipMenu builder(){
            return new SwipMenu(this);
        }

        public Context getContext() {
            return context;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public String getText() {
            return text;
        }

        public int getBackgroundColor() {
            return backgroundColor;
        }

        public int getGravity() {
            return gravity;
        }

        public int getTextColor() {
            return textColor;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }



}
