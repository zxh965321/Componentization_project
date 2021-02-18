package com.karson.lib_commen.net

import android.app.Application
import com.google.gson.Gson
import com.karson.lib_commen.net.gsonconvert.FixGsonConverterFactory
import com.karson.lib_commen.net.livedatafactory.LiveDataCallAdapterFactory
import com.karson.lib_commen.net.rxjavafactory.RxCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates


/**
 *
 * 通讯管理类
 * 目标：为了将业务逻辑逐渐拆解出来
 * app网络通讯规范化
 * @author chentong
 *
 * https://bytes.babbel.com/en/articles/2016-03-16-retrofit2-rxjava-error-handling.html
 *
 */
object NetWorkManage {

    var instance: Application by Delegates.notNull()
    var chuckInterceptor: ChuckInterceptor by Delegates.notNull()
    var baseUrl: String by Delegates.notNull()

    var isRelease: Boolean = false

    lateinit var okHttpClient: OkHttpClient

    fun init(app: Application, baseUrl: String, isRelease: Boolean) {
        instance = app
        chuckInterceptor = ChuckInterceptor(instance)
        this.baseUrl = baseUrl
        this.isRelease = isRelease
    }

    fun initOkHttpClient(client: OkHttpClient) {
        okHttpClient = client
    }

    //日志打印
    fun getLogging(): HttpLoggingInterceptor {

        var logger = HttpLoggingInterceptor.Logger { message ->
            Platform.get().log(message, Platform.WARN, null)
        }

        val logInterceptor = HttpLoggingInterceptor(logger)
        if (isRelease) {
            logInterceptor.level = HttpLoggingInterceptor.Level.NONE
        } else {
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return logInterceptor
    }

    fun <T> getRetrofit(baseUrl: String, clazz: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build().create(clazz) as T
    }

    fun <T> getLiveRetrofit(baseUrl: String, clazz: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(FixGsonConverterFactory(Gson()))
            .client(okHttpClient)
            .build().create(clazz) as T
    }

    fun <T> getRxRetrofit(baseUrl: String, clazz: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxCallAdapterFactory())
//                .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(FixGsonConverterFactory(Gson()))
            .client(okHttpClient)
            .build().create(clazz) as T
    }

}
