package com.liwy.easymusic.controllers.music;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;


import com.liwy.easymusic.R;
import com.liwy.easymusic.adapter.MusicAdapter;
import com.liwy.easymusic.base.BaseActivity;
import com.liwy.easymusic.base.easyrecycler.EasyRecyclerView;
import com.liwy.easymusic.base.easyrecycler.SwipMenu;
import com.liwy.easymusic.base.easyrecycler.SwipMenuCreator;
import com.liwy.easymusic.common.http.HttpUtils;
import com.liwy.easymusic.entity.Musics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 演示下拉刷新和上拉加载的功能,数据为空时的页面展示
 */
public class MusicActivity extends BaseActivity<MusicPresenter> implements MusicView {
    @BindView(R.id.rv_list)
    EasyRecyclerView recyclerview;

    @BindView(R.id.id_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.empty_view)
    public View emptyView;

    private List<String> listTag;
    private List<Musics> musicList;

    private Handler handler = new Handler();

    @Override
    public void initView() {
        initToolbarWithBack(TOOLBAR_MODE_CENTER, "听雨楼", 0, new OnLeftClickListener() {
            @Override
            public void onLeftClick() {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        initSlideMenu();
        // 音乐标签，用于查询音乐列表
        String[] strTags= HttpUtils.getApiTag(0);
        listTag= Arrays.asList(strTags);
        initRecyclerView();
        // 请求数据
        mPresenter.getMusicByTag(HttpUtils.getRandomTAG(listTag));
    }
    // 初始化recyclerview并设置上拉和下拉刷新事件
    private void initRecyclerView(){
        recyclerview.setLayoutManager( new LinearLayoutManager(this));
        recyclerview.setFooterResource(R.layout.item_footer);
        recyclerview.setLoadMoreEnable(true);
        recyclerview.setEmptyView(emptyView);
        //  设置上拉和下拉刷新事件
        recyclerview.setOnLoadMoreListener(new EasyRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMoreListener() {
                mPresenter.loadMoreMusicByTag(HttpUtils.getRandomTAG(listTag));
            }
        });
        recyclerview.setSwipeMenuEnable(true);//开启滑动菜单功能
        // 设置点击事件，包括item点击和菜单点击
        recyclerview.setOnItemClickListener(new EasyRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showToast("点击了item：" + position);
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
                mPresenter.getMusicByTag(HttpUtils.getRandomTAG(listTag));
            }
        });
    }

    @Override
    public void setAdapter(MusicAdapter adapter) {
        recyclerview.setAdapter(adapter);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MusicPresenter();
        mPresenter.init(this,this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_music;
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
