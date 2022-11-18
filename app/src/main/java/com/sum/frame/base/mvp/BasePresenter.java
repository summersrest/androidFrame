package com.sum.frame.base.mvp;

import android.content.Context;

import androidx.fragment.app.Fragment;

/**
 * @author liujiang
 * Desc:
 */
public abstract class BasePresenter<M extends BaseModel, V extends BaseView> {
    protected V listener;
    protected M model;
    protected Context context;
    protected Fragment fragment;

    public abstract M createModel();

    public BasePresenter(V listener, Context context) {
        this.listener = listener;
        this.context = context;
        model = createModel();
    }

    public BasePresenter(V listener, Fragment fragment) {
        this.listener = listener;
        this.fragment = fragment;
        model = createModel();
    }

    /**
     * Activity的onDestroy()中调用，端口链接防止内存泄漏
     */
    public void detach() {
        listener = null;
        model = null;
        context = null;
        fragment = null;
    }
}