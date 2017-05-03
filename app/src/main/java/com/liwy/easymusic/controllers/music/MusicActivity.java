package com.liwy.easymusic.controllers.music;

import android.support.v4.view.ViewPager;
import android.view.Gravity;

import com.liwy.easymusic.R;
import com.liwy.easymusic.adapter.FragmentAdapter;
import com.liwy.easymusic.base.BaseActivity;
import com.liwy.easymusic.controllers.music.local.LocalMusicFragment;
import com.liwy.easymusic.controllers.music.online.OnlineMusicFragment;

import butterknife.BindView;

/**
 * 演示下拉刷新和上拉加载的功能,数据为空时的页面展示
 */
public class MusicActivity extends BaseActivity<MusicPresenter> implements MusicView {
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    public void initView() {
        initToolbarWithBack(TOOLBAR_MODE_CENTER, "听雨楼", R.drawable.ic_menu, new OnLeftClickListener() {
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
