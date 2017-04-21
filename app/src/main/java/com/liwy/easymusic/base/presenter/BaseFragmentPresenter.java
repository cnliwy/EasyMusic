package com.liwy.easymusic.base.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liwy.easymusic.base.view.IView;


/**
 * Created by liwy on 2017/3/14.
 */

public class BaseFragmentPresenter<V extends IView> implements IFragmentPresenter {
    public V mView;//fragment view
    public Context mContext;
    public Activity mActivity;


    public void init(V view) {
        this.mView = view;
    }

    public void init(V view,Context context){
        this.mView = view;
        this.mContext = context;
    }

    public void init(V view,Context context,Activity activity){
        this.mView = view;
        this.mContext = context;
        this.mActivity = activity;
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

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
