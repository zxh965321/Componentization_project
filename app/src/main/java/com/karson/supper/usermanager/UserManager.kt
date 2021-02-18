package com.karson.supper.usermanager

/**
 * @Author karson
 * @Date 2020/12/15-18:02
 * @desc 用户管理类
 */
object UserManager {
    fun login(token: String, mobile: String, shareCode: String,isBindWxChart:Boolean,isBindMobile:Boolean) {
        Constant.spToken = token
        Constant.spUserMobile = mobile
        Constant.spBindWechat = isBindWxChart
        Constant.spBindMobile = isBindMobile
    }

    fun isLogin() = Constant.spUserMobile.isNotBlank()

    fun isNotLogin() = !isLogin()

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