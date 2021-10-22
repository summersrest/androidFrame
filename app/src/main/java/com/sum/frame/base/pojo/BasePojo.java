package com.sum.frame.base.pojo;

import java.io.Serializable;

/**
 * @author liujiang
 * Desc: json解析对象
 */
public class BasePojo<T> implements Serializable {
    private int errorCode;
    private T data;
    private String errorMsg;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
