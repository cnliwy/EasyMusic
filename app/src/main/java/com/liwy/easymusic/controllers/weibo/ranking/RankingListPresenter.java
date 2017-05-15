package com.liwy.easymusic.controllers.weibo.ranking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.liwy.easymusic.adapter.WeiboAdapter;
import com.liwy.easymusic.base.presenter.BaseFragmentPresenter;
import com.liwy.easymusic.common.ToastUtils;
import com.liwy.easymusic.common.http.HttpLifeUtils;
import com.liwy.easymusic.common.http.subscribers.HttpCallback;
import com.liwy.easymusic.common.http.subscribers.ProgressSubscriber;
import com.liwy.easymusic.controllers.weibo.detail.WeiboDetailActivity;
import com.liwy.easymusic.model.happy.Weibo;
import com.liwy.easymusic.model.happy.WeiboResult;

import java.util.ArrayList;
import java.util.List;


public class RankingListPresenter extends BaseFragmentPresenter<RankingListView> {
    public static final String SPACE_DAY = "day";
    public static final String SPACE_WEEK = "week";
    public static final String SPACE_MONTH = "month";

    private List<Weibo> datas = new ArrayList<Weibo>();
    private int currentPage = 1;
    private int allPages;
    WeiboAdapter adapter;
    public String currentSpace = "";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentSpace = SPACE_DAY;
        initData();
    }

    /**
     * 初始化（刷新）事件
     */
    public void initData(){
        datas = new ArrayList<Weibo>();
        currentPage = 1;
        getReadWeibos(currentPage,currentSpace,true);
    }

    /**
     * 加载更多事件
     */
    public void loadMore(){
        if (currentPage + 1 > allPages){
            ToastUtils.show("已经是最后一页啦！");
            return;
        }
        getReadWeibos(currentPage,"day",false);
    }

    /**
     * 天周月榜单
     * @param index
     */
    public void selectRanklistType(int index){
        switch (index){
            case 0:
                currentSpace = SPACE_DAY;
                break;
            case 1:
                currentSpace = SPACE_WEEK;
                break;
            case 2:
                currentSpace = SPACE_MONTH;
                break;
        }
        currentPage = 1;
        getReadWeibos(currentPage,currentSpace,true);
    }
    /**
     *
     * @param page //页码
     * @param space //榜单类型
     */
    public void getReadWeibos(int page, String space, final boolean isRefresh){
        HttpLifeUtils.getInstance().getWeibos("28",space,page,new ProgressSubscriber<WeiboResult>(new HttpCallback<WeiboResult>() {
            @Override
            public void onNext(WeiboResult result) {
                if (isRefresh){
                    if (result.getResult().getPageBean().getContentList() != null){
                        currentPage=1;
                        datas = result.getResult().getPageBean().getContentList();
                        allPages = result.getResult().getPageBean().getAllPages();
                        adapter = new WeiboAdapter(mContext,datas);
                        mView.setAdapter(adapter);
                    }
                }else{
                    currentPage++;
                    if (result.getResult().getPageBean().getContentList() != null){
                        List<Weibo> weibos = result.getResult().getPageBean().getContentList();
                        if (weibos != null && weibos.size() > 0)datas.addAll(weibos);
                        adapter.notifyDataSetChanged();
                    }

                }
                mView.finishRefresh();
            }

            @Override
            public void onFail(Throwable e) {
                if (isRefresh){
                    adapter = new WeiboAdapter(mContext,datas);
                    mView.setAdapter(adapter);
                }
                mView.finishRefresh();
            }
        }, mContext, isRefresh));
    }

    /**
     * item点击事件
     * @param position
     */
    public void onItemClick(int position){
        Bundle bundle = new Bundle();
        bundle.putString("url",datas.get(position).getUrl());
        bundle.putString("name",datas.get(position).getName());
        mView.turnToActivity(WeiboDetailActivity.class,bundle);
    }

}
