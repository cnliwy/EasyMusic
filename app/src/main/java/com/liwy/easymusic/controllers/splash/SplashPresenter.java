package com.liwy.easymusic.controllers.splash;


import com.liwy.easymusic.base.presenter.BasePresenter;
import com.liwy.easymusic.controllers.music.MusicActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by liwy on 2017/3/21.
 */

public class SplashPresenter extends BasePresenter<SplashView> {
    @Override
    public void onResume() {
        super.onResume();
        toMain();
    }

    public void toMain(){
       Observable.timer(3000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
           @Override
           public void call(Long aLong) {
               mView.turnToActivityWithFinish(MusicActivity.class);
           }
       });
   }
}
