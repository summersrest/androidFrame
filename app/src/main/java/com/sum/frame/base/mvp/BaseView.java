package com.sum.frame.base.mvp;

/**
 * @author liujiang
 * Desc:
 */
public interface BaseView {
    void showToast(String msg);

    void showProgressDialog();

    void showProgressDialog(String msg);

    void updateProgressText(String msg);

    void hideProgressDialog();

    void hideProgressDialog(String msg);

    void showLoading();

    void showContent();

    void showError();

    void showError(String msg);

    void showEmpty();
} 
