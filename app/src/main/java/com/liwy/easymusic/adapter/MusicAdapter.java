package com.liwy.easymusic.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.liwy.easymusic.R;
import com.liwy.easymusic.base.easyrecycler.EasyHolder;
import com.liwy.easymusic.base.easyrecycler.EasyRecyclerAdapter;
import com.liwy.easymusic.common.utils.ImgUtils;
import com.liwy.easymusic.entity.Musics;

import java.util.List;


/**
 * Created by liwy on 2017/3/28.
 */

public class MusicAdapter extends EasyRecyclerAdapter<Musics> {

    public MusicAdapter(Context context) {
        super(context);
    }

    public MusicAdapter(Context context, List<Musics> list) {
        super(context, list);
    }

    @Override
    public void convert(EasyHolder holder, final Musics item) {
        TextView nameTv = (TextView) holder.getView(R.id.tv_music_name);
        TextView gradeTv = (TextView) holder.getView(R.id.tv_music_grade);
        TextView artTv = (TextView) holder.getView(R.id.tv_music_art);
        ImageView imageIv = (ImageView)holder.getView(R.id.iv_music);

        ImgUtils.getInstance().display(mContext,item.getImage(),imageIv);
        if(!TextUtils.isEmpty(item.getTitle())) {
            nameTv.setText(item.getTitle());
        }
        if(item.getAuthor()!=null && item.getAuthor().size() > 0) {
            artTv.setText(item.getAuthor().get(0).getName());
        }
        if(!TextUtils.isEmpty(item.getRating().getAverage())) {
            gradeTv.setText(item.getRating().getAverage());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_music;
    }

}
