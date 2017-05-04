package com.liwy.easymusic.model.happy;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liwy on 2017/5/4.
 */

public class BaseHappyResult<T> {
    @SerializedName("showapi_res_code")
    private int code;

    @SerializedName("showapi_res_error")
    private String errorMsg;

    @SerializedName("showapi_res_body")
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
