package com.liwy.easymusic.controllers.weibo;

import android.view.Gravity;

import com.liwy.easymusic.R;
import com.liwy.easymusic.base.BaseActivity;


public class WeiboActivity extends BaseActivity<WeiboPresenter> implements WeiboView {


    @Override
    public void initView() {
        initToolbarWithBack(TOOLBAR_MODE_CENTER,getString(R.string.title_weibo), R.drawable.ic_menu, new OnLeftClickListener() {
            @Override
            public void onLeftClick() {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        initSlideMenu();
    }

    // init presenter
    @Override
    protected void initPresenter() {
        mPresenter = new WeiboPresenter();
        mPresenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_weibo;

    }
}
