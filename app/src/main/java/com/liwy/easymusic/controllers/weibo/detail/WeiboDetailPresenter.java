package com.liwy.easymusic.controllers.weibo.detail;

import com.liwy.easymusic.base.presenter.BasePresenter;


public class WeiboDetailPresenter extends BasePresenter<WeiboDetailView> {

    @Override
    public void onCreate() {
        super.onCreate();
        String url = mActivity.getIntent().getExtras().getString("url");
        String name = mActivity.getIntent().getExtras().getString("name");
        mView.initTop(name);
        mView.loadUrl(url);
    }
}
