package com.liwy.easymusic.controllers.joke;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;


import com.liwy.easymusic.R;
import com.liwy.easymusic.adapter.JokeAdapter;
import com.liwy.easymusic.base.BaseActivity;
import com.liwy.easymusic.base.easyrecycler.EasyRecyclerView;

import butterknife.BindView;



public class JokeActivity extends BaseActivity<JokePresenter> implements JokeView {

    @BindView(R.id.tv_contacts)
    public EasyRecyclerView listView;

    @BindView(R.id.id_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void initView() {
        initToolbarWithBack(TOOLBAR_MODE_CENTER, "开心一刻", 0, new OnLeftClickListener() {
            @Override
            public void onLeftClick() {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        initSlideMenu();
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setFooterResource(R.layout.item_footer);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.initData();
            }
        });

    }

    // init presenter
    @Override
    protected void initPresenter() {
        mPresenter = new JokePresenter();
        mPresenter.init(this,this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_contacts;
    }

    @Override
    public void setAdapter(JokeAdapter adapter) {
        listView.setAdapter(adapter);
        if (swipeRefreshLayout.isRefreshing()){
            finishRefresh();
        }
    }


    /**
     * 结束刷新
     * 刷新结束后，需调用swipeRefreshLayout.setRefreshing(false); 隐藏下拉刷新header
     */
    @Override
    public void finishRefresh() {
        listView.notifyData();
        swipeRefreshLayout.setRefreshing(false);
    }
}
