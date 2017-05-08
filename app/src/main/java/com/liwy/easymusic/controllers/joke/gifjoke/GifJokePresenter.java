package com.liwy.easymusic.controllers.joke.gifjoke;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.liwy.easymusic.adapter.ImgJokeAdapter;
import com.liwy.easymusic.base.presenter.BaseFragmentPresenter;
import com.liwy.easymusic.common.ToastUtils;
import com.liwy.easymusic.common.http.HttpJokeUtils;
import com.liwy.easymusic.common.http.subscribers.HttpCallback;
import com.liwy.easymusic.common.http.subscribers.ProgressSubscriber;
import com.liwy.easymusic.model.happy.BaseHappyResult;
import com.liwy.easymusic.model.happy.Joke;
import com.liwy.easymusic.model.happy.JokeResult;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.liwy.easymusic.common.http.HttpJokeUtils.maxResult;


public class GifJokePresenter extends BaseFragmentPresenter<GifJokeView> {
    private List<Joke> datas = new ArrayList<Joke>();
    private int currentPage = 1;    //当前页
    private int allPages;
    private int allNums;
    ImgJokeAdapter adapter;
    private int currentIndex = -1;   //当前序列

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    /**
     * 初始化（刷新）事件
     */
    public void initData(){
        datas = new ArrayList<Joke>();
        HttpJokeUtils.getInstance().getGifJokes(String.valueOf(1),new ProgressSubscriber<BaseHappyResult<JokeResult>>(new HttpCallback<BaseHappyResult<JokeResult>>() {
            @Override
            public void onNext(BaseHappyResult<JokeResult> result) {
                Logger.d(result.getResult().toString());
                if (result.getResult().getContentList() != null){
                    currentPage=1;
                    datas = result.getResult().getContentList();
                    allNums = result.getResult().getAllNum();
                    allPages = result.getResult().getAllPages();
                    adapter = new ImgJokeAdapter(mContext,datas);
                    mView.setAdapter(adapter);
                    next();
                }
            }
            @Override
            public void onFail(Throwable e) {
                adapter = new ImgJokeAdapter(mContext,datas);
                mView.setAdapter(adapter);
            }
        },mContext,true));
    }

    /**
     * 下一个
     */
    public void next(){
        if (datas == null || datas.size() < 1){
            initData();
            return;
        }
        if (currentIndex == currentPage * maxResult - 1){
            loadMore();
        }else{
            currentIndex++;
            Joke joke = datas.get(currentIndex);
            mView.updateNext(joke);
        }
    }

    /**
     * 加载更多事件
     */
    public void loadMore(){
        if (currentPage + 1 > allPages){
            ToastUtils.show("已经是最后一页啦！");
            return;
        }
        HttpJokeUtils.getInstance().getGifJokes(String.valueOf(currentPage + 1),new ProgressSubscriber<BaseHappyResult<JokeResult>>(new HttpCallback<BaseHappyResult<JokeResult>>() {
            @Override
            public void onNext(BaseHappyResult<JokeResult> result) {
                currentPage++;
                if (result.getResult().getContentList() != null){
                    List<Joke> jokes = result.getResult().getContentList();
                    if (jokes != null && jokes.size() > 0)datas.addAll(jokes);
                    adapter.notifyDataSetChanged();
                    next();
                }
                mView.finishRefresh();
            }
            @Override
            public void onFail(Throwable e) {
                mView.finishRefresh();
            }
        },mContext,false));
    }
}
