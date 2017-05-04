package com.liwy.easymusic.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liwy.easymusic.base.presenter.BaseFragmentPresenter;
import com.liwy.easymusic.base.view.IView;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;

/**
 * Created by liwy on 2016/12/9.
 */

public abstract class BaseFragment<T extends BaseFragmentPresenter> extends EasyFragment implements IView {
    public T mPresenter;
    // 获取presenter
    protected  BaseFragmentPresenter getPresenter(){
        return mPresenter;
    }
    //初始化presenter
    protected abstract void  initPresenter();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initPresenter();
        mContext = context;
        mActivity = getActivity();
        mPresenter.setContext(context);
        mPresenter.setActivity(mActivity);
        mPresenter.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        unbinder = ButterKnife.bind(this,view);
        mPresenter.onCreateView(inflater,container,savedInstanceState);
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
        mPresenter.onViewCreated(view,savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.onCreate();
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.onDetach();
    }
}
