package com.yutuo.warehouse.entity;


/**
 *
 * @author zhongjh
 * @date 2021/3/27
 */
public class WanEntity<T> {

    private int errorCode;
    private String errorMsg;
    private T data;

    public int getCode() {
        return errorCode;
    }

    public void setCode(int code) {
        errorCode = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return errorMsg;
    }

    public void setMsg(String msg) {
        errorMsg = msg;
    }
}
