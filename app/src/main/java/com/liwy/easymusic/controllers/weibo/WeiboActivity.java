package com.liwy.easymusic.controllers.weibo;

import android.support.annotation.RequiresPermission;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Gravity;

import com.liwy.easymusic.R;
import com.liwy.easymusic.adapter.FragmentAdapter;
import com.liwy.easymusic.base.BaseActivity;
import com.liwy.easymusic.controllers.weibo.read.ReadFragment;

import butterknife.BindView;


public class WeiboActivity extends BaseActivity<WeiboPresenter> implements WeiboView {

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    public void initView() {
        initToolbarWithBack(TOOLBAR_MODE_CENTER,getString(R.string.title_weibo), R.drawable.ic_menu, new OnLeftClickListener() {
            @Override
            public void onLeftClick() {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        initSlideMenu();
        initViewPayger();
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


    public void initViewPayger(){
        ReadFragment readFragment = new ReadFragment();
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(readFragment);
        viewPager.setAdapter(adapter);
    }
}
