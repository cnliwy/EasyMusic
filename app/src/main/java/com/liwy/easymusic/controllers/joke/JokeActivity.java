package com.liwy.easymusic.controllers.joke;

import android.support.v4.view.ViewPager;
import android.view.Gravity;

import com.liwy.easymusic.R;
import com.liwy.easymusic.adapter.FragmentAdapter;
import com.liwy.easymusic.base.BaseActivity;
import com.liwy.easymusic.controllers.joke.imgjoke.ImgJokeFragment;
import com.liwy.easymusic.controllers.joke.textjoke.TextJokeFragment;

import butterknife.BindView;



public class JokeActivity extends BaseActivity<JokePresenter> implements JokeView {
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private TextJokeFragment textJokeFragment;
    private ImgJokeFragment imgJokeFragment;

    @Override
    public void initView() {
        initToolbarWithBack(TOOLBAR_MODE_CENTER, "开心一刻", 0, new OnLeftClickListener() {
            @Override
            public void onLeftClick() {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        initSlideMenu();
        initViewPager();
    }

    /**
     * 初始化viewpager
     */
    public void initViewPager(){
        textJokeFragment = new TextJokeFragment();
        imgJokeFragment = new ImgJokeFragment();
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.addFragment(textJokeFragment);
        fragmentAdapter.addFragment(imgJokeFragment);

        viewPager.setAdapter(fragmentAdapter);
    }

    // init presenter
    @Override
    protected void initPresenter() {
        mPresenter = new JokePresenter();
        mPresenter.init(this,this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_jokes;
    }


}
