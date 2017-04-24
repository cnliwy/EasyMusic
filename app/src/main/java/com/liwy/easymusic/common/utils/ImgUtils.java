package com.liwy.easymusic.common.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.liwy.easymusic.R;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

/**
 * Glide图片加载框架的使用封装类  
 * 基础的图片操作封装类
 * Created by forezp on 16/8/10.
 */
public class ImgUtils {
    private static ImgUtils instance;
    private ImgUtils(){}
    public static ImgUtils getInstance(){
        if(instance==null){
            instance =new ImgUtils();
        }
        return  instance;
    }


    public void display(Context context, String url, ImageView imageView){
        Glide.with(context).load(url).into(imageView);
    }


    /**
     * 根据资源id获取drawable
     * @param mContext
     * @param rid
     * @return
     */
    public static Drawable getDrawable(Context mContext,int rid){
        Drawable drawable = null;
        if (Build.VERSION.SDK_INT >= LOLLIPOP) {
            drawable = mContext.getResources().getDrawable(rid, null);
        }else{
            drawable = mContext.getResources().getDrawable(rid);
        }
        return drawable;
    }

}
