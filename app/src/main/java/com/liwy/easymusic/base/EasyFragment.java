package com.liwy.easymusic.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liwy.easymusic.common.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liwy on 2017/4/26.
 */

public abstract class EasyFragment extends Fragment {
    public Activity mActivity;
    public Context mContext;
    public Unbinder unbinder;
    private String title;
    private int iconId;
    //获取LayoutId
    protected abstract int getLayoutResId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        mActivity = (Activity) getActivity();
        mContext = getContext();
        unbinder = ButterKnife.bind(this,view);
        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    // toast提示
    public void showToast(String msg) {
        ToastUtils.showToast(getContext().getApplicationContext(),msg);
    }
    // 跳转至下个页面
    public void turnToActivity(Class className){
        Intent intent = new Intent(mContext,className);
        startActivity(intent);
    }
    // 跳转至下个页面并传参
    public void turnToActivity(Class className,Bundle bundle){
        Intent intent = new Intent(mContext,className);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    // 跳转至下个页面并销毁
    public void turnToActivityWithFinish(Class className){
        Intent intent = new Intent(mContext,className);
        startActivity(intent);
    }
    // 跳转至下个页面并传参，并销毁
    public void turnToActivityWithFinish(Class className,Bundle bundle){
        Intent intent = new Intent(mContext,className);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
