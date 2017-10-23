package com.example.usercenter.common.base.model;

import java.util.List;

/**
 * Created by zxn on 2017/10/23.
 */
public class Page<T> {
    /**
     * 分页列表
     * */
    private List<T> rows;
    /**
     * 总记录数
     * */
    private long total;

    public Page(){}

    public Page(List<T> rows, long total) {
        super();
        this.rows = rows;
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }
    public void setRows(List<T> rows) {
        this.rows = rows;
    }
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Page [rows=" + rows + ", total=" + total + "]";
    }
}
