package com.liwy.easymusic.service.playmusic;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.util.LongSparseArray;
import android.util.DisplayMetrics;


import com.liwy.easymusic.base.BaseActivity;
import com.liwy.easymusic.common.ToastUtils;
import com.liwy.easymusic.common.utils.Preferences;
import com.liwy.easymusic.common.utils.ScreenUtils;
import com.liwy.easymusic.model.Music;
import com.liwy.easymusic.model.SongListInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 音乐播放缓存类
 */
public class AppCache {
    private Context mContext;
    private PlayService mPlayService;
    // 本地歌曲列表
    private final List<Music> mMusicList = new ArrayList<>();
    // 歌单列表
    private final List<SongListInfo> mSongListInfos = new ArrayList<>();
    private final List<BaseActivity> mActivityStack = new ArrayList<>();
    private final LongSparseArray<String> mDownloadList = new LongSparseArray<>();

    private AppCache() {
    }

    private static class SingletonHolder {
        private static AppCache sAppCache = new AppCache();
    }

    public static AppCache getInstance() {
        return SingletonHolder.sAppCache;
    }

    public static void init(Context context) {
        getInstance().onInit(context);
    }

    private void onInit(Context context) {
        mContext = context.getApplicationContext();
        Preferences.init(mContext);
        ScreenUtils.init(mContext);
//        CrashHandler.getInstance().init();
        startService(mContext);
    }

    public static Context getContext() {
        return getInstance().mContext;
    }

    public static PlayService getPlayService() {
        return getInstance().mPlayService;
    }

    public static void setPlayService(PlayService service) {
        getInstance().mPlayService = service;
    }

    public static void updateNightMode(boolean on) {
        Resources resources = getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.uiMode &= ~Configuration.UI_MODE_NIGHT_MASK;
        config.uiMode |= on ? Configuration.UI_MODE_NIGHT_YES : Configuration.UI_MODE_NIGHT_NO;
        resources.updateConfiguration(config, dm);
    }

    public static List<Music> getMusicList() {
        return getInstance().mMusicList;
    }

    public static List<SongListInfo> getSongListInfos() {
        return getInstance().mSongListInfos;
    }

    public static void addToStack(BaseActivity activity) {
        getInstance().mActivityStack.add(activity);
    }

    public static void removeFromStack(BaseActivity activity) {
        getInstance().mActivityStack.remove(activity);
    }

    public static void clearStack() {
        List<BaseActivity> activityStack = getInstance().mActivityStack;
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            BaseActivity activity = activityStack.get(i);
            activityStack.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static LongSparseArray<String> getDownloadList() {
        return getInstance().mDownloadList;
    }


    private void startService(Context context) {
        Intent intent = new Intent(context, PlayService.class);
        context.startService(intent);
    }
}
