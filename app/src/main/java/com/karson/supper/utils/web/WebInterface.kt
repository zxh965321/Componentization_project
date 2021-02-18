package com.karson.supper.utils.web

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import com.karson.supper.usermanager.Constant

/**
 * Created by Lzy on 2020/8/11.
 */
class WebInterface() {

    private var mUserInfo = ""
    private var mShopInfo = ""
    private var mContext: Context? = null

    @JavascriptInterface
    fun uploadUser(): String {
        Log.d("WebInterface", "uploadUser: " + mUserInfo)

        return mUserInfo
    }

    @JavascriptInterface
    fun uploadShop(): String {
        Log.d("WebInterface", "uploadShop: " + mShopInfo)
        return mShopInfo
    }

    fun getAndroidToken(): String {
        val token = Constant.spToken
        Log.d("WebInterface", "getAndroidToken: " + token)
        return token
    }

    fun payResult(status: String) {
//        mContext?.let { WebPayStatusActivity.startWebPayStatusActivity(it, status) }
    }

    fun setInfo(userInfo: String, shopInfo: String) {
        this.mUserInfo = userInfo
        this.mShopInfo = shopInfo
    }

    fun setContext(context: Context) {
        this.mContext = context
    }
}