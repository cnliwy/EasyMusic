package com.liwy.easymusic.common.http.subscribers;

/**
 * Created by liwy on 2017/3/14.
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}
