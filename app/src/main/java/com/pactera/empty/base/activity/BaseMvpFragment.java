package com.pactera.empty.base.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.pactera.empty.R;
import com.pactera.empty.base.http.HttpUtils;
import com.pactera.empty.base.mvp.BasePresenter;
import com.pactera.empty.base.mvp.BaseView;
import com.pactera.empty.base.utils.ToastUtils;
import com.sum.multiple.MultipleStatusView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

/**
 * @author liujiang
 * Desc:基类mvp
 */
public abstract class BaseMvpFragment<V extends ViewBinding, P extends BasePresenter> extends BaseFragment<V> implements BaseView {
    protected P presenter;

    public abstract P createPresenter();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 获取MultipleStatusView
     */
    protected MultipleStatusView getMultipleStatusView() {
        return null;
    }

    /**
     * 显示toast
     *
     * @param msg
     */
    @Override
    public void showToast(String msg) {
        if (!TextUtils.isEmpty(msg))
            ToastUtils.showShort(msg);
    }

    /**
     * 显示进度条弹窗
     */
    @Override
    public void showProgressDialog() {
        showDialog(getResources().getString(R.string.loading));
    }

    /**
     * 显示进度条弹窗
     *
     * @param msg
     */
    @Override
    public void showProgressDialog(String msg) {
        if (!TextUtils.isEmpty(msg))
            showDialog(msg);
    }

    /**
     * 隐藏进度条弹窗
     */
    @Override
    public void hideProgressDialog() {
        hintDialog(null);
    }

    /**
     * 隐藏进度条弹窗
     *
     * @param msg
     */
    @Override
    public void hideProgressDialog(String msg) {
        hintDialog(msg);
    }

    /**
     * 加载loading视图
     */
    @Override
    public void showLoading() {
        getMultipleStatusView().showLoading();
    }

    /**
     * 加载内容视图
     */
    @Override
    public void showContent() {
        getMultipleStatusView().showContent();
    }

    /**
     * 加载错误页面布局
     */
    @Override
    public void showError() {
        getMultipleStatusView().showEmpty();
    }

    /**
     * 加载错误页面布局
     *
     * @param msg
     */
    @Override
    public void showError(String msg) {
        if (!TextUtils.isEmpty(msg))
            ToastUtils.showShort(msg);
        getMultipleStatusView().showError();
    }

    /**
     * 加载空页面布局
     */
    @Override
    public void showEmpty() {
        getMultipleStatusView().showEmpty();
    }

    /**
     * 取消网络请求
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        HttpUtils.instance().cancelTag(context);
    }
}

