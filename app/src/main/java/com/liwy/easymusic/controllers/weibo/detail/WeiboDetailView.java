package com.liwy.easymusic.controllers.weibo.detail;

import com.liwy.easymusic.base.view.IView;

public interface WeiboDetailView extends IView {
    public void initTop(String name);
    public void loadUrl(String url);
}
