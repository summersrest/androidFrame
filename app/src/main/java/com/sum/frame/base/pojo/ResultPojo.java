package com.sum.frame.base.pojo;

/**
 * @author liujiang
 * Desc:
 */
public class ResultPojo<T> {
    private T data;
    private String code;
    private String message;

    public ResultPojo(T data, String code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public ResultPojo(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
