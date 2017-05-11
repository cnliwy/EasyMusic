package com.liwy.easymusic.adapter;

import android.content.Context;
import android.widget.ImageView;

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
        holder.setText(R.id.tv_name,item.getName());
        holder.setText(R.id.tv_desc,item.getDesc());
        holder.setText(R.id.tv_score,"影响力:" + item.getInfluence());
        ImageView imageView = holder.getView(R.id.iv_head_view);
        Glide.with(mContext).load(item.getImg()).placeholder(R.drawable.ic_slide_time).into(imageView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_weibo;
    }
}
