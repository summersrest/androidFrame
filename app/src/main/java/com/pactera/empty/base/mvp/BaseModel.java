package com.pactera.empty.base.mvp;

import android.content.Context;

import com.pactera.empty.base.http.HttpUtils;

/**
 * @author liujiang
 * Desc:BaseModel
 */
public class BaseModel {
    protected HttpUtils httpUtils;
    protected Context context;

    public BaseModel(Context context) {
        httpUtils = HttpUtils.instance();
        this.context = context;
    }
} 
