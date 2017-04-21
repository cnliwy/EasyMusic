package com.liwy.easymusic;

import android.app.Activity;
import android.app.Application;

import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

/**
 * Created by liwy on 2017/3/14.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;
    // 页面集合
    private ArrayList<Activity> activityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("暮醉南山");
        myApplication = this;
        // 初始化友盟统计
//        initYM();
        //自定义异常处理
//        CrashHandler handler = CrashHandler.getInstance();
//        handler.init(getApplicationContext());
        // 内存泄漏监控插件
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
    // 初始化友盟统计
    private void initYM(){
        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(getApplicationContext(),"58c7ad39bbea831db8001f59","pugongying", MobclickAgent.EScenarioType.E_UM_NORMAL,true);
        MobclickAgent.setCatchUncaughtExceptions(true);
        MobclickAgent.startWithConfigure(config);
        // 设置为集成模式，错误信息不会出现在正式数据里
        MobclickAgent.setDebugMode( true );
    }

    public static MyApplication getInstance(){
        return myApplication;
    }

    //开启新页面，需添加到集合中，方便退出统一管理
    public void addActivity(Activity activity){
        activityList.add(activity);
    }
    public void removeActivity(Activity activity){
        activityList.remove(activity);
    }
    //退出清空所有的activity
    public void clearAllActivities(){
        if (activityList.size() > 0){
            for(Activity activity : activityList){
                if (!activity.isFinishing()){
                    activity.finish();
                }
            }
        }
    }

    // 退出程序
    public void exit(){
        clearAllActivities();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
