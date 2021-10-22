package com.sum.frame.base.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sum.frame.R;
import com.sum.frame.base.http.HttpUtils;
import com.sum.frame.base.mvp.BasePresenter;
import com.sum.frame.base.mvp.BaseView;
import com.sum.frame.base.utils.ToastUtils;
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

    public abstract void initView();
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
        //取消网络请求
        HttpUtils.instance().cancelByTag(this);
        //断开presenter链接，防止内存泄漏
        if (null != presenter) {
            presenter.detach();
            presenter = null;
        }
    }
}

