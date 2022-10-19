package com.sum.frame.base.utils;

import com.sum.frame.BuildConfig;

/**
 * @author liujiang
 * Desc: 网络请求地址
 */
public class Config {
    public static final String BASE_SERVICE = BuildConfig.BASE_URL;    //开发环境

    public static final String SERVER_HOME = BASE_SERVICE + "";

    /**
     * 登录接口
     */
    public static final String LOGIN = SERVER_HOME + "";
} 
