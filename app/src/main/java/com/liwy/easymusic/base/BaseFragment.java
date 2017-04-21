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


import com.liwy.easymusic.base.presenter.BaseFragmentPresenter;
import com.liwy.easymusic.base.presenter.IPresenter;
import com.liwy.easymusic.base.view.IView;
import com.liwy.easymusic.common.ToastUtils;

import java.util.HashSet;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liwy on 2016/12/9.
 */

public abstract class BaseFragment<T extends BaseFragmentPresenter> extends Fragment implements IView {
    private HashSet<IPresenter> mPresenters = new HashSet<IPresenter>();
    public T mPresenter;
    public Activity mActivity;
    public Context mContext;
    private Unbinder unbinder;
    private String title;
    private int iconId;

    // 获取presenter
    protected  BaseFragmentPresenter getPresenter(){
        return mPresenter;
    }
    //初始化presenter
    protected abstract void  initPresenter();

    //获取LayoutId
    protected abstract int getLayoutResId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        mActivity = (Activity) getActivity();
        mContext = getContext();
        unbinder = ButterKnife.bind(this,view);
        initPresenter();
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
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
