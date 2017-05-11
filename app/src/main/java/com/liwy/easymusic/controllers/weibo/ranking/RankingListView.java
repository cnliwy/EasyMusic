package com.liwy.easymusic.controllers.weibo.ranking;

import com.liwy.easymusic.adapter.WeiboAdapter;
import com.liwy.easymusic.base.view.IView;

public interface RankingListView extends IView {
    public void setAdapter(WeiboAdapter adapter);
    public void finishRefresh();
}
