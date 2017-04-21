package com.liwy.easymusic.controllers.music;


import com.liwy.easymusic.adapter.MusicAdapter;
import com.liwy.easymusic.base.presenter.BasePresenter;
import com.liwy.easymusic.common.http.HttpUtils;
import com.liwy.easymusic.common.http.subscribers.ProgressSubscriber;
import com.liwy.easymusic.common.http.subscribers.SubscriberOnNextListener;
import com.liwy.easymusic.entity.MusicRoot;
import com.liwy.easymusic.entity.Musics;

import java.util.ArrayList;
import java.util.List;


public class MusicPresenter extends BasePresenter<MusicView> {
    private List<Musics> dataList = new ArrayList<>();
    private MusicAdapter adapter;
    ProgressSubscriber<MusicRoot> subscriber;

    public void initAdapter(){
        adapter = new MusicAdapter(mContext,dataList);
        mView.setAdapter(adapter);
    }
    // 数据请求
    public void getMusicByTag(String tag){
        subscriber =  new ProgressSubscriber<MusicRoot>(new SubscriberOnNextListener<MusicRoot>() {
            @Override
            public void onNext(MusicRoot musicRoot) {
                dataList = musicRoot.getMusics();
                initAdapter();
                mView.finishRefresh();
            }
        },mContext,true);
        HttpUtils.getInstance().searchMusicByTag(tag,subscriber);
    }

   // 加载数据
    public void loadMoreMusicByTag(String tag){
        subscriber =  new ProgressSubscriber<MusicRoot>(new SubscriberOnNextListener<MusicRoot>() {
            @Override
            public void onNext(MusicRoot musicRoot) {
                dataList.addAll(musicRoot.getMusics());
                mView.finishLoad();
            }
        },mContext,false);
        HttpUtils.getInstance().searchMusicByTag(tag,subscriber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 当页面销毁的时候取消http请求和取消订阅
        if (subscriber.isUnsubscribed()){
            subscriber.unsubscribe();
        }
    }
}
