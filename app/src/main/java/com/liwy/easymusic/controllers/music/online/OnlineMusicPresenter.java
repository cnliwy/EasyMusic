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
import com.liwy.easymusic.common.http.subscribers.SubscriberOnNextListener;
import com.liwy.easymusic.model.Music;
import com.liwy.easymusic.model.OnlineMusic;
import com.liwy.easymusic.model.OnlineMusicList;
import com.liwy.easymusic.service.playmusic.PlayOnlineMusic;
import com.orhanobut.logger.Logger;

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
        subscriber = new ProgressSubscriber<OnlineMusicList>(new SubscriberOnNextListener<OnlineMusicList>() {
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

    public void onItemClick(int position){
        playMusic(dataList.get(position));
    }

    public void playMusic(OnlineMusic onlineMusic){
            new PlayOnlineMusic(mActivity, onlineMusic) {
                @Override
                public void onPrepare() {
//                    mProgressDialog.show();
                }

                @Override
                public void onExecuteSuccess(Music music) {
//                    mProgressDialog.cancel();
                    getPlayService().play(music);
                    ToastUtils.show(getString(R.string.now_play, music.getTitle()));
                }

                @Override
                public void onExecuteFail(Exception e) {
//                    mProgressDialog.cancel();
                    ToastUtils.show(R.string.unable_to_play);
                }
            }.execute();
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
}
