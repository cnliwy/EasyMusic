package com.liwy.easymusic.base.easyrecycler;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * adapter基类，只需实现EasyAdapter的abstract方法即可
 * Created by liwy on 2017/3/20.
 */

public abstract class EasyListAdapter<T> extends BaseAdapter {
    public Context mContext;
    public List<T> mDatas;
    public LayoutInflater mInflater;

    public EasyListAdapter(Context context, List<T> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (mDatas == null){
            return 0;
        }else{
            return mDatas.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(mContext,position,convertView,parent,getLayoutId());
        return initView(viewHolder,position).getmConvertView();
    }

    //定义为抽象方法，子类必须实现(初始化item)
    public abstract ViewHolder initView(ViewHolder viewHolder,int position);
    // 钩子方法，返回layout id
    public abstract int getLayoutId();



    public static class ViewHolder{
        private SparseArray<View> mViews;
        private int mPosition;
        private View mConvertView;
        private ViewGroup parentView;

        public ViewHolder(Context context, int mPosition,ViewGroup parentView,int layoutId) {
            this.mPosition = mPosition;
            mViews = new SparseArray<View>();
            this.parentView = parentView;
            this.mConvertView = View.inflate(context, layoutId, null);
            this.mConvertView.setTag(this);
        }



        public static ViewHolder getViewHolder(Context context, int position, View convertView,ViewGroup parentView,int layoutId){
            if (convertView == null){
                return new ViewHolder(context,position,parentView,layoutId);
            }else{
                ViewHolder viewHolder = (ViewHolder) convertView.getTag();
                viewHolder.mPosition = position;
                return viewHolder;
            }
        }

        public <T extends View>T getView(int layoutId){
            View view = mViews.get(layoutId);
            if (view == null){
                view = getmConvertView().findViewById(layoutId);
                mViews.put(layoutId,view);
            }
            return (T)view;
        }
        public View getmConvertView() {
            return mConvertView;
        }
    }
}
