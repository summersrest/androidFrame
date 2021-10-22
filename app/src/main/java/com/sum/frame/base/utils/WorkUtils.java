package com.sum.frame.base.utils;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.sum.frame.base.App;

import androidx.core.content.ContextCompat;

/**
 * @author liujiang
 * Desc:工具类
 */
public class WorkUtils {
    /**
     * 获取drawable资源
     *
     * @param resId
     * @return
     */
    public static Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(App.instance(), resId);
    }

    /**
     * 获取字符串资源
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return App.instance().getResources().getString(resId);
    }

    /**
     * 获取color资源
     *
     * @param resId
     * @return
     */
    public static int getColor(int resId) {
        return ContextCompat.getColor(App.instance(), resId);
    }

    /**
     * 根据传入控件的坐标和用户的焦点坐标，判断是否隐藏键盘，如果点击的位置在控件内，则不隐藏键盘
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
} 
