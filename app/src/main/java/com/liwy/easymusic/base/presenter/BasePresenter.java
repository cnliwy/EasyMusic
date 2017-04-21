package com.liwy.easymusic.base.presenter;

import android.app.Activity;
import android.content.Context;

import com.liwy.easymusic.base.view.IView;


/**
 * Created by liwy on 2017/3/14.
 */

public class BasePresenter<V extends IView> implements IPresenter {
    public V mView;
    public Context mContext;
    public Activity mActivity;

    // 初始化方法
    public void init(V view) {
        this.mView = view;
        this.mView.initView();
    }

    public void init(V view,Context context){
        this.mView = view;
        this.mContext = context;
        this.mView.initView();
    }

    public void init(V view,Context context,Activity activity){
        this.mView = view;
        this.mContext = context;
        this.mActivity = activity;
        this.mView.initView();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        clearMemory();
    }

    @Override
    public void onCreate() {

    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public Activity getmActivity() {
        return mActivity;
    }

    public void setmActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * 为防止内存泄漏,需在页面销毁的时候清除对context实例的引用
     */
    public void clearMemory(){
        this.mContext = null;
        this.mActivity = null;
    }
}
