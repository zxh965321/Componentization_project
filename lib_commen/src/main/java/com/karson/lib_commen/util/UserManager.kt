package com.karson.lib_commen.util

import com.karson.lib_commen.app.Constant
/**
 * @Author karson
 * @Date 2021/1/6-17:31
 * @desc 项目基础信息管理
 */
object UserManager {

    fun login(token: String, mobile: String, isBindWxChart:Boolean,isBindMobile:Boolean) {
        Constant.spToken = token
        Constant.spUserMobile = mobile
        Constant.spBindWechat = isBindWxChart
        Constant.spBindMobile = isBindMobile
    }

    fun isLogin() = Constant.spUserMobile.isNotBlank()
    fun isBindWeChat() = Constant.spBindWechat
    fun isBindMobile() = Constant.spBindMobile
    fun bindWechat(){
        Constant.spBindWechat = true
    }
    fun bindMobile(mobile: String){
        Constant.spUserMobile = mobile
        Constant.spBindMobile = true
    }
    fun logout() {
        Constant.spToken = ""
        Constant.spUserMobile = ""
        Constant.spBindWechat = false
        Constant.spBindMobile = false
    }

}