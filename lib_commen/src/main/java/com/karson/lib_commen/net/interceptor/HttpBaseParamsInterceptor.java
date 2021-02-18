package com.karson.lib_commen.net.interceptor;

import android.text.TextUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * @author chentong
 * @date 2019-2-20
 * 设置公共参数
 * head params query
 * 在指定patternUrl中设置公共参数
 */
public class HttpBaseParamsInterceptor implements Interceptor {

    private Map<String, String> queryParamsMap = new HashMap<>();
    private Map<String, String> paramsMap = new HashMap<>();
    private Map<String, String> headerParamsMap = new HashMap<>();
    private List<String> headerLinesList = new ArrayList<>();
    private List<String> urlPatternList = new ArrayList<>(  );

    private static final String X_WWW_FORM_URLENCODED = "x-www-form-urlencoded";
    private static final String JSON_TYPE="application/json";

    private Builder builder;
    private UpdateHandler updateHandler;

    public HttpBaseParamsInterceptor() {
    }

    private boolean isMatchPatternUrl(String url){
        for (String patternUrl :urlPatternList){
            if (contains( url,patternUrl)) return true;
        }
        return false;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        //每次获取公共数据
        if (updateHandler != null) {
            builder = updateHandler.createNewBuilder();
            reloadBuilder( builder );
        }

        Request request = chain.request();

        String url = request.url().toString();

        //如果不在匹配url范围内，不设置公共的Param
        if (!isMatchPatternUrl( url )){
            return chain.proceed( request );
        }

        Request.Builder requestBuilder = request.newBuilder();

        // 设置header
        Headers.Builder headerBuilder = request.headers().newBuilder();
        if (headerParamsMap.size() > 0) {
            Iterator iterator = headerParamsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                headerBuilder.add( (String) entry.getKey(), (String) entry.getValue() );
            }
        }

        if (headerLinesList.size() > 0) {
            for (String line : headerLinesList) {
                headerBuilder.add( line );
            }
        }

        requestBuilder.headers( headerBuilder.build() );

        // process queryParams inject whatever it's GET or POST
        if (queryParamsMap.size() > 0) {
            injectParamsIntoUrl( request, requestBuilder, queryParamsMap );
        }

        // process header params end
        //设置param
        //请求体 可以为空
        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        String contentType = "";

        if (hasRequestBody) {
            //contentType存在空
            if (requestBody.contentType() != null) {
                contentType = requestBody.contentType().toString();
            }
        }

