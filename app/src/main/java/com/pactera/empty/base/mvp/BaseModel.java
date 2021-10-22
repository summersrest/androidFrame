package com.pactera.empty.base.mvp;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.pactera.empty.base.http.HttpUtils;

/**
 * @author liujiang
 * Desc:BaseModel
 */
public class BaseModel {
    protected HttpUtils httpUtils;
    protected Context context;
    protected Fragment fragment;

    public BaseModel(Context context) {
        httpUtils = HttpUtils.instance();
        this.context = context;
    }

    public BaseModel(Fragment fragment) {
        httpUtils = HttpUtils.instance();
        this.fragment = fragment;
    }

} 
