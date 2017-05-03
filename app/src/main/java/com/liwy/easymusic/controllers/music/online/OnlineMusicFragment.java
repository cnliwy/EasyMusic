package com.liwy.easymusic.controllers.music.online;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.liwy.easymusic.adapter.MusicAdapter;
import com.liwy.easymusic.base.BaseFragment;

import com.liwy.easymusic.R;
import com.liwy.easymusic.base.easyrecycler.EasyRecyclerView;
import com.liwy.easymusic.base.easyrecycler.SwipMenu;
import com.liwy.easymusic.base.easyrecycler.SwipMenuCreator;
import com.liwy.easymusic.common.http.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class OnlineMusicFragment extends BaseFragment<OnlineMusicPresenter> implements OnlineMusicView {
    @BindView(R.id.rv_list)
    EasyRecyclerView recyclerview;

    @BindView(R.id.id_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.empty_view)
    public View emptyView;


    @Override
    public void initView() {
        initRecyclerView();
    }

    // init presenter
    @Override
    protected void initPresenter() {
        mPresenter = new OnlineMusicPresenter();
        mPresenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_online_music;
    }

    // 初始化recyclerview并设置上拉和下拉刷新事件
    private void initRecyclerView(){
        recyclerview.setLayoutManager( new LinearLayoutManager(mContext));
        recyclerview.setFooterResource(R.layout.item_footer);
        recyclerview.setLoadMoreEnable(true);
        recyclerview.setEmptyView(emptyView);
        //  设置上拉和下拉刷新事件
        recyclerview.setOnLoadMoreListener(new EasyRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMoreListener() {
                mPresenter.loadMoreMusic();
            }
        });
        recyclerview.setSwipeMenuEnable(true);//开启滑动菜单功能
        // 设置点击事件，包括item点击和菜单点击
        recyclerview.setOnItemClickListener(new EasyRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPresenter.onItemClick(position);
            }

            @Override
            public void onMenuClick(int itemPosition, int menuPosition) {
                showToast("点击了item" + itemPosition + "的第"+ menuPosition + "个按钮" );

            }
        });
        recyclerview.setSwipMenuCreator(new SwipMenuCreator() {
            @Override
            public List<SwipMenu> addMenu(Context context) {
                List<SwipMenu> menus = new ArrayList<SwipMenu>();
                for (int i = 0; i < 3; i++){
                    if (i == 1){
                        SwipMenu swipMenu = new SwipMenu.Builder().setContext(context).setTextColor(Color.RED).setBackgroundColor(Color.YELLOW).setText("功能" + i).setWidth(200).setHeight(ViewGroup.LayoutParams.MATCH_PARENT).builder();
                        menus.add(swipMenu);
                    }else{
                        SwipMenu swipMenu = new SwipMenu.Builder().setContext(context).setTextColor(Color.RED).setBackgroundColor(Color.GRAY).setText("功能" + i).setWidth(200).setHeight(ViewGroup.LayoutParams.MATCH_PARENT).builder();
                        menus.add(swipMenu);
                    }

                }
                return menus;
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMusicList(0,true);
            }
        });
    }
    @Override
    public void setAdapter(MusicAdapter adapter) {
        recyclerview.setAdapter(adapter);
    }

    /**
     * 结束刷新
     * 刷新结束后，需调用swipeRefreshLayout.setRefreshing(false); 隐藏下拉刷新header
     */
    @Override
    public void finishRefresh() {
        recyclerview.notifyData();
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 结束加载
     */
    @Override
    public void finishLoad() {
        recyclerview.notifyData();
    }
}
