package com.karson.supper.utils.web

import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView

/**
 * Created by Lzy on 2020/8/11.
 */
open class WebChromeClientImpl(iWebTitleCallback: IWebTitleCallback?) : WebChromeClient() {

    private val mIWebTitleCallback = iWebTitleCallback
    private var mIWebProgressBar: IWebProgressCallback? = null

    //接收网页加载进度
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        mIWebProgressBar?.onWebProgress(newProgress)
        Log.d("TAG", "onProgressChanged: " + newProgress)
    }

    //接收网页Title
    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        mIWebTitleCallback?.onWebTitle(title)
    }

    fun setWebProgressCallBack(iWebProgressCallback: IWebProgressCallback?) {
        this.mIWebProgressBar = iWebProgressCallback
    }

}