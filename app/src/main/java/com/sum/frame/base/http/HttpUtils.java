package com.sum.frame.base.http;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liujiang
 * Desc:网络请求
 */
public class HttpUtils {
    private final Map<String, Object> parameter = new HashMap<>();

    private Object body;

    private Object tag;

    private String url;

    private HttpUtils() {
    }

    private volatile static HttpUtils httpUtils;

    public static HttpUtils instance() {
        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                if (httpUtils == null) {
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }

    public HttpUtils setParameter(String key, String value) {
        parameter.put(key, value);
        return this;
    }

    public HttpUtils setParameter(String key, Integer value) {
        parameter.put(key, value);
        return this;
    }

    public HttpUtils setParameter(String key, Long value) {
        parameter.put(key, value);
        return this;
    }

    public HttpUtils setParameter(String key, Float value) {
        parameter.put(key, value);
        return this;
    }

    public HttpUtils setParameter(String key, Boolean value) {
        parameter.put(key, value);
        return this;
    }

    public HttpUtils setParameter(String key, File value) {
        parameter.put(key, value);
        return this;
    }

    public HttpUtils setBody(Object body){
        this.body = body;
        return this;
    }

    public HttpUtils tag(Object tag) {
        this.tag = tag;
        return this;
    }

    public HttpUtils url(String url) {
        this.url = getBaseUrl(url);
        return this;
    }

    private String getBaseUrl(String url) {
//        return Config.BASE_SERVICE + url;
        return url;
    }

    public void clearParameter() {
        parameter.clear();
        body = null;
        tag = null;
        url = "";
    }

    /**
     * get请求
     * @param jsonCallback
     * @param <T>
     * @return
     */
    public <T> T getRequest(JsonCallback<T> jsonCallback) {
        setCommonParameter();
        new Okgo().getRequest(tag, url, parameter, jsonCallback);
        clearParameter();
        return null;
    }

    /**
     * post请求
     * @param jsonCallback
     * @param <T>
     * @return
     */
    public <T> T postRequest(AbsCallback<T> jsonCallback) {
        setCommonParameter();
        new Okgo().postRequest(tag, url, parameter, body, jsonCallback);
        clearParameter();
        return null;
    }

    /**
     * put请求
     * @param jsonCallback
     * @param <T>
     * @return
     */
    public <T> T putRequest(AbsCallback<T> jsonCallback) {
        setCommonParameter();
        new Okgo().putRequest(tag, url, parameter, body, jsonCallback);
        clearParameter();
        return null;
    }

    /**
     * @author liujiang
     * created at: 2021/5/17 17:24
     * Desc: 添加公共参数
     */
    private void setCommonParameter() {

    }

    /**
     * 取消所有网络请求
     */
    public void cancelAll() {
        //取消所有请求
        OkGo.getInstance().cancelAll();
    }

    /**
     * 球销某一个网络请求
     * @param tag
     */
    public void cancelByTag(Object tag) {
        //取消所有请求
        OkGo.getInstance().cancelTag(tag);
    }
} 
