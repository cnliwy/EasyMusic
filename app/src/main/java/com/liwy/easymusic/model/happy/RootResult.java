package com.liwy.easymusic.model.happy;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liwy on 2017/5/4.
 */

public class RootResult<T> {
    @SerializedName("showapi_res_code")
    private int code;

    @SerializedName("showapi_res_error")
    private String errorMsg;

    @SerializedName("showapi_res_body")
    private DataResult<T> result;

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

    public DataResult<T> getResult() {
        return result;
    }

    public void setResult(DataResult<T> result) {
        this.result = result;
    }
}
