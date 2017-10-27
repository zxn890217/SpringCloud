package com.example.usercenter.common.base.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxn on 2017/10/23.
 */
public class QueryModel {
    /** 要排序的字段名 */
    protected String sort;
    /** 排序方式: desc \ asc */
    protected String order = "";
    /** 获取一页行数 */
    protected int rows = 20;
    /** 获取的页码 */
    protected int page = 1;
    /** 起始记录 */
    protected int offset;
    protected String[] sortableFields;

    public boolean validSort(String sort){
        if(sortableFields!=null){
            String[] sorts = sort.split(",");
            for(int j=0; j<sorts.length; j++){
                boolean flag = false;
                loopFields:for(int i=0; i<sortableFields.length;i++){
                    if(sorts[j].equalsIgnoreCase(sortableFields[i])){
                        flag = true;
                        break loopFields;
                    }
                }
                if(!flag)
                    return false;
            }
        }
        return true;
    }

    public String getSort() {
        if(sort!=null && sort.indexOf(",")>0)
            return null;
        return sort;
    }

    public void setSort(String sort) {
        if(validSort(sort))
            this.sort = sort.toLowerCase();
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getOffset() {
        offset = (page - 1) * rows;
        return offset;
    }
}
