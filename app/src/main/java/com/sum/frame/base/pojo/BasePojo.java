package com.sum.frame.base.pojo;

import java.io.Serializable;

/**
 * @author liujiang
 * Desc: json解析对象
 */
public class BasePojo<T> implements Serializable {
    private int error;
    private T data;
    private String message;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
