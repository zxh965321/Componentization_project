package com.karson.lib_commen.net.exception

import com.google.gson.JsonIOException
import com.karson.lib_commen.util.LoggerUtil
import org.json.JSONException
import java.net.ConnectException

import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

import retrofit2.HttpException;

/**
 * Created by xuhao on 2017/12/5.
 * desc: 异常处理类
 */
object ExceptionHandle {

    fun handleException(e: Throwable): String {
        var errorCode = ErrorStatus.UNKNOWN_ERROR
        var errorMsg = "请求失败，请稍后重试"
        if (e is SocketTimeoutException) {//网络超时
            LoggerUtil.e( "网络连接异常: " + e.message)
            errorMsg = "网络连接异常"
            errorCode = ErrorStatus.NETWORK_ERROR
        } else if (e is ConnectException) { //均视为网络错误
            LoggerUtil.e( "网络连接异常: " + e.message)
            errorMsg = "网络连接异常"
            errorCode = ErrorStatus.NETWORK_ERROR
            //e is JsonParseException
        }else if( e is HttpException){
            errorMsg = convertStatusCode(e)?:""
            errorCode = ErrorStatus.SERVER_ERROR
        }else if (e is JSONException
            || e is ParseException || e is JSONException || e is JsonIOException
        ) {   //均视为解析错误
            LoggerUtil.e( "数据解析异常: " + e.message)
            errorMsg = "数据解析异常"
            errorCode = ErrorStatus.SERVER_ERROR
        } else if (e is ApiException) {//服务器返回的错误信息
            errorMsg = e.message.toString()
            errorCode = ErrorStatus.SERVER_ERROR
        } else if (e is UnknownHostException) {
            LoggerUtil.e("网络连接异常: " + e.message)
            errorMsg = "网络连接异常"
            errorCode = ErrorStatus.NETWORK_ERROR
        } else if (e is IllegalArgumentException) {
            errorMsg = "参数错误"
            errorCode = ErrorStatus.SERVER_ERROR
        }else {//未知错误
            try {
                LoggerUtil.e( "错误: " + e.message)
            } catch (e1: Exception) {
                LoggerUtil.e("未知错误Debug调试 ")
            }

            errorMsg = "未知错误，可能抛锚了吧~"
            errorCode = ErrorStatus.UNKNOWN_ERROR
        }
        return errorMsg
    }

    private fun convertStatusCode(httpException: HttpException): String? {
        return when {
            httpException.code() == 500 -> {
                "服务器发生错误"
            }
            httpException.code() == 404 -> {
                "请求地址不存在"
            }
            httpException.code() == 403 -> {
                "请求被服务器拒绝"
            }
            httpException.code() == 307 -> {
                "请求被重定向到其他页面"
            }
            else -> {
                httpException.message()
            }
        }
    }

}
