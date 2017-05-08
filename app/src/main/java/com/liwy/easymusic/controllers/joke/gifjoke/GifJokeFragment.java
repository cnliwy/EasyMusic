package com.liwy.easymusic.controllers.joke.gifjoke;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.liwy.easymusic.R;
import com.liwy.easymusic.adapter.ImgJokeAdapter;
import com.liwy.easymusic.base.BaseFragment;
import com.liwy.easymusic.views.easyrecycler.EasyRecyclerView;
import com.liwy.easymusic.model.happy.Joke;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;


public class GifJokeFragment extends BaseFragment<GifJokePresenter> implements GifJokeView {

    @BindView(R.id.rv_list)
    public EasyRecyclerView listView;

    @BindView(R.id.id_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.empty_view)
    public View emptyView;

    @BindView(R.id.tv_time)
    TextView timeTv;

    @BindView(R.id.tv_title)
    TextView titleTv;

    @BindView(R.id.img_joke)
    PhotoView imgIv;

    @BindView(R.id.btn_next)
    Button nextBtn;

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
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.next();
            }
        });
    }

    @Override
    protected void initPresenter() {
        mPresenter = new GifJokePresenter();
        mPresenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_gif_joke;

    }

    @Override
    public void setAdapter(ImgJokeAdapter adapter) {
        listView.setAdapter(adapter);
        if (swipeRefreshLayout.isRefreshing()){
            finishRefresh();
        }
    }

    @Override
    public void updateNext(Joke joke) {
        String time = joke.getTime();
        time = time.substring(0,time.length()-4);
        timeTv.setText(time);
        titleTv.setText(joke.getTitle());
        Glide.with(this).load(joke.getImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade().centerCrop()
                .placeholder(R.drawable.ic_slide_time)
                .into(imgIv);
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
