package com.liwy.easymusic.controllers.splash;

import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.liwy.easymusic.R;
import com.liwy.easymusic.base.BaseActivity;

import butterknife.BindView;



public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashView{
    public @BindView(R.id.tv_device_id) TextView deviceTv;

    @Override
    public void initView() {
        deviceTv.setText(getString(R.string.author));
    }
    // 初始化presenter
    @Override
    protected void initPresenter() {
        mPresenter = new SplashPresenter();
        mPresenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 移除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_splash;
    }
}
