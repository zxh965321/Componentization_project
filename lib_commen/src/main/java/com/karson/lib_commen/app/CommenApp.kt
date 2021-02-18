package com.karson.lib_commen.app

import android.app.Application
import android.content.Context
import com.karson.lib_commen.R
import com.karson.lib_commen.ui.wight.dialog.DialogHelper
import com.karson.lib_commen.util.ToastUtil
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader

/**
 * @Author karson
 * @Date 2020/11/4-14:34
 * @desc 上下文对象获取
 */
object CommenApp {
    lateinit var app: Application

    fun init(app: Application) {
        CommenApp.app = app
        ToastUtil.init(app)
        app.registerActivityLifecycleCallbacks(ImmersionBarLifeCylce())
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.color_theme, android.R.color.white);//全局设置主题颜色
            ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f);
        };
    }

    fun getAppContext(): Application = app?.let { it }
    fun getContext(): Context = app?.let { it.applicationContext }
}