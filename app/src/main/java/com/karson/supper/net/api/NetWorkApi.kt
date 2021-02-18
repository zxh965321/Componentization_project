package com.karson.supper.net.api

import com.karson.lib_commen.net.bean.BaseDataBean
import com.karson.supper.net.Urls
import com.karson.supper.net.entity.HomeBean
import java.util.*

import io.reactivex.Observable
import retrofit2.http.*

/**
 *
 * original in http://bytes.babbel.com/en/articles/2016-03-16-retrofit2-rxjava-error-handling.html
 * 异步后台api请求
 * rxjava
 */
interface NetWorkApi {

    @POST(Urls.GET_HOMEDATE)
    fun getHomeBannerList(@Body map: MutableMap<Any, Any>): Observable<BaseDataBean<HomeBean>>


}