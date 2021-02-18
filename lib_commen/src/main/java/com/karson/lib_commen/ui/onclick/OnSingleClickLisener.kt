package com.karson.lib_commen.ui.onclick

import android.view.View
import com.karson.lib_commen.util.ToastUtil


/**
 * @Author karson
 * @Date 2020/11/4-14:19
 * @desc 全局短时间多次点击过滤 全局点击事件都需要使用此函数实现
 */
open abstract class OnSingleClickLisener : View.OnClickListener {

    private var lastTime: Long = 0
    private val LIMIT_TIME: Long = 800

    abstract fun onSingleClickLisener(v: View?,vararg params:String?)

    override fun onClick(v: View?) {
        if (lastTime == null){
            lastTime = System.currentTimeMillis()
            onSingleClickLisener(v)
        }else{
            if ((System.currentTimeMillis() - lastTime) > LIMIT_TIME) {
                lastTime = System.currentTimeMillis()
                onSingleClickLisener(v)
            }
        }

    }
}