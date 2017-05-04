package com.liwy.easymusic.common.http.subscribers;

/**
 * Created by liwy on 2017/3/14.
 */
public interface HttpCallback<T> {
    void onNext(T t);
    void onFail(Throwable e);
}
