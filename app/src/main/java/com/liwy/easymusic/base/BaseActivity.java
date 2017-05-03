package com.liwy.easymusic.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.liwy.easymusic.base.presenter.BasePresenter;
import com.liwy.easymusic.base.view.IView;

/**
 * 抽象基类
 * Created by liwy on 2016/11/16.
 */

public abstract class BaseActivity<T extends BasePresenter> extends EasyActivity implements IView {
    public T mPresenter;

    protected  BasePresenter getPresenter(){
        return mPresenter;
    }

    /**
     * 初始化presenters
     */
    protected abstract void  initPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化presenter
        initPresenter();
        mPresenter.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
