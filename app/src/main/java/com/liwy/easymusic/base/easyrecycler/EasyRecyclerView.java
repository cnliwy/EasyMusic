package com.liwy.easymusic.base.easyrecycler;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;

/**
 * 将footer布局放在最后一个条目,当加载更多的时候显示,加载完成的时候隐藏
 */

public class EasyRecyclerView extends RecyclerView {

    private boolean swipeMenuEnable = false;//是否开启滑动菜单,默认关闭

    private AutoLoadAdapter autoLoadAdapter;

    public EasyRecyclerView(Context context) {
        this(context, null);
    }

    public EasyRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        mContext = context;

        mScroller = new Scroller(context, new LinearInterpolator());
        mVelocityTracker = VelocityTracker.obtain();
    }

    private boolean isLoadingMore = false;//是否正在加载更多
    private OnLoadMoreListener loadMoreListener;//加载数据监听
    private boolean loadMoreEnable = false;//是否允许加载更多
    private int footerResource = -1;//脚布局
    private boolean footer_visible = false;//脚部是否可以见
    private void init() {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (getAdapter() != null && getLayoutManager() != null) {
                    int lastVisiblePosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                    int itemCount = getAdapter().getItemCount();
                    /**
                     * 控制下拉刷新回调
                     * itemCount != 0 排除没有数据情况
                     * lastVisiblePosition + 4 >= itemCount - 1 最后可见+4 >= 总条数 加载更多
                     * distanceY < 0 为上拉的时候才刷新
                     */
                    if (distanceY < 0 && itemCount != 0 && lastVisiblePosition + 4 >= itemCount - 1 && !isLoadingMore && loadMoreEnable) {
                        Log.i("test","加载更多");
                        //正在加载更多
                        loading();
                        if (footerResource != -1){//有脚布局
                            //显示脚布局
                            footer_visible = true;
                            getAdapter().notifyItemChanged(itemCount - 1);
                        }
                        if (loadMoreListener != null) {
                            loadMoreListener.loadMoreListener();
                        }
                    }
                }
            }
        });
    }

    /**
     * 判断滑动方向
     */
    private float distanceY = 0;
    float startY = 0;
    private float distanceX = 0;
    float startX = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float y = ev.getRawY();
        float x = ev.getRawX();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = y;
                startX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                distanceX = x - startX;
                startX = x;
                distanceY = y - startY;
                startY = y;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        //判断是否为空
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        SlideInBottomAnimationAdapter slideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(adapter);
        slideInBottomAnimationAdapter.setDuration(600);
        autoLoadAdapter = new AutoLoadAdapter(slideInBottomAnimationAdapter);//添加动画
        super.setAdapter(autoLoadAdapter);
        autoLoadAdapter.registerAdapterDataObserver(observer);
        checkIsEmpty();
    }
    private SwipMenuCreator swipMenuCreator;

    public SwipMenuCreator getSwipMenuCreator() {
        return swipMenuCreator;
    }

    public void setSwipMenuCreator(SwipMenuCreator swipMenuCreator) {
        this.swipMenuCreator = swipMenuCreator;
    }



    /**
     * 设置是否允许加载更多
     *
     * @param isEnable
     */
    public void setLoadMoreEnable(boolean isEnable) {
        this.loadMoreEnable = isEnable;
    }

    /**
     * 设置脚布局
     */
    public void setFooterResource(int footerResource) {
        this.footerResource = footerResource;
    }


    /**
     * 加载完成
     */
    private void loadMoreComplete() {
        this.isLoadingMore = false;
    }

    /**
     * 正在刷新
     */
    private void loading(){
        this.isLoadingMore = true;//设置正在刷新
    }

    /**
     * 加载更多数据回调
     *
     * @param listener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.loadMoreListener = listener;
    }

    public interface OnLoadMoreListener {
        void loadMoreListener();//上拉刷新
    }

    public boolean isSwipeMenuEnable() {
        return swipeMenuEnable;
    }

    public void setSwipeMenuEnable(boolean swipeMenuEnable) {
        this.swipeMenuEnable = swipeMenuEnable;
    }

    /**
     * 刷新数据
     */
    public void notifyData() {
        if (getAdapter() != null) {
            loadMoreComplete();
            if(footerResource != -1 && loadMoreEnable){
                //隐藏脚布局
                footer_visible = false;
            }
            getAdapter().notifyDataSetChanged();
        }
    }

    /**
     * 重写recycleview的adapter
     */
    public class AutoLoadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Adapter dataAdapter;//数据adapter
        private final int TYPE_FOOTER = Integer.MAX_VALUE;//底部布局

        public AutoLoadAdapter(RecyclerView.Adapter adapter) {
            this.dataAdapter = adapter;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == getItemCount() - 1 && loadMoreEnable && footerResource != -1 && footer_visible) {
                return TYPE_FOOTER;
            }
            if (dataAdapter.getItemViewType(position) == TYPE_FOOTER) {
                throw new RuntimeException("adapter中itemType不能为:" + Integer.MAX_VALUE);
            }
            return dataAdapter.getItemViewType(position);

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder = null;
            if (viewType == TYPE_FOOTER) {//脚部
                holder = new FooterViewHolder(LayoutInflater.from(getContext()).inflate(footerResource, parent, false));
            } else {//数据
                holder = dataAdapter.onCreateViewHolder(parent, viewType);
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            int itemViewType = getItemViewType(position);
            if (itemViewType != TYPE_FOOTER) {
                dataAdapter.onBindViewHolder(holder, position);
            }
        }

        @Override
        public int getItemCount() {
            if (dataAdapter.getItemCount() != 0) {
                int count = dataAdapter.getItemCount();
                if (loadMoreEnable && footerResource != -1 && footer_visible) {
                    count++;
                }
                return count;
            }
            return 0;
        }

        public class FooterViewHolder extends RecyclerView.ViewHolder {

            public FooterViewHolder(View itemView) {
                super(itemView);
            }
        }

    }


    private View emptyView;//设置空数据是显示的view
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        checkIsEmpty();
    }

    /**
     * 判断数据是否为空
     */
    private void checkIsEmpty(){
        if (emptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible =
                    getAdapter().getItemCount() == 0;
            emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            if (emptyViewVisible){
                distanceY = 0;
            }
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }
    /**
     * 监听数据，以显示空数据view和有数据view
     */
    private final AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIsEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIsEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIsEmpty();
        }
    };
    /**
     * 滑动删除
     */
    private Context mContext;

    //上一次的触摸点
    private int mLastX, mLastY;
    //当前触摸的item的位置
    private int mPosition;

    //item对应的布局
    private View parentLayout;
    //删除按钮
    private LinearLayout menuLayout;

    //最大滑动距离(即删除按钮的宽度)
    private int mMaxLength;
    //是否在垂直滑动列表
    private boolean isDragging;
    //item是在否跟随手指移动
    private boolean isItemMoving;

    //item是否开始自动滑动
    private boolean isStartScroll;
    //删除按钮状态   0：关闭 1：将要关闭 2：将要打开 3：打开
    private int mDeleteBtnState;

    //检测手指在滑动过程中的速度
    private VelocityTracker mVelocityTracker;
    private Scroller mScroller;
    private OnItemClickListener mListener;



    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        View view = findChildViewUnder(x, y);
        if (view == null)return super.onTouchEvent(e);//如果未找到item则执行默认的滑动方法
        ViewHolder viewHolder = getChildViewHolder(view);

        // 如果未开启滑动菜单，则直接事件分发
        if (!swipeMenuEnable){
            switch (e.getAction()){
                case MotionEvent.ACTION_UP:
                    if (!isItemMoving && !isDragging && mListener != null) {
                        parentLayout = viewHolder.itemView;
                        mPosition = viewHolder.getAdapterPosition();
                        mListener.onItemClick(parentLayout, mPosition);
                    }
                    break;
            }
            return super.onTouchEvent(e);
        }
        mVelocityTracker.addMovement(e);

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mDeleteBtnState == 0) {
                    if (view == null) {
                        return false;
                    }
                    parentLayout = viewHolder.itemView;
                    mPosition = viewHolder.getAdapterPosition();

                    LinearLayout linearLayout = (LinearLayout)parentLayout;
                    View cacheView = linearLayout.getChildAt(1);
                    if (!(cacheView instanceof LinearLayout)){
                        return false;
                    }
                    menuLayout = (LinearLayout) linearLayout.getChildAt(1);

                    mMaxLength = menuLayout.getWidth();
                    int childs = menuLayout.getChildCount();
                    for (int i = 0; i < childs; i++){
                        TextView btn = (TextView) menuLayout.getChildAt(i);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mListener.onMenuClick(mPosition,(int)v.getTag());
                                parentLayout.scrollTo(0, 0);
                                mDeleteBtnState = 0;
                            }
                        });
                    }

                } else if (mDeleteBtnState == 3){
                    mScroller.startScroll(parentLayout.getScrollX(), 0, -mMaxLength, 0, 200);
                    invalidate();
                    mDeleteBtnState = 0;
                    return false;
                }else{
                    return false;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                int dx = mLastX - x;
                int dy = mLastY - y;
                if (view == null) {
                    return false;
                }
                // 防止滑动过程中parentView对象被替换
                if (parentLayout == null){
                    parentLayout = viewHolder.itemView;
                }
                int scrollX = parentLayout.getScrollX();
                if (Math.abs(dx) > Math.abs(dy)) {//左边界检测
                    isItemMoving = true;
                    if (scrollX + dx <= 0) {
                        parentLayout.scrollTo(0, 0);
                        return false;//水平滑动期间禁止垂直滑动
                    } else if (scrollX + dx >= mMaxLength) {//右边界检测
                        parentLayout.scrollTo(mMaxLength, 0);
                        return false;
                    }
                    parentLayout.scrollBy(dx, 0);//item跟随手指滑动
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isItemMoving && !isDragging && mListener != null) {
                    mListener.onItemClick(parentLayout, mPosition);
                }
                isItemMoving = false;

                mVelocityTracker.computeCurrentVelocity(1000);//计算手指滑动的速度
                float xVelocity = mVelocityTracker.getXVelocity();//水平方向速度（向左为负）
                float yVelocity = mVelocityTracker.getYVelocity();//垂直方向速度

                int deltaX = 0;
                int upScrollX = parentLayout.getScrollX();

                if (Math.abs(xVelocity) > 100 && Math.abs(xVelocity) > Math.abs(yVelocity)) {
                    if (xVelocity <= -100) {//左滑速度大于100，则删除按钮显示
                        deltaX = mMaxLength - upScrollX;
                        mDeleteBtnState = 2;
                    } else if (xVelocity > 100) {//右滑速度大于100，则删除按钮隐藏
                        deltaX = -upScrollX;
                        mDeleteBtnState = 1;
                    }
                } else {
                    if (upScrollX >= mMaxLength / 2) {//item的左滑动距离大于删除按钮宽度的一半，则则显示删除按钮
                        deltaX = mMaxLength - upScrollX;
                        mDeleteBtnState = 2;
                    } else if (upScrollX < mMaxLength / 2) {//否则隐藏
                        deltaX = -upScrollX;
                        mDeleteBtnState = 1;
                    }
                }

                //item自动滑动到指定位置
                mScroller.startScroll(upScrollX, 0, deltaX, 0, 200);
                isStartScroll = true;
                invalidate();

                mVelocityTracker.clear();
                break;
        }

        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(e);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            parentLayout.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        } else if (isStartScroll) {
            isStartScroll = false;
            if (mDeleteBtnState == 1) {
                mDeleteBtnState = 0;
            }

            if (mDeleteBtnState == 2) {
                mDeleteBtnState = 3;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        isDragging = state == SCROLL_STATE_DRAGGING;
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnItemClickListener {
        /**
         * item点击回调
         *
         * @param view
         * @param position
         */
        void onItemClick(View view, int position);

        /**
         * 删除按钮回调
         *
         * @param itemPosition item的position位置
         * @param menuPosition menu的positon位置
         */
        void onMenuClick(int itemPosition, int menuPosition);
    }

}
