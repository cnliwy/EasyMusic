package com.liwy.easymusic.model.happy;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by liwy on 2017/5/4.
 */

public class JokeResult{
    private int allNum;
    private int allPages;
    private int currentPage;
    private int maxResult;

    @SerializedName("contentlist")
    private List<Joke> contentList;

    @Override
    public String toString() {
        return "JokeResult{" +
                "allNum=" + allNum +
                ", allPages=" + allPages +
                ", currentPage=" + currentPage +
                ", maxResult=" + maxResult +
                ", contentList=" + (contentList!=null ? contentList.size():0) +
                '}';
    }

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

    public List<Joke> getContentList() {
        return contentList;
    }

    public void setContentList(List<Joke> contentList) {
        this.contentList = contentList;
    }
}
