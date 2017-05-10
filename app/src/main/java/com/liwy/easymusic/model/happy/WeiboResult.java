package com.liwy.easymusic.model.happy;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by liwy on 2017/5/10.
 */

public class WeiboResult {
    @SerializedName("showapi_res_code")
    private int code;

    @SerializedName("showapi_res_error")
    private String errorMsg;

    @SerializedName("showapi_res_body")
    private DataResult result;

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

    public DataResult getResult() {
        return result;
    }

    public void setResult(DataResult result) {
        this.result = result;
    }

    public static class DataResult{
        @SerializedName("ret_code")
        int retCode;
        @SerializedName("pagebean")
        PageBean pageBean;

        public int getRetCode() {
            return retCode;
        }

        public void setRetCode(int retCode) {
            this.retCode = retCode;
        }

        public PageBean getPageBean() {
            return pageBean;
        }

        public void setPageBean(PageBean pageBean) {
            this.pageBean = pageBean;
        }
    }
    public static class PageBean{
        private int allNum;
        private int allPages;
        private int currentPage;
        private int maxResult;

        @SerializedName("contentlist")
        private List<Weibo> contentList;

        public int getAllNum() {
            return allNum;
        }

        public void setAllNum(int allNum) {
            this.allNum = allNum;
        }

        public int getAllPages() {
            return allPages;
        }

        public void setAllPages(int allPages) {
            this.allPages = allPages;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getMaxResult() {
            return maxResult;
        }

        public void setMaxResult(int maxResult) {
            this.maxResult = maxResult;
        }

        public List<Weibo> getContentList() {
            return contentList;
        }

        public void setContentList(List<Weibo> contentList) {
            this.contentList = contentList;
        }
    }
}
