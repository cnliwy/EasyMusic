package com.liwy.easymusic.service.playmusic;

/**
 * http请求回调
 */
public interface IExecutor<T> {
    void execute();

    void onPrepare();

    void onExecuteSuccess(T t);

    void onExecuteFail(Exception e);
}
