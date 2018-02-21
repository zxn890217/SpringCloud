package com.example.domain.base;

import java.util.List;

/**
 * Created by zxn on 2018/1/11.
 */
public class Page<T> {
    private int total;
    private List<T> rows;

    public Page(){}

    public Page(List<T> rows, int total) {
        this.rows = rows;
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
