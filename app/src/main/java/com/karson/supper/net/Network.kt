package com.karson.supper.net

import android.app.Application
import android.os.Build
import com.karson.supper.BuildConfig
import com.karson.lib_commen.app.CommenApp
import com.karson.lib_commen.net.NetWorkManage
import com.karson.lib_commen.net.NetWorkManage.getRetrofit
import com.karson.lib_commen.net.NetWorkManage.initOkHttpClient
import com.karson.lib_commen.net.interceptor.HttpBaseParamsInterceptor
import com.karson.lib_commen.util.DeviceUtil
import com.karson.supper.net.api.LiveDataApi
import com.karson.supper.net.api.NetWorkApi
import com.karson.supper.net.interceptor.RefreshTokenInterceptor
import com.karson.supper.usermanager.Constant
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


object Network {

    lateinit var liveNetwork: LiveDataApi;
    lateinit var network: NetWorkApi;

    private val client:OkHttpClient = getClient()
    fun getClient():OkHttpClient =  OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(getBaseParams())
        .addInterceptor(RefreshTokenInterceptor())
//        .addInterceptor(NetWorkManage.chuckInterceptor)
//            .addInterceptor(NetWorkManage.getLogging())
        .build()
    /**
     * 设置http公共参数
     * head query param
     * @return
     */
    private fun getBaseParams(): HttpBaseParamsInterceptor {
        val interceptor = HttpBaseParamsInterceptor()
        interceptor.setUpdateHandler {
            val urlPatternList: MutableList<String?> = ArrayList()
            urlPatternList.add(NetWorkManage.baseUrl)
            val builder = HttpBaseParamsInterceptor.Builder()
                .addHeaderParam(Constant.HEAD_TOKEN, Constant.spToken)
                .addHeaderParam(Constant.HEAD_PLATFORM, "HETAI")
                .addHeaderParam(
                    Constant.HEAD_DOWNLOAD_CHANNEL,
                    DeviceUtil.getUmengChannel(CommenApp.getContext())
                )
                .addHeaderParam(Constant.HEAD_VERSIONNAME, BuildConfig.VERSION_NAME)
                .addHeaderParam(
                    Constant.HEAD_DEVICE_ID,
                    DeviceUtil.getDeviceId(CommenApp.getContext())
                )
                .addHeaderParam(Constant.HEAD_DEVICE_TYPE, "android")
                .addHeaderParam(Constant.HEAD_OSVERSION, Build.VERSION.RELEASE)
                .addHeaderParam(Constant.HEAD_OSBRANCH, Build.BRAND + " " + Build.MODEL)
                .addHeaderParam(
                    Constant.HEAD_ANDROIDID,
                    DeviceUtil.getAndroidId(CommenApp.getContext())
                )
                .addUrlPatternList(urlPatternList) //给匹配url地址设置公共参数
            builder
        }
        return interceptor
    }

    //网络初始化
    fun init(app: Application, baseUrl: String, isRelease: Boolean) {
        NetWorkManage.init(app, baseUrl, isRelease)
        initOkHttpClient(client)
        initNetwork(baseUrl)

        //异常统一处理
//        RxJavaPlugins.setErrorHandler {e->
//            e.printStackTrace()
//            ExceptionHandle.handleException(e).toast()
//        }

    }

    //网络加载
    fun initNetwork(baseUrl: String) {
//        liveNetwork = getLiveRetrofit(baseUrl, LiveDataApi::class.java)//初始化数据库
        network = getRetrofit(baseUrl, NetWorkApi::class.java)
    }

}