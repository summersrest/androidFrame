package com.sum.frame.base.utils;

import java.util.Date;

/**
 * @author liujiang
 * Desc:Date工具类
 */
public class DateUtils {
    /**
     * 得到当前时间戳
     *
     * @return yyyy-MM-dd HH:mm:ss格式的当前时间
     */
    public static long getCurrentStamps() {
        return new Date().getTime();
    }
} 
