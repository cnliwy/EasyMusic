package com.liwy.easymusic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.liwy.easymusic.constants.Actions;
import com.liwy.easymusic.service.playmusic.PlayService;


/**
 * 来电/耳机拔出时暂停播放
 */
public class NoisyAudioStreamReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PlayService.startCommand(context, Actions.ACTION_MEDIA_PLAY_PAUSE);
    }
}
