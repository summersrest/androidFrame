package com.sum.frame.base.http;

import android.text.TextUtils;

import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.utils.IOUtils;
import com.lzy.okgo.utils.OkLogger;
import com.sum.frame.base.utils.Constant;
import com.sum.frame.base.utils.SPUtils;
import com.sum.frame.base.utils.Config;
import com.sum.frame.base.utils.DateUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;

/**
 * @author liujiang
 * Desc: token失效拦截器
 */
public class RefreshTokenInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private volatile HttpLoggingInterceptor.Level printLevel = HttpLoggingInterceptor.Level.NONE;
    private java.util.logging.Level colorLevel;
    private Logger logger;
    public RefreshTokenInterceptor(String tag) {
        logger = Logger.getLogger(tag);
    }

    public void setPrintLevel(HttpLoggingInterceptor.Level level) {
        if (printLevel == null) throw new NullPointerException("printLevel == null. Use Level.NONE instead.");
        printLevel = level;
    }

    public void setColorLevel(java.util.logging.Level level) {
        colorLevel = level;
    }

    private void log(String message) {
        logger.log(colorLevel, message);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        /****************刷新token开始******************/
        //需要token的URL
        if (!request.url().toString().contains(Config.LOGIN)) {
            //token
            String token = SPUtils.getString(Constant.ACCESS_TOKEN, null);
            //token不为空，已登录
            if (!TextUtils.isEmpty(token)) {
                //上次token刷新时间
                long lastRefreshTime = SPUtils.getLong(Constant.TOKEN_REFRESH_TIME, 0);
                //当前时间
                long now = DateUtils.getCurrentStamps();
                //token有效时间
                long indate = Constant.INDATE;
                //token已过期,重新请求登录接口
                if (now - lastRefreshTime > indate) {
                    //账号密码登录（同步网络请求,阻塞方法）
                    //用户名
                    String userName = SPUtils.getString(Constant.USERNAME, null);
                    //密码
                    String pwd = SPUtils.getString(Constant.PWD, null);
                    if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(pwd)) {
                        //进行登录网络请求
//                        LoginParametersBean model = new LoginParametersBean(userName, pwd);
//                        Response result = OkGo.post(Config.LOGIN)
//                                .upJson(JSON.toJSONString(model))
//                                .execute();
//                        assert result.body() != null;
//                        BasePojo<LoginBean> pojo = JSON.parseObject(result.body().string(), new TypeReference<BasePojo<LoginBean>>() {
//                        });
//                        if (null != pojo && 200 == pojo.getCode()) {
//                            String tokenNew = pojo.getResult().getToken();
//                            L.showD("tokenNew：" + tokenNew);
//                            //存储token
//                            SPUtils.setString(Constant.ACCESS_TOKEN, tokenNew);
//                            //更新token刷新时间
//                            SPUtils.setLong(Constant.TOKEN_REFRESH_TIME, DateUtils.getCurrentStamps());
//                            //更新网络请求header中的token
//                            Request newRequest = chain.request()
//                                    .newBuilder()
//                                    .header("X-Access-Token", tokenNew)
//                                    .build();
//
//
//                            /****************刷新token结束******************/
//                            /****************定义日志开始******************/
//                            if (printLevel == HttpLoggingInterceptor.Level.NONE) {
//                                return chain.proceed(newRequest);
//                            }
//                            //请求日志拦截
//                            logForRequest(newRequest, chain.connection());
//                            //执行请求，计算请求时间
//                            long startNs = System.nanoTime();
//                            Response response;
//                            try {
//                                response = chain.proceed(newRequest);
//                            } catch (Exception e) {
//                                log("<-- HTTP FAILED: " + e);
//                                throw e;
//                            }
//                            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
//                            /****************定义日志结束******************/
//
//                            //响应日志拦截
//                            return logForResponse(response, tookMs);
//                        }
                    }
                }
            }
        }

        /****************刷新token结束******************/
        /****************定义日志开始******************/
        if (printLevel == HttpLoggingInterceptor.Level.NONE) {
            return chain.proceed(request);
        }
        //请求日志拦截
        logForRequest(request, chain.connection());
        //执行请求，计算请求时间
        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            log("<-- HTTP FAILED: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        /****************定义日志结束******************/
        //响应日志拦截
        return logForResponse(response, tookMs);
    }

    private Response logForResponse(Response response, long tookMs) {
        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        ResponseBody responseBody = clone.body();
        boolean logBody = (printLevel == HttpLoggingInterceptor.Level.BODY);
        boolean logHeaders = (printLevel == HttpLoggingInterceptor.Level.BODY || printLevel == HttpLoggingInterceptor.Level.HEADERS);

        try {
            log("<-- " + clone.code() + ' ' + clone.message() + ' ' + clone.request().url() + " (" + tookMs + "ms）");
            if (logHeaders) {
                Headers headers = clone.headers();
                for (int i = 0, count = headers.size(); i < count; i++) {
                    log("\t" + headers.name(i) + ": " + headers.value(i));
                }
                log(" ");
                if (logBody && HttpHeaders.hasBody(clone)) {
                    if (responseBody == null) return response;

                    if (isPlaintext(responseBody.contentType())) {
                        byte[] bytes = IOUtils.toByteArray(responseBody.byteStream());
                        MediaType contentType = responseBody.contentType();
                        String body = new String(bytes, getCharset(contentType));
                        log("\tbody:" + body);
                        responseBody = ResponseBody.create(responseBody.contentType(), bytes);
                        return response.newBuilder().body(responseBody).build();
                    } else {
                        log("\tbody: maybe [binary body], omitted!");
                    }
                }
            }
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
        } finally {
            log("<-- END HTTP");
        }
        return response;
    }

    private void logForRequest(Request request, Connection connection) throws IOException {
        boolean logBody = (printLevel == HttpLoggingInterceptor.Level.BODY);
        boolean logHeaders = (printLevel == HttpLoggingInterceptor.Level.BODY || printLevel == HttpLoggingInterceptor.Level.HEADERS);
        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;

        try {
            String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
            log(requestStartMessage);

            if (logHeaders) {
                if (hasRequestBody) {
                    // Request body headers are only present when installed as a network interceptor. Force
                    // them to be included (when available) so there values are known.
                    if (requestBody.contentType() != null) {
                        log("\tContent-Type: " + requestBody.contentType());
                    }
                    if (requestBody.contentLength() != -1) {
                        log("\tContent-Length: " + requestBody.contentLength());
                    }
                }
                Headers headers = request.headers();
                for (int i = 0, count = headers.size(); i < count; i++) {
                    String name = headers.name(i);
                    // Skip headers from the request body as they are explicitly logged above.
                    if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                        log("\t" + name + ": " + headers.value(i));
                    }
                }

                log(" ");
                if (logBody && hasRequestBody) {
                    if (isPlaintext(requestBody.contentType())) {
                        bodyToString(request);
                    } else {
                        log("\tbody: maybe [binary body], omitted!");
                    }
                }
            }
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
        } finally {
            log("--> END " + request.method());
        }
    }

    private static Charset getCharset(MediaType contentType) {
        Charset charset = contentType != null ? contentType.charset(UTF8) : UTF8;
        if (charset == null) charset = UTF8;
        return charset;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private static boolean isPlaintext(MediaType mediaType) {
        if (mediaType == null) return false;
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        String subtype = mediaType.subtype();
        if (subtype != null) {
            subtype = subtype.toLowerCase();
            if (subtype.contains("x-www-form-urlencoded") || subtype.contains("json") || subtype.contains("xml") || subtype.contains("html")) //
                return true;
        }
        return false;
    }

    private void bodyToString(Request request) {
        try {
            Request copy = request.newBuilder().build();
            RequestBody body = copy.body();
            if (body == null) return;
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            Charset charset = getCharset(body.contentType());
            log("\tbody:" + buffer.readString(charset));
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
        }
    }
}
