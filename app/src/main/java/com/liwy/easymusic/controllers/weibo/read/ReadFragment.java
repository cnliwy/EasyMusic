package com.liwy.easymusic.controllers.weibo.read;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.liwy.easymusic.adapter.TextJokeAdapter;
import com.liwy.easymusic.adapter.WeiboAdapter;
import com.liwy.easymusic.base.BaseFragment;

import com.liwy.easymusic.R;
import com.liwy.easymusic.controllers.joke.textjoke.TextJokePresenter;
import com.liwy.easymusic.views.easyrecycler.EasyRecyclerView;

import butterknife.BindView;


public class ReadFragment extends BaseFragment<ReadPresenter> implements ReadView {

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
        listView.setOnItemClickListener(new EasyRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPresenter.onItemClick(position);
            }

            @Override
            public void onMenuClick(int itemPosition, int menuPosition) {

            }
        });
    }

    @Override
    protected void initPresenter() {
        mPresenter = new ReadPresenter();
        mPresenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_read;

    }

    @Override
    public void setAdapter(WeiboAdapter adapter) {
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