        //X_WWW_FORM_URLENCODED 追加数据
        if (request.method().equals( "POST" ) && contentType.toLowerCase().contains( X_WWW_FORM_URLENCODED )) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            if (paramsMap.size() > 0) {
                Iterator iterator = paramsMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    formBodyBuilder.add( (String) entry.getKey(), (String) entry.getValue() );
                }
            }

            RequestBody formBody = formBodyBuilder.build();
            String formBodyString = bodyToString( formBody );
            String requestBodyString = bodyToString( requestBody );
            String postBodyString = "";
            if (!TextUtils.isEmpty( requestBodyString ) && !TextUtils.isEmpty( formBodyString )) {
                postBodyString = requestBodyString + "&" + formBodyString;
            } else if (!TextUtils.isEmpty( requestBodyString ) || TextUtils.isEmpty( formBodyString )) {
                postBodyString = requestBodyString;
            } else if (TextUtils.isEmpty( requestBodyString ) && !TextUtils.isEmpty( formBodyString )) {
                postBodyString = formBodyString;
            } else if (TextUtils.isEmpty( requestBodyString ) && TextUtils.isEmpty( formBodyString )) {
                postBodyString = "";
            }
            requestBuilder.post( RequestBody.create( MediaType.parse( "application/x-www-form-urlencoded;charset=UTF-8" ), postBodyString ) );

            //追加数据
        } else if(request.method().equals( "POST" ) && contentType.toLowerCase().contains( JSON_TYPE )){

            String requestBodyString = bodyToString( requestBody );
            if (TextUtils.isEmpty( requestBodyString )){
                requestBodyString = "{}";
            }

            try {
                JSONObject jsonObject = new JSONObject(requestBodyString);
                //追加数据
                if (paramsMap.size() > 0) {
                    Iterator iterator = paramsMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) iterator.next();
                        String key = (String) entry.getKey();
                        String value = (String) entry.getValue();
                        jsonObject.put( key,value );
                    }
                    requestBodyString = jsonObject.toString();
                    requestBuilder.post( RequestBody.create( MediaType.parse( "application/json;charset=UTF-8" ), requestBodyString ) );
                }
            } catch (Exception e) {
                requestBuilder.post( RequestBody.create( MediaType.parse( "application/json;charset=UTF-8" ), requestBodyString ) );
            }

        }else{
            injectParamsIntoUrl( request, requestBuilder, paramsMap );
        }

        request = requestBuilder.build();
        return chain.proceed( request );
    }

    // func to inject params into url
    private void injectParamsIntoUrl(Request request, Request.Builder requestBuilder, Map<String, String> paramsMap) {
        HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
        if (paramsMap.size() > 0) {
            Iterator iterator = paramsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                httpUrlBuilder.addQueryParameter( (String) entry.getKey(), (String) entry.getValue() );
            }
        }

        requestBuilder.url( httpUrlBuilder.build() );
    }

    private String bodyToString(final RequestBody requestBody) {
        if (requestBody == null) return "";
        try {
            Buffer buffer = new Buffer();
            requestBody.writeTo( buffer );
            return buffer.readUtf8();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 设置刷新句柄
     * @param updateHandler
     */
    public void setUpdateHandler(UpdateHandler updateHandler) {
        this.updateHandler = updateHandler;
    }

    public interface UpdateHandler {
        Builder createNewBuilder();
    }

    /**
     * 重新加载数据
     * @param builder
     */
    private void reloadBuilder(Builder builder) {
        queryParamsMap.clear();
        paramsMap.clear();
        headerParamsMap.clear();
        headerLinesList.clear();
        urlPatternList.clear();

        queryParamsMap.putAll( builder.queryParamsMap );
        paramsMap.putAll( builder.paramsMap );
        headerParamsMap.putAll( builder.headerParamsMap );
        headerLinesList.addAll( builder.headerLinesList );
        urlPatternList.addAll( builder.urlPatternList );
    }

    /**
     * 数据要刷新
     */
    public static class Builder {

        private Map<String, String> queryParamsMap = new HashMap<>();
        private Map<String, String> paramsMap = new HashMap<>();
        private Map<String, String> headerParamsMap = new HashMap<>();
        private List<String> headerLinesList = new ArrayList<>();
        private List<String> urlPatternList = new ArrayList<>(  );

        public Builder() {
        }

        public Builder addParam(String key, String value) {
            paramsMap.put( key, value );
            return this;
        }

        public Builder addParamsMap(Map<String, String> paramsMap) {
            paramsMap.putAll( paramsMap );
            return this;
        }

        public Builder addHeaderParam(String key, String value) {
            headerParamsMap.put( key, value );
            return this;
        }

        public Builder addHeaderParamsMap(Map<String, String> headerParamsMap) {
            headerParamsMap.putAll( headerParamsMap );
            return this;
        }

        public Builder addHeaderLine(String headerLine) {
            int index = headerLine.indexOf( ":" );
            if (index == -1) {
                throw new IllegalArgumentException( "Unexpected header: " + headerLine );
            }
            headerLinesList.add( headerLine );
            return this;
        }

        public Builder addHeaderLinesList(List<String> headerLinesList) {
            for (String headerLine : headerLinesList) {
                int index = headerLine.indexOf( ":" );
                if (index == -1) {
                    throw new IllegalArgumentException( "Unexpected header: " + headerLine );
                }
                headerLinesList.add( headerLine );
            }
            return this;
        }

        public Builder addQueryParam(String key, String value) {
            queryParamsMap.put( key, value );
            return this;
        }

        public Builder addQueryParamsMap(Map<String, String> queryParamsMap) {
            queryParamsMap.putAll( queryParamsMap );
            return this;
        }

        public Builder addUrlPatternList(List list){
            urlPatternList.addAll( list );
            return this;
        }

        public Builder build() {
            return this;
        }

    }

    public static boolean contains(String content, String str) {
        if (content == null) return false;
        String str1 = trim(content);
        String str2 = trim(str);
        return str1.contains(str2);
    }

    //清空空格
    public static String trim(String str) {
        if (isEmpty(str)) return "";
        return str.trim();
    }

    //判断是否是空字符串  纯空格也包含
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.toString().trim().length() == 0;
    }

}