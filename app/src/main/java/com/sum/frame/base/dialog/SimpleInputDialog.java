package com.sum.frame.base.dialog;

import android.content.Context;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

import java.lang.ref.SoftReference;

/**
 * @author liujiang
 * created at: 2022/10/18 16:13
 * Desc:    带输入框的dialog
 */
public class SimpleInputDialog {
    private final Context context;

    /**
     * 标题
     */
    private String title = "请填写";


    /**
     * 确定按钮
     */
    private OnSimpleInputResultListener onSimpleInputResultListener;

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

    /**
     * 是否自动打开输入法
     */
    private boolean autoOpenSoftInput = true;

    /**
     * 输入框默认内容
     */
    private String content;

    /**
     * 提示文字
     */
    private String hint;

    private SimpleInputDialog(Context context) {
        this.context = context;
        this.title = "请输入";
    }

    public static SimpleInputDialog builder(Context context) {
        SoftReference<SimpleInputDialog> softReference = new SoftReference<>(new SimpleInputDialog(context));
        return (SimpleInputDialog) softReference.get();
    }

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public SimpleInputDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 确定按钮
     *
     * @return
     */
    public SimpleInputDialog setConfirm(OnSimpleInputResultListener onSimpleInputResultListener) {
        this.onSimpleInputResultListener = onSimpleInputResultListener;
        return this;
    }


    /**
     * 是否隐藏取消按钮
     *
     * @param isHideCancel
     * @return
     */
    public SimpleInputDialog isHideCancel(boolean isHideCancel) {
        this.isHideCancel = isHideCancel;
        return this;
    }

    /**
     * 点击谈窗外关闭弹窗
     *
     * @param dismissOnTouchOutside
     * @return
     */
    public SimpleInputDialog setDismissOnTouchOutside(boolean dismissOnTouchOutside) {
        isDismissOnTouchOutside = dismissOnTouchOutside;
        return this;
    }

    /**
     * 操作结束后是否自动关闭弹窗
     *
     * @param autoDismiss
     * @return
     */
    public SimpleInputDialog autoDismiss(boolean autoDismiss) {
        this.autoDismiss = autoDismiss;
        return this;
    }

    /**
     * 是否深色主题
     *
     * @return
     */
    public SimpleInputDialog isDarkTheme(boolean isDarkTheme) {
        this.isDarkTheme = isDarkTheme;
        return this;
    }

    /**
     * 返回按钮是否可以关闭弹窗
     *
     * @return
     */
    public SimpleInputDialog dismissOnBackPressed(boolean isDismissOnBackPressed) {
        this.isDismissOnBackPressed = isDismissOnBackPressed;
        return this;
    }

    /**
     * 是否自动打开输入法
     * @param autoOpenSoftInput
     * @return
     */
    public SimpleInputDialog autoOpenSoftInput(boolean autoOpenSoftInput) {
        this.autoOpenSoftInput = autoOpenSoftInput;
        return this;
    }

    /**
     * 输入框默认内容
     * @param content
     * @return
     */
    public SimpleInputDialog setContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * 输入框默认hint
     * @param hint
     * @return
     */
    public SimpleInputDialog setHint(String hint) {
        this.hint = hint;
        return this;
    }

    public void show() {
        new XPopup.Builder(context)
                .autoDismiss(autoDismiss)
                .isDarkTheme(isDarkTheme)
                .autoOpenSoftInput(autoOpenSoftInput)
                .dismissOnBackPressed(isDismissOnBackPressed)
                .dismissOnTouchOutside(isDismissOnTouchOutside)
                .asInputConfirm(title, content, hint, text -> {
                    if (null != onSimpleInputResultListener) {
                        onSimpleInputResultListener.onResult(text);
                    }
                })
                .show();
    }

    public interface OnSimpleInputResultListener {
        void onResult(String result);
    }
}
