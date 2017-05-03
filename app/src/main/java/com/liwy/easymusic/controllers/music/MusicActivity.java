package com.liwy.easymusic.controllers.music;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import com.liwy.easymusic.R;
import com.liwy.easymusic.adapter.FragmentAdapter;
import com.liwy.easymusic.adapter.MusicAdapter;
import com.liwy.easymusic.adapter.SlideItemAdapter;
import com.liwy.easymusic.base.BaseActivity;
import com.liwy.easymusic.base.easyrecycler.EasyRecyclerView;
import com.liwy.easymusic.base.easyrecycler.SwipMenu;
import com.liwy.easymusic.base.easyrecycler.SwipMenuCreator;
import com.liwy.easymusic.common.http.HttpUtils;
import com.liwy.easymusic.controllers.music.local.LocalMusicFragment;
import com.liwy.easymusic.controllers.music.online.OnlineMusicFragment;
import com.liwy.easymusic.entity.Musics;
import com.liwy.easymusic.entity.SlideItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 演示下拉刷新和上拉加载的功能,数据为空时的页面展示
 */
public class MusicActivity extends BaseActivity<MusicPresenter> implements MusicView {
    @BindView(R.id.viewpager)
    ViewPager viewPager;


    @Override
    public void initView() {
        initToolbarWithBack(TOOLBAR_MODE_CENTER, "听雨楼", R.drawable.btn_menu, new OnLeftClickListener() {
            @Override
            public void onLeftClick() {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        initSlideMenu();
        initViewPager();
    }


    public void initViewPager(){
        OnlineMusicFragment onlineMusicFragment = new OnlineMusicFragment();
        LocalMusicFragment localMusicFragment = new LocalMusicFragment();

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.addFragment(onlineMusicFragment);
        fragmentAdapter.addFragment(localMusicFragment);

        viewPager.setAdapter(fragmentAdapter);
    }


    @Override
    protected void initPresenter() {
        mPresenter = new MusicPresenter();
        mPresenter.init(this,this,this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_music;
    }

}
