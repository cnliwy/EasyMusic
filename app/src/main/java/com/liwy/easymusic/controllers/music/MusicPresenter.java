package com.liwy.easymusic.controllers.music;


import android.content.Intent;

import com.liwy.easymusic.R;
import com.liwy.easymusic.adapter.MusicAdapter;
import com.liwy.easymusic.base.presenter.BasePresenter;
import com.liwy.easymusic.common.http.HttpUtils;
import com.liwy.easymusic.common.http.subscribers.ProgressSubscriber;
import com.liwy.easymusic.common.http.subscribers.SubscriberOnNextListener;
import com.liwy.easymusic.model.OnlineMusic;
import com.liwy.easymusic.model.OnlineMusicList;
import com.liwy.easymusic.service.playmusic.AppCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MusicPresenter extends BasePresenter<MusicView> {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
