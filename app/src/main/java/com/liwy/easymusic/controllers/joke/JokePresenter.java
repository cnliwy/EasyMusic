package com.liwy.easymusic.controllers.joke;


import com.liwy.easymusic.adapter.JokeAdapter;
import com.liwy.easymusic.base.presenter.BasePresenter;
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

public class JokePresenter extends BasePresenter<JokeView> {
    private List<Joke> datas = new ArrayList<Joke>();
    private int currentPage = 1;
    String timeStr = "2000-05-21";
    private int allPages;
    private int allNums;
    JokeAdapter adapter;

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }
    /**
     * 初始化（刷新）事件
     */
    public void initData(){
        datas = new ArrayList<Joke>();
        HttpJokeUtils.getInstance().getJokes(timeStr,String.valueOf(1),new ProgressSubscriber<BaseHappyResult<JokeResult>>(new HttpCallback<BaseHappyResult<JokeResult>>() {
            @Override
            public void onNext(BaseHappyResult<JokeResult> result) {
                Logger.d(result.getResult().toString());
                if (result.getResult().getContentList() != null){
                    currentPage=1;
                    datas = result.getResult().getContentList();
                    allNums = result.getResult().getAllNum();
                    allPages = result.getResult().getAllPages();
                    adapter = new JokeAdapter(mContext,datas);
                    mView.setAdapter(adapter);
                }
            }
            @Override
            public void onFail(Throwable e) {
                Logger.d("失败咯");
                adapter = new JokeAdapter(mContext,datas);
                mView.setAdapter(adapter);
            }
        },mContext,true));
    }

    /**
     * 加载更多事件
     */
    public void loadMore(){
        if (currentPage + 1 > allPages){
            ToastUtils.show("已经是最后一页啦！");
            return;
        }
        HttpJokeUtils.getInstance().getJokes(timeStr,String.valueOf(currentPage + 1),new ProgressSubscriber<BaseHappyResult<JokeResult>>(new HttpCallback<BaseHappyResult<JokeResult>>() {
            @Override
            public void onNext(BaseHappyResult<JokeResult> result) {
                Logger.d("加载成功");
                currentPage++;
                if (result.getResult().getContentList() != null){
                    List<Joke> jokes = result.getResult().getContentList();
                    if (jokes != null && jokes.size() > 0)datas.addAll(jokes);
                    adapter.notifyDataSetChanged();
                }
                mView.finishRefresh();
            }
            @Override
            public void onFail(Throwable e) {
                Logger.d("加载失败");
                mView.finishRefresh();
            }
        },mContext,false));
    }
}
