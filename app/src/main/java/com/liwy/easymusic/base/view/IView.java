package com.liwy.easymusic.base.view;

import android.os.Bundle;

/**
 * Created by liwy on 2016/11/16.
 */

public interface IView {
    /**
     * 初始化view
     */
    public void initView();

    public void showToast(String msg);

    // 跳转至下个页面
    public void turnToActivity(Class className);

    // 跳转至下个页面并传参
    public void turnToActivity(Class className, Bundle bundle);

    // 跳转至下个页面并销毁
    public void turnToActivityWithFinish(Class className);

    // 跳转至下个页面并传参，并销毁
    public void turnToActivityWithFinish(Class className, Bundle bundle);
}
