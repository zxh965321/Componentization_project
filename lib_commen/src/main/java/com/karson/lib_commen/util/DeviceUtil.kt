package com.karson.lib_commen.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.core.app.ActivityCompat


object DeviceUtil {

    private var umengChannel = ""

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String {
        return ""
    }

    //校验是否含有权限
    fun checkSelfPermission(context: Context, permission: String): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true

        }
        return false
    }

    @SuppressLint("HardwareIds")
    fun getAndroidId(context: Context): String? {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    //获得渠道
    fun getUmengChannel(context: Context): String {
        if (umengChannel.isNotEmpty()) return umengChannel
        umengChannel = getMetaValue(context, "UMENG_CHANNEL")
        return umengChannel
    }

    fun getMetaValue(context: Context, name: String): String {
        val info: ApplicationInfo
        var value: String
        try {
            info = context.getPackageManager()
                .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
            value = info.metaData.getString(name) ?: "hetai"
        } catch (e: PackageManager.NameNotFoundException) {
            return "hetai"
        }
        return value
    }

    fun getVersionName(mContext: Context): String {
        try {
            return mContext.packageManager
                .getPackageInfo(mContext.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }

}