package com.karson.lib_commen.net.bean;

import com.google.gson.annotations.JsonAdapter;
import com.karson.lib_commen.net.util.BaseJsonAdapterFactory;

import java.io.Serializable;

/**
 * @author lixuebo
 * @time 2018/1/31.
 */
public class BaseDataBean<T> implements Serializable {

    public static final String RESPONSE_SUCCESS_CODE = "000";
    public String code;
    public String message;
    public String msg;
    public String status;
    @JsonAdapter(BaseJsonAdapterFactory.class)
    public T data;

    //判断业务是否成功
    public boolean isSuccessful(){
        this.msg = this.message;
        return RESPONSE_SUCCESS_CODE.equals(code);
    }

}