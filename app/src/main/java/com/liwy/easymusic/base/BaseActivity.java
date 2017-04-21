package com.liwy.easymusic.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.liwy.easymusic.MyApplication;
import com.liwy.easymusic.R;
import com.liwy.easymusic.adapter.SlideItemAdapter;
import com.liwy.easymusic.base.presenter.BasePresenter;
import com.liwy.easymusic.base.presenter.IPresenter;
import com.liwy.easymusic.base.view.IView;
import com.liwy.easymusic.common.ToastUtils;
import com.liwy.easymusic.controllers.contacts.ContactsActivity;
import com.liwy.easymusic.controllers.music.MusicActivity;
import com.liwy.easymusic.entity.SlideItem;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 抽象基类
 * Created by liwy on 2016/11/16.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IView {
    private HashSet<IPresenter>  mPresenters = new HashSet<IPresenter>();
    public T mPresenter;
    public Context mContext;
    private OnLeftClickListener onLeftClickListener;                                // 左侧返回按钮点击事件
    private OnRightClickListener onRightClickListener;                              // 右侧标题点击事件
    private static final int defalutBackIconResID = R.drawable.ic_menu;          // 左侧返回按钮的默认图片

    // toolbar标题显示位置
    protected static final int TOOLBAR_MODE_NONE = 0;                             // 不显示标题
    protected static final int TOOLBAR_MODE_LEFT = 1;                             // 标题居左侧
    protected static final int TOOLBAR_MODE_CENTER = 2;                           // 标题居右

    @Nullable
    public @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    public @BindView(R.id.tv_toolbar_title) TextView toolbarTitle;
    @Nullable
    public @BindView(R.id.tv_toolbar_right) TextView toolbarRight;

    // 左侧滑动菜单控件
    @Nullable
    @BindView(R.id.id_lv_left_menu)
    public ListView mLvLeftMenu;
    @Nullable
    @BindView(R.id.fd)
    public DrawerLayout drawerLayout;

    public List<SlideItem> mItems = new ArrayList<SlideItem>(
            Arrays.asList(
                    new SlideItem(R.mipmap.ic_launcher, "听雨楼"),
                    new SlideItem(R.mipmap.ic_launcher, "通讯录"),
                    new SlideItem(R.mipmap.ic_launcher, "定时关闭音乐"),
                    new SlideItem(R.mipmap.ic_launcher, "下载歌曲品质"),
                    new SlideItem(R.mipmap.ic_launcher, "退出")

            ));


    /**
     * 需要子类来实现，获取子类的IPresenter，一个activity有可能有多个IPresenter
     */
    protected  BasePresenter getPresenter(){
        return mPresenter;
    }

    /**
     * 初始化presenters
     */
    protected abstract void  initPresenter();

    /**
     * 获取layout的id，由子类实现
     * @return
     */
    protected abstract int getLayoutResId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        // 设置应用为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //使用ButterKnife注入
        ButterKnife.bind(this);
        mContext = this;
        MyApplication.getInstance().addActivity(this);
        // 初始化toolbar
        if (toolbar != null){
            //如果页面导入了toolbar则显示
            setSupportActionBar(toolbar);
            if (toolbarTitle != null) {
                //getTitle()的值是activity的android:lable属性值
                toolbarTitle.setText(getTitle());
                toolbarRight.setText("");
                //设置默认的标题不显示
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
        // 初始化presenter
        initPresenter();
        mPresenter.onCreate();
    }

    // 左侧返回按钮点击事件
    public interface OnLeftClickListener{
        public void onLeftClick();
    }
    // 右侧子标题点击事件
    public interface OnRightClickListener{
        public void onRightClick();
    }

    // 初始化侧滑菜单
    public void initSlideMenu() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mLvLeftMenu.addHeaderView(inflater.inflate(R.layout.nav_header_main, mLvLeftMenu, false));
        mLvLeftMenu.setAdapter(new SlideItemAdapter(this,mItems));
        mLvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawers();
                switch (position) {
                    case 1:
                        turnToActivity(MusicActivity.class);
                        break;
                    case 2:
                        turnToActivity(ContactsActivity.class);
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
            }
        });
    }

    /**
     * 初始化toolbar标题
     * @param titleMode     标题的位置模式
     * @param title          标题的内容
     */
    public void initToolbarTitle (int titleMode,String title) {
        switch (titleMode) {
            case TOOLBAR_MODE_NONE:
                toolbar.setTitle("");
                toolbarTitle.setText("");
                break;
            case TOOLBAR_MODE_LEFT:
                toolbar.setTitle(title);
                toolbarTitle.setText("");
                break;
            case TOOLBAR_MODE_CENTER:
                toolbar.setTitle("");
                toolbarTitle.setText(title);
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    /**
     * 初始化toolbar标题和左侧返回键
     * @param titleMode     标题的位置模式
     * @param title          标题的内容
     * @param backResId     左侧返回按钮的图标资源id
     */
    public void initToolbarWithBack(int titleMode,String title,int backResId,OnLeftClickListener leftClickListener){
        initToolbarTitle(titleMode,title);
        if (backResId != 0){
            toolbar.setNavigationIcon(backResId);
        }else{
            toolbar.setNavigationIcon(defalutBackIconResID);
        }
        this.onLeftClickListener = leftClickListener;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLeftClickListener != null){
                    onLeftClickListener.onLeftClick();
                }else{
                    onBackPressed();
                }
            }
        });
    }

    /**
     *  设置右侧文字或图标及点击事件
     * @param text  右侧标题内容
     * @param rightClickListener  右侧点击事件
     */
    public void setToolbarRight(String text,OnRightClickListener rightClickListener){
        if (text != null && !"".equals(text)){
            toolbarRight.setCompoundDrawables(null,null,null,null);
            toolbarRight.setText(text);
            this.onRightClickListener = rightClickListener;
            toolbarRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRightClickListener != null){
                        onRightClickListener.onRightClick();
                    }
                }
            });
        }else{
            toolbarRight.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置右侧文字或图标及点击事件
     * @param resId 右侧图标资源id
     * @param rightClickListener 右侧点击事件
     */
    public void setToolbarRight(int resId,OnRightClickListener rightClickListener){
        if (resId != 0){
            toolbarRight.setText("");
            Drawable drawable;
            if (Build.VERSION.SDK_INT >= 21){
                drawable = getResources().getDrawable(resId,null);
            }else{
                drawable = getResources().getDrawable(resId);
            }
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());

            toolbarRight.setCompoundDrawables(drawable,null,null,null);
            this.onRightClickListener = rightClickListener;
            toolbarRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRightClickListener != null){
                        onRightClickListener.onRightClick();
                    }
                }
            });
        }else{
            toolbarRight.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
        MobclickAgent.onResume(this);//友盟统计
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
        MobclickAgent.onPause(this);// 友盟统计
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
        mPresenter.onDestroy();
    }
    // toast提示
    public void showToast(String msg) {
        ToastUtils.showToast(getApplicationContext(),msg);
    }
    // 跳转至下个页面
    public void turnToActivity(Class className){
        Intent intent = new Intent(this,className);
        startActivity(intent);
    }
    // 跳转至下个页面并传参
    public void turnToActivity(Class className,Bundle bundle){
        Intent intent = new Intent(this,className);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    // 跳转至下个页面并销毁
    public void turnToActivityWithFinish(Class className){
        Intent intent = new Intent(this,className);
        startActivity(intent);
        this.finish();
    }
    // 跳转至下个页面并传参，并销毁
    public void turnToActivityWithFinish(Class className,Bundle bundle){
        Intent intent = new Intent(this,className);
        intent.putExtras(bundle);
        startActivity(intent);
        this.finish();
    }

    public void setOnLeftClickListener(OnLeftClickListener onLeftClickListener) {
        this.onLeftClickListener = onLeftClickListener;
    }

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        this.onRightClickListener = onRightClickListener;
    }

    /**
     * 获取资源中的字符串内容
     * @param resId string字符串的res id
     * @return
     */
    public String getResString(int resId){
        return getResources().getString(resId);
    }
}
