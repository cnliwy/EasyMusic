package com.liwy.easymusic.controllers.joke.textjoke;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.liwy.easymusic.R;
import com.liwy.easymusic.adapter.TextJokeAdapter;
import com.liwy.easymusic.base.BaseFragment;
import com.liwy.easymusic.views.easyrecycler.EasyRecyclerView;

import butterknife.BindView;


public class TextJokeFragment extends BaseFragment<TextJokePresenter> implements TextJokeView {
    @BindView(R.id.rv_list)
    public EasyRecyclerView listView;

    @BindView(R.id.id_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.empty_view)
    public View emptyView;

    @Override
    public void initView() {
        listView.setLayoutManager(new LinearLayoutManager(mContext));
        listView.setFooterResource(R.layout.item_footer);
        listView.setEmptyView(emptyView);
        listView.setLoadMoreEnable(true);
        listView.setOnLoadMoreListener(new EasyRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMoreListener() {
                mPresenter.loadMore();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.initData();
            }
        });
    }

    @Override
    protected void initPresenter() {
        mPresenter = new TextJokePresenter();
        mPresenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_text_joke;

    }

    @Override
    public void setAdapter(TextJokeAdapter adapter) {
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
