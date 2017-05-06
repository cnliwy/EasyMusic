package com.liwy.easymusic.controllers.splash;

import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.liwy.easymusic.R;
import com.liwy.easymusic.base.BaseActivity;
import com.liwy.easymusic.common.ToastUtils;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

import butterknife.BindView;



public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashView{
    public @BindView(R.id.tv_device_id) TextView deviceTv;

    @Override
    public void initView() {
        deviceTv.setText(getString(R.string.author));
    }

    // 初始化presenter
    @Override
    protected void initPresenter() {
        mPresenter = new SplashPresenter();
        mPresenter.init(this,this,this);
    }

    @Override
    protected int getLayoutResId() {
        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 移除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_splash;
    }

    // 请求权限结果返回
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, permissionlistener);
    }

    private PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantPermissions) {
            if (requestCode == 100) {
                mPresenter.checkService();
            } else {

            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            if (requestCode == 100) {
//               finish();
                ToastUtils.show("获取权限失败！");
            } else {

            }
        }
    };
}
