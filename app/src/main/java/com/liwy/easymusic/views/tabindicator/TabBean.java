package com.liwy.easymusic.views.tabindicator;

/**
 * Created by liwy on 16/7/19.
 */
public class TabBean {
    private String name;
    private int resIconNormal;
    private int resIconSelected;

    public TabBean(String name) {
        this.name = name;
    }

    public TabBean(String name, int resIconNormal) {
        this.name = name;
        this.resIconNormal = resIconNormal;
    }

    public TabBean(String name, int resIconNormal, int resIconSelected) {
        this.name = name;
        this.resIconNormal = resIconNormal;
        this.resIconSelected = resIconSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResIconNormal() {
        return resIconNormal;
    }

    public void setResIconNormal(int resIconNormal) {
        this.resIconNormal = resIconNormal;
    }

    public int getResIconSelected() {
        return resIconSelected;
    }

    public void setResIconSelected(int resIconSelected) {
        this.resIconSelected = resIconSelected;
    }
}
