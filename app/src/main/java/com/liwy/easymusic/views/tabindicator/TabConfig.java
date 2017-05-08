package com.liwy.easymusic.views.tabindicator;


/**
 * tab 配置类
 * Created by liwy on 16/7/19.
 */
public class TabConfig {

    public TabConfig(Builder builder) {
        this.tabHeight = builder.getTabHeight();
        this.imgHeight = builder.getImgHeight();
        this.imgWidth = builder.getImgWidth();
        this.bgColorNor = builder.getBgColorNor();
        this.bgColorSel = builder.getBgColorSel();
        this.textSize = builder.getTextSize();
        this.textColorNor = builder.getTextColorNor();
        this.textColorSel = builder.getTextColorSel();
        this.lineColor = builder.getLineColor();
        this.distance = builder.getDistance();
        this.isShowLine = builder.isShowLine();
    }

    //TabView的高
    private int tabHeight = 49;
    //TabView背景图片的宽和高
    private int imgWidth = 20;
    private int imgHeight = 20;
    //TabView的默认及选中背景色
    private int bgColorNor;
    private int bgColorSel;
    //字体的大小
    private int textSize = 10;
    //默认及选中字体颜色
    private int textColorNor;
    private int textColorSel;
    //选中状态下下划线的颜色
    private int lineColor;
    //文字和图片的距离
    private int distance = 5;
    // 是否显示下划线
    private boolean isShowLine = false;

    public boolean isShowLine() {
        return isShowLine;
    }

    public void setShowLine(boolean showLine) {
        isShowLine = showLine;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getTabHeight() {
        return tabHeight;
    }

    public void setTabHeight(int tabHeight) {
        this.tabHeight = tabHeight;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public int getBgColorNor() {
        return bgColorNor;
    }

    public void setBgColorNor(int bgColorNor) {
        this.bgColorNor = bgColorNor;
    }

    public int getBgColorSel() {
        return bgColorSel;
    }

    public void setBgColorSel(int bgColorSel) {
        this.bgColorSel = bgColorSel;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextColorNor() {
        return textColorNor;
    }

    public void setTextColorNor(int textColorNor) {
        this.textColorNor = textColorNor;
    }

    public int getTextColorSel() {
        return textColorSel;
    }

    public void setTextColorSel(int textColorSel) {
        this.textColorSel = textColorSel;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    /**
     * 配置类Builder构造器
     */
    public static class Builder{
        //TabView的高
        public int tabHeight = 49;
        //TabView背景图片的宽和高
        public int imgWidth = 20;
        public int imgHeight = 20;
        //TabView的默认背景色
        public int bgColorNor;
        //TabView选中状态的背景色
        public int bgColorSel;
        //字体的大小
        public int textSize = 10;
        //默认及选中字体颜色
        public int textColorNor;
        public int textColorSel;
        //选中状态下下划线的颜色
        public int lineColor;
        //文字和图片的距离
        private int distance = 5;
        // 是否显示下划线
        private boolean isShowLine = false;

        public Builder() {
        }
        public TabConfig builder(){
            return new TabConfig(this);
        }

        public boolean isShowLine() {
            return isShowLine;
        }

        public Builder setShowLine(boolean showLine) {
            this.isShowLine = showLine;
            return this;
        }

        public int getDistance() {
            return distance;
        }

        public Builder setDistance(int distance) {
            this.distance = distance;
            return this;
        }

        public int getTabHeight() {
            return tabHeight;
        }

        public Builder setTabHeight(int tabHeight) {
            this.tabHeight = tabHeight;
            return this;
        }

        public int getImgWidth() {
            return imgWidth;
        }

        public Builder setImgWidth(int imgWidth) {
            this.imgWidth = imgWidth;
            return this;
        }

        public int getImgHeight() {
            return imgHeight;
        }

        public Builder setImgHeight(int imgHeight) {
            this.imgHeight = imgHeight;
            return this;
        }

        public int getBgColorNor() {
            return bgColorNor;
        }

        public Builder setBgColorNor(int bgColorNor) {
            this.bgColorNor = bgColorNor;
            return this;
        }

        public int getBgColorSel() {
            return bgColorSel;
        }

        public Builder setBgColorSel(int bgColorSel) {
            this.bgColorSel = bgColorSel;
            return this;
        }

        public int getTextSize() {
            return textSize;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public int getTextColorNor() {
            return textColorNor;
        }

        public Builder setTextColorNor(int textColorNor) {
            this.textColorNor = textColorNor;
            return this;
        }

        public int getTextColorSel() {
            return textColorSel;
        }

        public Builder setTextColorSel(int textColorSel) {
            this.textColorSel = textColorSel;
            return this;
        }

        public int getLineColor() {
            return lineColor;
        }

        public Builder setLineColor(int lineColor) {
            this.lineColor = lineColor;
            return this;
        }
    }

}
