package com.liwy.easymusic.common.old;

/**
 * Created by hzwangchenyan on 2017/2/8.
 */
public abstract class HttpCallback<T> {
    public abstract void onSuccess(T t);

    public abstract void onFail(Exception e);

    public void onFinish() {
    }
}
