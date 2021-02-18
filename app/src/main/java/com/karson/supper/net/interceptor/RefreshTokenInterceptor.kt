package com.karson.supper.net.interceptor

import android.content.Intent
import com.karson.supper.usermanager.UserManager
import java.io.IOException
import kotlin.jvm.Throws
import okhttp3.Interceptor
import okhttp3.Response

class RefreshTokenInterceptor : Interceptor {

    val LOGOUT_CODE = "10001"
    val REFRESH_CODE = "10022"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val code = response.header("code")
        if (LOGOUT_CODE.equals(code)) {
            //退出登录
            logout()
        } else if (REFRESH_CODE.equals(code)) {
            //刷新token
            refreshToken()
        }
        return response
    }

    //刷新
    private fun refreshToken() {

    }

    //退出登录
    private fun logout() {
        UserManager.logout()
        var intent =  Intent()
        intent.putExtra("showBack",true)
    }

}