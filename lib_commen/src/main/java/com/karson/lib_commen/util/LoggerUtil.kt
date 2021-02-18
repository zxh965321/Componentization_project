package com.karson.lib_commen.util

import android.util.Log

/**
 * @Author karson
 * @Date 2020/11/18-17:22
 * @desc 调试日志工具类
 */
object LoggerUtil {

    var isDebug:Boolean = true
    val TAG :String = "LOGGERUTIL"
    /**
     * 初始化debug不再打印日志
     */
    fun init(isDebug: Boolean) {
        this.isDebug = isDebug
    }

    fun e(msg:String){
        if (isDebug)
            Log.e(TAG,msg)
    }

    fun i(msg: String){
        if (isDebug)
            Log.i(TAG,msg)
    }

    fun d(msg: String){
        if (isDebug)
            Log.d(TAG,msg)
    }
     fun e(msg:String,tag:String){
        if (isDebug)
            Log.e(tag,msg)
    }

    fun i(msg: String,tag:String){
        if (isDebug)
            Log.i(tag,msg)
    }

    fun d(msg: String,tag:String){
        if (isDebug)
            Log.d(tag,msg)
    }


}