package com.example.domain.entity.user;

import com.example.domain.base.QueryBase;

/**
 * Created by zxn on 2018/2/24.
 */
public class QMenu extends QueryBase{
    //主键
    private Long id;
    //名称
    private String name;
    //类型（1：一级菜单；2：二级菜单）
    private String type;
    //来源，表示各个子系统的菜单分类
    private String fromType;
    //URL
    private Long resourceId;
    //排序
    private Integer seq;
    //父级菜单ID
    private Integer pid;
    //图标样式类型
    private String iconClass;
    //状态（1：可用；0：禁用）
    private String state;
    //可视（1：是；0：否）
    private String visable;
    //用户ID
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getSeq(){
        return seq;
    }

    public void setSeq(Integer seq){
        this.seq = seq;
    }

    public Integer getPid(){
        return pid;
    }

    public void setPid(Integer pid){
        this.pid = pid;
    }

    public String getIconClass(){
        return iconClass;
    }

    public void setIconClass(String iconClass){
        this.iconClass = iconClass;
    }

    public String getState(){
        return state;
    }

    public void setState(String state){
        this.state = state;
    }

    public String getVisable() {
        return visable;
    }

    public void setVisable(String visable) {
        this.visable = visable;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
