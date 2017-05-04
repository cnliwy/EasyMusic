package com.liwy.easymusic.controllers.music;


import android.content.ComponentName;
import android.media.AudioManager;

import com.liwy.easymusic.base.presenter.BasePresenter;
import com.liwy.easymusic.receiver.RemoteControlReceiver;

import static android.content.Context.AUDIO_SERVICE;


public class MusicPresenter extends BasePresenter<MusicView> {
    private AudioManager mAudioManager;
    private ComponentName mRemoteReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver();
    }


    private void registerReceiver() {
        mAudioManager = (AudioManager) mContext.getSystemService(AUDIO_SERVICE);
        mRemoteReceiver = new ComponentName(mContext.getPackageName(), RemoteControlReceiver.class.getName());
        mAudioManager.registerMediaButtonEventReceiver(mRemoteReceiver);
    }

}
