package com.liwy.easymusic.views.tabindicator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by liwy on 2017/5/7.
 */

public class TabView extends Button {
    private Bitmap bitmap;
    private int index;          //tabview的index位置
    private int distance;   // 文字和图片的间隔
    private int imgHeight;     // 图片的高度
    private int imgWidth;      // 图片的宽度
    int parentHeight;
    int parentWidth;
    private boolean isShowLine = false;//是否显示下划线

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public boolean isShowLine() {
        return isShowLine;
    }

    public void setShowLine(boolean showLine) {
        isShowLine = showLine;
    }

    public int getDistance() {
        return distance;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    private int lineColor;//下划线颜色


    public TabView(Context context) {
        super(context,null);
    }

    public TabView(Context context,AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setClickable(true);
    }

    public void setIcon(int resourceId)
    {
        this.bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        parentHeight = this.getMeasuredHeight();
        parentWidth = this.getMeasuredWidth();
        if (isShowLine){
            if (lineColor == 0)lineColor = Color.RED;
            Paint paint = new Paint();
            paint.setColor(lineColor);
            paint.setStrokeWidth(5);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawLine(0, parentHeight, parentWidth, parentHeight, paint);
        }
        if (bitmap != null){
            this.bitmap = zoomImg(bitmap,imgWidth,imgHeight);
            int imgHeight = bitmap.getHeight();
            int textHeight = (int)this.getTextSize();
            // 图片和文字作为一个整体居中显示
            int imgX = (this.getMeasuredWidth() - bitmap.getWidth())/2;
            int imgY = (parentHeight - imgHeight - textHeight - distance)/2;
            canvas.drawBitmap(bitmap, imgX, imgY, null);
            // 坐标需要转换，因为默认情况下Button中的文字居中显示，这里计算的距离中点的y轴位移
            int textY = Math.abs((imgHeight - textHeight + distance)/2);
            //设置文字的y位置
            canvas.translate(0,textY);
        }
        super.onDraw(canvas);

    }

    public int getIndex() {
            return index;
        }

    public void setIndex(int index) {
            this.index = index;
        }

    /**
     * 设置文字和图片的间距
     * @param distance
     */
    public void setDistance(int distance) {
        this.distance = dip2px(getContext(),distance);
    }

    /**
     * 设置图片高度
     * @param imgHeight
     */
    public void setImgHeight(int imgHeight) {
        this.imgHeight = dip2px(getContext(),imgHeight);
    }

    /**
     * 设置图片宽度
     * @param imgWidth
     */
    public void setImgWidth(int imgWidth) {
        this.imgWidth = dip2px(getContext(),imgWidth);
    }

    /**
     *  处理图片
     * @param bm 所要转换的bitmap
     * @param newWidth 新的宽
     * @param newHeight 新的高
     * @return 指定宽高的bitmap
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片   www.2cto.com
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
