package com.liwy.easymusic.controllers.music.local;

import com.liwy.easymusic.base.BaseFragment;

import com.liwy.easymusic.R;


public class LocalMusicFragment extends BaseFragment<LocalMusicPresenter> implements LocalMusicView {

    @Override
    public void initView() {

    }

    // init presenter
    @Override
    protected void initPresenter() {
        mPresenter = new LocalMusicPresenter();
        mPresenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_local_music;

    }
}
