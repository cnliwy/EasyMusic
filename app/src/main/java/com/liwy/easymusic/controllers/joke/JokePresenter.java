package com.liwy.easymusic.controllers.joke;


import com.liwy.easymusic.adapter.JokeAdapter;
import com.liwy.easymusic.base.presenter.BasePresenter;
import com.liwy.easymusic.common.http.HttpJokeUtils;
import com.liwy.easymusic.common.http.subscribers.ProgressSubscriber;
import com.liwy.easymusic.common.http.subscribers.HttpCallback;
import com.liwy.easymusic.model.happy.BaseHappyResult;
import com.liwy.easymusic.model.happy.Joke;
import com.liwy.easymusic.model.happy.JokeResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JokePresenter extends BasePresenter<JokeView> {
    private List<Joke> datas = new ArrayList<Joke>();

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void initData(){
        datas = new ArrayList<Joke>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String timeStr = sdf.format(new Date());
        HttpJokeUtils.getInstance().getJokes(timeStr,"1",new ProgressSubscriber<BaseHappyResult<JokeResult>>(new HttpCallback<BaseHappyResult<JokeResult>>() {
            @Override
            public void onNext(BaseHappyResult<JokeResult> result) {
                if (result.getResult().getContentList() != null){
                    datas = result.getResult().getContentList();
                    JokeAdapter adapter = new JokeAdapter(mContext,datas);
                    mView.setAdapter(adapter);
                }
            }
            @Override
            public void onFail(Throwable e) {
                JokeAdapter adapter = new JokeAdapter(mContext,datas);
                mView.setAdapter(adapter);
            }
        },mContext,true));
    }
}
