package com.liwy.easymusic.common.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Glide图片加载框架的使用封装类  
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

}
