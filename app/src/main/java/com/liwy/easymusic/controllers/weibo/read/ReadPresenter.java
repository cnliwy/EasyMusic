package com.liwy.easymusic.controllers.weibo.read;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.liwy.easymusic.adapter.TextJokeAdapter;
import com.liwy.easymusic.adapter.WeiboAdapter;
import com.liwy.easymusic.base.presenter.BaseFragmentPresenter;
import com.liwy.easymusic.common.ToastUtils;
import com.liwy.easymusic.common.http.HttpLifeUtils;
import com.liwy.easymusic.common.http.subscribers.HttpCallback;
import com.liwy.easymusic.common.http.subscribers.ProgressSubscriber;
import com.liwy.easymusic.controllers.weibo.detail.WeiboDetailActivity;
import com.liwy.easymusic.model.happy.Joke;
import com.liwy.easymusic.model.happy.RootResult;
import com.liwy.easymusic.model.happy.Weibo;
import com.liwy.easymusic.model.happy.WeiboResult;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;


public class ReadPresenter extends BaseFragmentPresenter<ReadView> {

    private List<Weibo> datas = new ArrayList<Weibo>();
    private int currentPage = 1;
    private int allPages;
    WeiboAdapter adapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    /**
     * 初始化（刷新）事件
     */
    public void initData(){
        datas = new ArrayList<Weibo>();
        getReadWeibos(currentPage,"day",true);
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
     *
     * @param page //页码
     * @param space //榜单类型
     */
    public void getReadWeibos(int page, String space, final boolean isRefresh){
        HttpLifeUtils.getInstance().getWeibos("28",space,page,new ProgressSubscriber<WeiboResult>(new HttpCallback<WeiboResult>() {
            @Override
            public void onNext(WeiboResult result) {
//                System.out.println("共有多少条微博：" + baseResultRootResult.getResult().getPageBean().getContentList().size());
//                ToastUtils.show("共有多少条微博：" + baseResultRootResult.getResult().getPageBean().getContentList().size());
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
                System.out.println("微博数据请求失败");
                ToastUtils.show("微博数据请求失败");
                if (isRefresh){
                    adapter = new WeiboAdapter(mContext,datas);
                    mView.setAdapter(adapter);
                }
                mView.finishRefresh();
            }
        }, mContext, isRefresh));
    }

    public void onItemClick(int position){
        Bundle bundle = new Bundle();
        bundle.putString("url",datas.get(position).getUrl());
        mView.turnToActivity(WeiboDetailActivity.class,bundle);
    }

}
