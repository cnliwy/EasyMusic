package com.liwy.easymusic.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.liwy.easymusic.R;
import com.liwy.easymusic.model.happy.Weibo;
import com.liwy.easymusic.views.easyrecycler.EasyHolder;
import com.liwy.easymusic.views.easyrecycler.EasyRecyclerAdapter;

import java.util.List;

import uk.co.senab.photoview.PhotoView;


/**
 * Created by liwy on 2017/4/20.
 */

public class WeiboAdapter extends EasyRecyclerAdapter<Weibo> {

    public WeiboAdapter(Context context) {
        super(context);
    }

    public WeiboAdapter(Context context, List<Weibo> list) {
        super(context, list);
    }

    @Override
    public void convert(EasyHolder holder, Weibo item) {
        String time = item.getDate();
        holder.setText(R.id.tv_time,time);
        holder.setText(R.id.tv_content,item.getNewinfo());
        PhotoView imageView = holder.getView(R.id.view_image);
        Glide.with(mContext).load(item.getImg()).placeholder(R.drawable.ic_slide_time).into(imageView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_weibo;
    }
}
