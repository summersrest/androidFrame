package com.pactera.empty.base.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lzy.okgo.callback.AbsCallback;
import com.pactera.empty.base.pojo.BasePojo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * @author liujiang
 * Desc: 返回数据解析
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {
    @Override
    public T convertResponse(Response response) throws Throwable {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        String content = response.body().string();
//        String content = "{\"success\":true,\"result\":1.23,\"files\":\"\",\"error\":\"\"}";
        BasePojo<T> pojo = JSON.parseObject(content, new TypeReference<BasePojo<T>>() {
        });
        response.close();
        if (null != pojo && pojo.getErrorCode() == 0) {
            if (type == String.class || type == Integer.class || type == Boolean.class|| type == Double.class|| type == Float.class)
                return pojo.getData();
            try {
                return JSON.parseObject(pojo.getData().toString(), type);
            } catch(Exception exception) {
                return pojo.getData();
            }
        } else if (null != pojo && pojo.getErrorCode() != 0) {
            throw new IllegalStateException(pojo.getErrorMsg());
        } else {
            throw new IllegalStateException("服务端接口错误");
        }
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        onError(response.getException().getMessage());
    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<T> response) {
        onSuccess(response.body());
    }

    protected abstract void onSuccess(T t);

    protected abstract void onError(String msg);
}
