package com.liwy.easymusic.controllers.music.online;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.liwy.easymusic.R;
import com.liwy.easymusic.adapter.MusicAdapter;
import com.liwy.easymusic.base.presenter.BaseFragmentPresenter;
import com.liwy.easymusic.common.ToastUtils;
import com.liwy.easymusic.common.http.HttpUtils;
import com.liwy.easymusic.common.http.subscribers.ProgressSubscriber;
import com.liwy.easymusic.common.http.subscribers.HttpCallback;
import com.liwy.easymusic.model.Music;
import com.liwy.easymusic.model.OnlineMusic;
import com.liwy.easymusic.model.OnlineMusicList;
import com.liwy.easymusic.service.playmusic.AppCache;
import com.liwy.easymusic.service.playmusic.PlayOnlineMusic;
import com.liwy.easymusic.service.playmusic.PlayService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class OnlineMusicPresenter extends BaseFragmentPresenter<OnlineMusicView> {
    private List<OnlineMusic> dataList = new ArrayList<>();
    private MusicAdapter adapter;
    ProgressSubscriber<OnlineMusicList> subscriber;
    private String[] musicTypes;
    private int currentPage = 0;
    private int pageSize = 20;
    private int musicType = 0;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        musicTypes = mContext.getResources().getStringArray(R.array.online_music_list_type);
        musicType = new Random().nextInt(musicTypes.length);
        getMusicList(currentPage,true);
    }

    public void getMusicList(int page, final boolean isRefresh){
        musicType = new Random().nextInt(musicTypes.length);
        subscriber = new ProgressSubscriber<OnlineMusicList>(new HttpCallback<OnlineMusicList>() {
            @Override
            public void onNext(OnlineMusicList onlineMusicList) {
                if (isRefresh){
                    currentPage = 1;
                    dataList = onlineMusicList.getSong_list();
                }else{
                    dataList.addAll(onlineMusicList.getSong_list());
                }
                currentPage++;
                initAdapter(isRefresh);
                mView.finishRefresh();
            }

            @Override
            public void onFail(Throwable e) {
                mView.finishRefresh();
            }
        },mContext,true);
        HttpUtils.getInstance().searchMusicByConditions(String.valueOf(musicTypes[musicType]),pageSize,page*pageSize,subscriber);
    }

    public void initAdapter(boolean isRefresh){
        if (isRefresh){
            adapter = new MusicAdapter(mContext,dataList);
            mView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }
    // item点击事件,点击播放音乐
    public void onItemClick(int position){
        AppCache.getPlayService().setPlayNetStyle(true);
        AppCache.getPlayService().setOnlineMusicList(dataList);
        AppCache.getPlayService().playOnlieMusic(position);
    }

    // 加载数据
    public void loadMoreMusic(){
        getMusicList(currentPage,false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 当页面销毁的时候取消http请求和取消订阅
        if (subscriber.isUnsubscribed()){
            subscriber.unsubscribe();
        }
    }

    public PlayService getPlayService() {
        PlayService playService = AppCache.getPlayService();
        if (playService == null) {
            throw new NullPointerException("play service is null");
        }
        return playService;
    }
}
