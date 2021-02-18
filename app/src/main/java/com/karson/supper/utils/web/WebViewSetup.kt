package com.karson.supper.utils.web

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView

/**
 * Created by Lzy on 2020/8/11.
 */
class WebViewSetup {

    @SuppressLint("SetJavaScriptEnabled")
    fun initWebViewSetup(webView: WebView?) : WebView?{
        webView?.clearCache(true)
        webView?.clearHistory()
        //初始化WebSettings
        val settings = webView?.settings
        settings?.javaScriptEnabled = true
        //隐藏缩放控件
        settings?.builtInZoomControls = false
        settings?.displayZoomControls = true
        //禁止缩放
        settings?.setSupportZoom(true)
        //文件权限
        settings?.allowFileAccess = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings?.allowFileAccessFromFileURLs = true
            settings?.allowUniversalAccessFromFileURLs = true
        }
        settings?.allowContentAccess = true

        //缓存相关
        settings?.setAppCacheEnabled(false)
        settings?.domStorageEnabled = false
        settings?.databaseEnabled = false
        settings?.cacheMode = WebSettings.LOAD_NO_CACHE

        //自适应屏幕
        settings?.loadWithOverviewMode = true
        settings?.useWideViewPort = true
        return webView
    }
}