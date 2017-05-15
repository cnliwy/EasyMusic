package com.liwy.easymusic.controllers.weibo.ranking;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.liwy.easymusic.adapter.WeiboAdapter;
import com.liwy.easymusic.base.BaseFragment;

import com.liwy.easymusic.R;
import com.liwy.easymusic.views.easyrecycler.EasyRecyclerView;
import com.liwy.easymusic.views.tabindicator.EasyIndicator;
import com.liwy.easymusic.views.tabindicator.OnTabClickListener;
import com.liwy.easymusic.views.tabindicator.TabBean;
import com.liwy.easymusic.views.tabindicator.TabConfig;
import com.liwy.easymusic.views.tabindicator.TabView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class RankingListFragment extends BaseFragment<RankingListPresenter> implements RankingListView {

    @BindView(R.id.rv_list)
    public EasyRecyclerView listView;

    @BindView(R.id.id_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.empty_view)
    public View emptyView;

    @BindView(R.id.indictor)
    EasyIndicator indicator;

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
        initIndictor();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new RankingListPresenter();
        mPresenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_rank_list;

    }

    /**
     * 初始化导航栏
     */
    public void initIndictor(){
        TabConfig config = new TabConfig.Builder()
                .setTextColorNor(R.color.text_gray_6)    // 设置默认的文字颜色
                .setTextColorSel(R.color.colorAccent)    // 设置选中后的文字颜色
                .setTextSize(18)                           // 设置文字的大小
                .setBgColorNor(R.color.white)             // 设置默认的背景色
                .setLineColor(R.color.colorAccent)       // 设置下划线的颜色
                .setShowLine(true)                        // 设置是否显示下划线
                .builder();
        indicator.setConfig(config);
        indicator.setOnTabClickListener(new OnTabClickListener() {
            @Override
            public void onClick(TabView v) {
                mPresenter.selectRanklistType(v.getIndex());
            }
        });
        List<TabBean> list = new ArrayList<TabBean>() ;
        list.add(new TabBean("天榜"));
        list.add(new TabBean("周榜"));
        list.add(new TabBean("月榜"));
        indicator.setTabs(list);
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
