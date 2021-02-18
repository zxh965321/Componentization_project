package com.karson.lib_commen.util

import android.widget.TextView

/**
 * @Author karson
 * @Date 2020/4/23-下午5:49
 * @desc
 */
object CheckViewValueNotNullUtil {

    fun checkViewsNotNull(mutableList: MutableList<TextView>): Boolean {
        mutableList.forEach { textView ->
            if (textView.text == null ||textView.text.equals(""))return true

        }
        return false
    }
}