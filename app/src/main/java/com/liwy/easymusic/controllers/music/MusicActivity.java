package com.liwy.easymusic.controllers.music;

import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liwy.easymusic.R;
import com.liwy.easymusic.adapter.FragmentAdapter;
import com.liwy.easymusic.base.BaseActivity;
import com.liwy.easymusic.common.ToastUtils;
import com.liwy.easymusic.common.utils.CoverLoader;
import com.liwy.easymusic.controllers.music.local.LocalMusicFragment;
import com.liwy.easymusic.controllers.music.online.OnlineMusicFragment;
import com.liwy.easymusic.model.Music;
import com.liwy.easymusic.service.playmusic.OnPlayerEventListener;

import butterknife.BindView;

import static com.liwy.easymusic.service.playmusic.AppCache.getPlayService;

/**
 * 演示下拉刷新和上拉加载的功能,数据为空时的页面展示
 */
public class MusicActivity extends BaseActivity<MusicPresenter> implements MusicView,OnPlayerEventListener,View.OnClickListener {
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.fl_play_bar)
    FrameLayout flPlayBar;

    @BindView(R.id.iv_play_bar_cover)
    ImageView ivPlayBarCover;

    @BindView(R.id.tv_play_bar_title)
    TextView tvPlayBarTitle;

    @BindView(R.id.tv_play_bar_artist)
    TextView tvPlayBarArtist;

    @BindView(R.id.iv_play_bar_play)
    ImageView ivPlayBarPlay;

    @BindView(R.id.iv_play_bar_next)
    ImageView ivPlayBarNext;

    @BindView(R.id.pb_play_bar)
    ProgressBar mProgressBar;

    OnlineMusicFragment onlineMusicFragment;
    LocalMusicFragment localMusicFragment;
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
        flPlayBar.setOnClickListener(this);
        ivPlayBarPlay.setOnClickListener(this);
        ivPlayBarNext.setOnClickListener(this);

        getPlayService().setOnPlayEventListener(this);
//        registerReceiver();
        onChange(getPlayService().getPlayingMusic());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPlayService().setOnPlayEventListener(null);
    }

    public void initViewPager(){
        onlineMusicFragment = new OnlineMusicFragment();
        localMusicFragment = new LocalMusicFragment();

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_play_bar:
//                showPlayingFragment();
                ToastUtils.show("播放页面正在开发");
                break;
            case R.id.iv_play_bar_play:
                play();
                break;
            case R.id.iv_play_bar_next:
                next();
                break;
        }
    }

    /**
     * 更新播放进度
     */
    @Override
    public void onPublish(int progress) {
        mProgressBar.setProgress(progress);
//        if (mPlayFragment != null && mPlayFragment.isInitialized()) {
//            mPlayFragment.onPublish(progress);
//        }
    }

    @Override
    public void onChange(Music music) {
        onPlay(music);
//        if (mPlayFragment != null && mPlayFragment.isInitialized()) {
//            mPlayFragment.onChange(music);
//        }
    }

    @Override
    public void onPlayerPause() {
        ivPlayBarPlay.setSelected(false);
//        if (mPlayFragment != null && mPlayFragment.isInitialized()) {
//            mPlayFragment.onPlayerPause();
//        }
    }

    @Override
    public void onPlayerResume() {
        ivPlayBarPlay.setSelected(true);
//        if (mPlayFragment != null && mPlayFragment.isInitialized()) {
//            mPlayFragment.onPlayerResume();
//        }
    }

    @Override
    public void onTimer(long remain) {
//        if (timerItem == null) {
//            timerItem = navigationView.getMenu().findItem(R.id.action_timer);
//        }
//        String title = getString(R.string.menu_timer);
//        timerItem.setTitle(remain == 0 ? title : SystemUtils.formatTime(title + "(mm:ss)", remain));
    }

    public void onPlay(Music music) {
        if (music == null) {
            return;
        }

        Bitmap cover = CoverLoader.getInstance().loadThumbnail(music);
        ivPlayBarCover.setImageBitmap(cover);
        tvPlayBarTitle.setText(music.getTitle());
        tvPlayBarArtist.setText(music.getArtist());
        if (getPlayService().isPlaying() || getPlayService().isPreparing()) {
            ivPlayBarPlay.setSelected(true);
        } else {
            ivPlayBarPlay.setSelected(false);
        }
        mProgressBar.setMax((int) music.getDuration());
        mProgressBar.setProgress(0);

//        if (mLocalMusicFragment != null && mLocalMusicFragment.isInitialized()) {
//            mLocalMusicFragment.onItemPlay();
//        }
    }

    private void play() {
        getPlayService().playPause();
    }

    private void next() {
        getPlayService().next();
    }
}
