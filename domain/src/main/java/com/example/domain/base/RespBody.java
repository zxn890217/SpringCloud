package com.example.domain.base;

/**
 * Created by zxn on 2017/10/23.
 */
public class RespBody {
    //请求结果
    private boolean success;
    //消息提示
    private String msg;
    //返回结果
    private Object result;

    public RespBody(){}

    public RespBody(boolean success, String msg){
        this.success = success;
        this.msg = msg;
    }

    public RespBody(boolean success, String msg, Object result){
        this.success = success;
        this.msg = msg;
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
