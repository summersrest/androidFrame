package com.sum.frame.base.utils;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.sum.frame.R;


/**
 * @author liujiang
 * Desc:toast封装
 */
public class ToastUtils {
    private ToastUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        if (isShow) {
            show(message, Toast.LENGTH_LONG);
        }

    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        if (isShow) {
            show(message, Toast.LENGTH_LONG);
        }

    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        if (isShow && !TextUtils.isEmpty(message)) {
            com.hjq.toast.ToastUtils.setView(R.layout.view_toast_layout);
            com.hjq.toast.ToastUtils.setGravity(Gravity.BOTTOM, 0, ScreenUtils.getScreenHeight() / 12);
            com.hjq.toast.ToastUtils.delayedShow(message, 200);
        }
    }
} 
