package com.sum.frame.base.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lzy.okgo.callback.AbsCallback;
import com.sum.frame.base.pojo.BasePojo;
import com.sum.frame.base.pojo.ResultPojo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * @author liujiang
 * Desc: 返回数据解析
 */
public abstract class JsonCallback<T> extends AbsCallback<ResultPojo<T>> {
    @Override
    public ResultPojo<T> convertResponse(okhttp3.Response response) throws Throwable {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        if (null != response.body()) {
            String content = response.body().string();
//        String content = "{\"success\":true,\"result\":1.23,\"files\":\"\",\"error\":\"\"}";
            BasePojo<T> pojo = JSON.parseObject(content, new TypeReference<BasePojo<T>>() {
            });
            response.close();
            if (null != pojo && pojo.getError() == 0) {
                if (type == String.class || type == Integer.class || type == Boolean.class|| type == Double.class|| type == Float.class)
                    return new ResultPojo<>(pojo.getData(), pojo.getMessage());
                try {
                    return new ResultPojo<>(JSON.parseObject(pojo.getData().toString(), type), pojo.getMessage());
                } catch(Exception exception) {
                    return new ResultPojo<>(pojo.getData(), pojo.getMessage());
                }
            } else if (null != pojo && pojo.getError() != 0) {
                throw new IllegalStateException(pojo.getMessage());
            } else {
                throw new IllegalStateException("服务端接口错误");
            }
        } else {
            throw new IllegalStateException("服务端接口错误");
        }

    }

    @Override
    public void onError(com.lzy.okgo.model.Response<ResultPojo<T>> response) {
        super.onError(response);
        onError(response.getException().getMessage());
    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<ResultPojo<T>> response) {
        onSuccess(response.body().getData(), response.body().getMessage());
    }

    protected abstract void onSuccess(T t, String msg);

    protected abstract void onError(String msg);
}
