package com.liwy.easymusic.controllers.music;


import android.content.ComponentName;
import android.media.AudioManager;

import com.liwy.easymusic.base.presenter.BasePresenter;
import com.liwy.easymusic.receiver.RemoteControlReceiver;

import static android.content.Context.AUDIO_SERVICE;
import static com.liwy.easymusic.service.playmusic.AppCache.getPlayService;


public class MusicPresenter extends BasePresenter<MusicView> {
    private AudioManager mAudioManager;
    private ComponentName mRemoteReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPlayService().setOnPlayEventListener(null);
    }

    /**
     * 初始化监听广播
     */
    private void registerReceiver() {
        mAudioManager = (AudioManager) mContext.getSystemService(AUDIO_SERVICE);
        mRemoteReceiver = new ComponentName(mContext.getPackageName(), RemoteControlReceiver.class.getName());
        mAudioManager.registerMediaButtonEventReceiver(mRemoteReceiver);
    }

    public void play() {
        getPlayService().playPause();
    }

    public void next() {
        getPlayService().next();
    }

}
