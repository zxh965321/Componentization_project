package com.karson.lib_commen.util

import android.content.Context
import android.view.Gravity
import android.widget.Toast

/**
 * @Author karson
 * @Date 2020/11/4-14:54
 * @desc 吐司工具类
 */
object ToastUtil {
    lateinit var mContext: Context
    fun init(context: Context) {
        mContext = context
    }

    /**
     * 短时（2秒）吐司
     */
    fun shorShow(msg: String) {
        show(msg,Gravity.BOTTOM,Toast.LENGTH_SHORT)
    }

    /**
     * 短时（2秒）吐司
     */
    fun shorCenterShow(msg: String) {
        show(msg,Gravity.CENTER,Toast.LENGTH_SHORT)
    }


    /**
     * 长时吐司
     */
    fun longShow(msg: String) {
        show(msg,Gravity.BOTTOM,Toast.LENGTH_LONG)
    }

    fun show(msg: String, position: Int,durition :Int) {

        var toast = Toast.makeText(mContext,msg,durition)
        toast?.let { it ->
            it.setGravity(position, 0, 0)
            toast.show()
        }
    }
}