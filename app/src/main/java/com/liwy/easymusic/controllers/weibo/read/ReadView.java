package com.liwy.easymusic.controllers.weibo.read;

import com.liwy.easymusic.adapter.WeiboAdapter;
import com.liwy.easymusic.base.view.IView;

public interface ReadView extends IView {
    public void setAdapter(WeiboAdapter adapter);
    public void finishRefresh();
}
