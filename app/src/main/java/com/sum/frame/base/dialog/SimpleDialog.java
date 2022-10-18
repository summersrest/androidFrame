package com.sum.frame.base.dialog;

import android.content.Context;

import com.lxj.xpopup.XPopup;

import java.lang.ref.SoftReference;

/**
 * @author liujiang
 * created at: 2022/10/14 23:03
 * Desc:   简单的Dialog弹窗
 */
public class SimpleDialog {
    private final Context context;
    /**
     * 标题
     */
    private String title = "⚠警告";

    /**
     * 内容
     */
    private String message = "";

    /**
     * 确定按钮
     */
    private String positiveText = "确定";
    private OnDialogClickListener onPositiveClickListener;

    /**
     * 取消按钮
     */
    private String negativeText = "取消";
    private OnDialogClickListener onNegativeClickListener;

    /**
     * 触摸弹窗外关闭弹窗
     */
    private boolean isDismissOnTouchOutside = true;

    /**
     * 隐藏取消按钮
     */
    private boolean isHideCancel;

    /**
     * 是否自动关闭
     */
    private boolean autoDismiss = true;

    /**
     * 是否深色主题
     */
    private boolean isDarkTheme;

    /**
     * 返回按钮是否可关闭
     */
    private boolean isDismissOnBackPressed = true;

    private SimpleDialog(Context context) {
        this.context = context;
        this.title = "⚠警告";
        this.positiveText = "确定";
        this.negativeText = "取消";
    }

    public static SimpleDialog builder(Context context) {
        SoftReference<SimpleDialog> softReference = new SoftReference<>(new SimpleDialog(context));
        return (SimpleDialog) softReference.get();
    }

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public SimpleDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 设置内容
     *
     * @param message
     * @return
     */
    public SimpleDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * 确定按钮
     *
     * @return
     */
    public SimpleDialog setPositiveButton(OnDialogClickListener onPositiveClickListener) {
        this.onPositiveClickListener = onPositiveClickListener;
        return this;
    }

    /**
     * 确定按钮
     *
     * @param text
     * @return
     */
    public SimpleDialog setPositiveButton(String text, OnDialogClickListener onPositiveClickListener) {
        this.positiveText = text;
        this.onPositiveClickListener = onPositiveClickListener;
        return this;
    }

    /**
     * 取消按钮
     *
     * @return
     */
    public SimpleDialog setNegativeButton(OnDialogClickListener onNegativeClickListener) {
        this.onNegativeClickListener = onNegativeClickListener;
        return this;
    }

    /**
     * 取消按钮
     *
     * @param text
     * @return
     */
    public SimpleDialog setNegativeButton(String text, OnDialogClickListener onNegativeClickListener) {
        this.onNegativeClickListener = onNegativeClickListener;
        this.negativeText = text;
        return this;
    }

    /**
     * 是否隐藏取消按钮
     *
     * @param isHideCancel
     * @return
     */
    public SimpleDialog isHideCancel(boolean isHideCancel) {
        this.isHideCancel = isHideCancel;
        return this;
    }

    /**
     * 点击谈窗外关闭弹窗
     *
     * @param dismissOnTouchOutside
     * @return
     */
    public SimpleDialog setDismissOnTouchOutside(boolean dismissOnTouchOutside) {
        isDismissOnTouchOutside = dismissOnTouchOutside;
        return this;
    }

    /**
     * 操作结束后是否自动关闭弹窗
     *
     * @param autoDismiss
     * @return
     */
    public SimpleDialog autoDismiss(boolean autoDismiss) {
        this.autoDismiss = autoDismiss;
        return this;
    }

    /**
     * 是否深色主题
     *
     * @return
     */
    public SimpleDialog isDarkTheme(boolean isDarkTheme) {
        this.isDarkTheme = isDarkTheme;
        return this;
    }

    /**
     * 返回按钮是否可以关闭弹窗
     *
     * @return
     */
    public SimpleDialog dismissOnBackPressed(boolean isDismissOnBackPressed) {
        this.isDismissOnBackPressed = isDismissOnBackPressed;
        return this;
    }

    public void show() {
        new XPopup.Builder(context)
                .autoDismiss(autoDismiss)
                .isDarkTheme(isDarkTheme)
                .dismissOnBackPressed(isDismissOnBackPressed)
                .dismissOnTouchOutside(isDismissOnTouchOutside)
                .asConfirm(title, message, negativeText, positiveText,
                        () -> {
                            if (null != onPositiveClickListener) {
                                onPositiveClickListener.onCLick();
                            }
                        }, () -> {
                            if (null != onNegativeClickListener) {
                                onNegativeClickListener.onCLick();
                            }
                        }, isHideCancel)
                .show();
    }

    public interface OnDialogClickListener {
        void onCLick();
    }
}
