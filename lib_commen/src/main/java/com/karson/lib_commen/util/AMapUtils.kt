package com.karson.lib_commen.util

import android.content.Context
import android.content.pm.PackageInfo
import android.util.Log

/**
 * Created by Lzy on 2020/8/17.
 */
object AMapUtils {


    /**
     * 拼接打开所需高德地图Uri
     * */
    fun getAMapUri(dLon: String, dLat: String, dName:String?): String {
        //高德地图api地址
        //https://lbs.amap.com/api/amap-mobile/guide/android/route
        val buffer = StringBuffer()

        buffer.append("amapuri://route/plan/?sourceApplication=com.hetai.shop")//高德地图api中的固定格式
//            .append("&sid=").append("")//起点POIID
//            .append("&slat=").append(sLat)//起点维度 默认为用户当前位置
//            .append("&slon=").append(sLon)//起点经度 默认为用户当前位置
//            .append("&sname=").append("我的位置")//起点名称
//            .append("&did=").append("")//终点POIID
            .append("&dlat=").append(dLat)//终点维度
            .append("&dlon=").append(dLon)//终点经度
            .append("&dname=").append(dName)//终点名称
            .append("&dev=").append("0")//
            .append("&t=").append("0")//t = 0（驾车）= 1（公交）= 2（步行）= 3（骑行）= 4（火车）= 5（长途客车）
        Log.d("TAG", "getAMapUri: ${buffer.toString()}")
        return buffer.toString()
    }

    /**
     * 检测应用是否安装
     */
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun checkMapAppsIsExist(context: Context, packageName: String): Boolean {
        var packageInfo: PackageInfo?
        try {
            packageInfo = context.packageManager.getPackageInfo(packageName, 0)
        } catch (e: Exception) {
            packageInfo = null
            e.printStackTrace()
        }
        return packageInfo != null
    }
}