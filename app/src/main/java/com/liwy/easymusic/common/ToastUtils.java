package com.liwy.easymusic.common;

import android.content.Context;
import android.widget.Toast;

import com.liwy.easymusic.MyApplication;

/**
 * Created by liwy on 2017/3/13.
 */

public class ToastUtils {
    private static Context context;
    /** 之前显示的内容 */
    private static String oldMsg ;
    /** Toast对象 */
    private static Toast toast = null;
    /** 第一次时间 */
    private static long oneTime = 0 ;
    /** 第二次时间 */
    private static long twoTime = 0 ;

    /**
     * 显示Toast
.    * @param context
     * @param message
     */
      public static void showToast(Context context, String message){

          if(toast == null){
              oneTime = System.currentTimeMillis();
              toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
              toast.show() ;
          }else{
              twoTime = System.currentTimeMillis();
              if(message.equals(oldMsg)){
                  if(twoTime - oneTime > Toast.LENGTH_SHORT){
                      toast.show() ;
                  }
              }else{
                  oldMsg = message ;
                  toast.setText(message) ;
                  toast.show() ;
              }
          }
          oneTime = twoTime ;
      }

    /**
     * 显示Toast
     * @param message
     */
    public static void show(String message){
        if (context == null) context = MyApplication.getInstance().getApplicationContext();

        if(toast == null){
            oneTime = System.currentTimeMillis();
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.show() ;
        }else{
            twoTime = System.currentTimeMillis();
            if(message.equals(oldMsg)){
                if(twoTime - oneTime > Toast.LENGTH_SHORT){
                    toast.show() ;
                }
            }else{
                oldMsg = message ;
                toast.setText(message) ;
                toast.show() ;
            }
        }
        oneTime = twoTime ;
    }

    public static void show(int resId) {
        if (context == null) context = MyApplication.getInstance().getApplicationContext();
        show(context.getString(resId));
    }
}
