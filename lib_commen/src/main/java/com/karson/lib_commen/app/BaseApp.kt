package com.karson.lib_commen.app

import android.app.Application
import androidx.multidex.MultiDex

/**
 * @Author karson
 * @Date 2021/1/5-16:54
 * @desc
 */
open class BaseApp :Application() {
    companion object{
        const val is_application = BuildConfig.is_application;
    }
    override fun onCreate() {
        super.onCreate()
        CommenApp.init(this)
        MultiDex.install(this);
    }
}