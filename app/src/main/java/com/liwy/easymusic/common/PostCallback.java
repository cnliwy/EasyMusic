package com.liwy.easymusic.common;

/**
 * 事件处理
 * Created by liwy on 2017/5/9.
 */

public interface PostCallback<T> {
    public void call(T t);
}
