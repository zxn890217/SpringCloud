package com.example.domain.base;

/**
 * Created by zxn on 2018/1/11.
 */
public class QueryBase {
    /** 要排序的字段名 */
    protected String sort;
    /** 排序方式: desc \ asc */
    protected String order = "";
    /** 获取一页行数 */
    protected int limit;
    /** 起始记录 */
    protected int offset;
    /**可排序字段声明*/
    protected String sortableFields;

    public boolean validSort(String sort){
        if(sortableFields!=null){
            sortableFields = sortableFields.toLowerCase();
            String[] sorts = sort.toLowerCase().split(",");
            for(int j=0; j<sorts.length; j++){
                if(sortableFields.indexOf(sort)<0)
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

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
