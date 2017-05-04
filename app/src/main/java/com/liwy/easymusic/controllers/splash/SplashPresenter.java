package com.liwy.easymusic.controllers.splash;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.liwy.easymusic.base.presenter.BasePresenter;
import com.liwy.easymusic.controllers.music.MusicActivity;
import com.liwy.easymusic.service.playmusic.AppCache;
import com.liwy.easymusic.service.playmusic.PlayService;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by liwy on 2017/3/21.
 */

public class SplashPresenter extends BasePresenter<SplashView> {
    private ServiceConnection mPlayServiceConnection;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCache.init(mContext);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkService();
    }

    public void toMain(){
       Observable.timer(2000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
           @Override
           public void call(Long aLong) {
               mView.turnToActivityWithFinish(MusicActivity.class);
           }
       });
   }

    private void checkService() {
        if (AppCache.getPlayService() == null) {
            bindService();
        }
        toMain();
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(mContext, PlayService.class);
        mPlayServiceConnection = new PlayServiceConnection();
        mContext.bindService(intent, mPlayServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            final PlayService playService = ((PlayService.PlayBinder) service).getService();
            AppCache.setPlayService(playService);
            playService.updateMusicList();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }
}
