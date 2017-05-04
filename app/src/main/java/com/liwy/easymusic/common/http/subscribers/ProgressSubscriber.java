package com.liwy.easymusic.common.http.subscribers;

import android.content.Context;
import android.widget.Toast;

import com.liwy.easymusic.common.http.progress.ProgressCancelListener;
import com.liwy.easymusic.common.http.progress.ProgressDialogHandler;
import com.orhanobut.logger.Logger;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog(构造方法里可配置是否显示)
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by liwy on 2017/3/14.
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private HttpCallback<T> mHttpCallback;
    private ProgressDialogHandler mProgressDialogHandler;

    private Context context;
    private boolean isShowDialog;

    public ProgressSubscriber(HttpCallback<T> mHttpCallback, Context context, boolean isShowDialog) {
        this.mHttpCallback = mHttpCallback;
        this.context = context;
        this.isShowDialog = isShowDialog;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgressDialog(){
        if (mProgressDialogHandler != null && isShowDialog) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null && isShowDialog) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        Logger.d(e.getMessage());
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        }
//        else {
//            Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
        dismissProgressDialog();
        if (mHttpCallback != null) {
            mHttpCallback.onFail(e);
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mHttpCallback != null) {
            mHttpCallback.onNext(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}