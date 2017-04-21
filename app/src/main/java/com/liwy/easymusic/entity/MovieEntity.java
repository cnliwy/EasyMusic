package com.liwy.easymusic.entity;

/**
 * Created by liwy on 2017/3/14.
 */

public class MovieEntity {
    private int count;
    private int start;
    private int total;
    private String title;
    private Object subjects;

    @Override
    public String toString() {
        return "count=" + count + ",start=" + start + ",total=" + total + ",title=" + title + ",subjects=" + subjects.toString();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getSubjects() {
        return subjects;
    }

    public void setSubjects(Object subjects) {
        this.subjects = subjects;
    }
}
