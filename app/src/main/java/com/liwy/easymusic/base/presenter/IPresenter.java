package com.liwy.easymusic.base.presenter;

/**
 * Created by liwy on 2016/11/16.
 */

public interface IPresenter {
    public void onStart();
    public void onResume();
    public void onPause();
    public void onStop();
    public void onDestroy();
    public void onCreate();
}
