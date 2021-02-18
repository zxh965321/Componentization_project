package com.karson.lib_commen.ui.mvp

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * @Author karson
 * @Date 2020/11/19-16:49
 * @desc mvp 模式下presenter顶级接口
 */
interface IPresenter<VIEW: IBaseView> : LifecycleObserver {

    fun attach(view: VIEW, context: Context)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory()

}