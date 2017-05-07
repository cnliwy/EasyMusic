package com.liwy.easymusic.controllers.joke;

import android.support.v4.view.ViewPager;
import android.view.Gravity;

import com.liwy.easymusic.R;
import com.liwy.easymusic.adapter.FragmentAdapter;
import com.liwy.easymusic.base.BaseActivity;
import com.liwy.easymusic.controllers.joke.imgjoke.ImgJokeFragment;
import com.liwy.easymusic.controllers.joke.textjoke.TextJokeFragment;
import com.liwy.easymusic.views.tabindicator.LiwyIndicator;
import com.liwy.easymusic.views.tabindicator.TabBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;



public class JokeActivity extends BaseActivity<JokePresenter> implements JokeView {
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.indictor)
    LiwyIndicator indicator;

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
        initIndictor();
    }

    /**
     * 初始化导航栏
     */
    public void initIndictor(){
        List<TabBean> list = new ArrayList<TabBean>() ;
        list.add(new TabBean("笑话",R.drawable.ic_slide_happy));
        list.add(new TabBean("趣图",R.drawable.ic_slide_music));
        indicator.setTabAndViewPager(list,viewPager);
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
