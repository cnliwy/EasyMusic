package com.liwy.easymusic.base.easyrecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 使用注意：1 子类需首先构造方法，无需重写，super()即可；
 *           2 实现抽象方法
 * Created by liwy on 2017/3/27.
 */

public abstract class EasyRecyclerAdapter<T> extends RecyclerView.Adapter<EasyHolder>{
    public Context mContext;
    public List<T> list;
    protected LayoutInflater mInflater;

    public EasyRecyclerAdapter() {
    }

    public EasyRecyclerAdapter(Context context) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.list = new ArrayList<T>();

    }

    public EasyRecyclerAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.list = list;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        // TODO Auto-generated method stub
        this.list = list;
        notifyDataSetChanged();
    }

    public void appendList(List<T> list2) {
        // TODO Auto-generated method stub
        this.list.addAll((Collection<? extends T>) list2);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return  list != null ? list.size() : 0;
    }



    @Override
    public void onBindViewHolder(EasyHolder holder, int position) {
        convert(holder, (T) list.get(position));
    }

    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, final int position) {
        return EasyHolder.get(mContext, parent, getLayoutId(), position);
    }
    //这里定义抽象方法，我们在匿名内部类实现的时候实现此方法来调用控件
    public abstract void convert(EasyHolder holder, T item);
    public abstract int getLayoutId();
}
