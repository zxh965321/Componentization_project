package com.karson.lib_commen.ui.onclick

import com.karson.lib_commen.net.bean.VersionBean

/**
 * @Author karson
 * @Date 2021/1/6-17:22
 * @desc 请求更新接口
 */
interface UpdataInterface {
    fun queryVersionInfoComplete(vararg params:String):VersionBean
}