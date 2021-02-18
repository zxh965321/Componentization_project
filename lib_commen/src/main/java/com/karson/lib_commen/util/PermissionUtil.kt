package com.karson.lib_commen.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat


/**
 * Created by Lzy on 2020/8/10.
 */
class PermissionUtil {

    /**
     * 返回未授予的权限数组
     */
    fun unGranted(
        context: Context,
        permissions: Array<String>
    ): Array<String>? {

        val unGrantedList = ArrayList<String>()
        for (i in permissions.indices) {
            val permission = permissions[i]
            if (!isAccept(context, permission)) {
                unGrantedList.add(permission)
            }
        }
        return if (unGrantedList.size > 0) {
            unGrantedList.toTypedArray()
        } else {
            null
        }
    }


    /**
     * 权限是否已经获取
     */
    fun isAccept(context: Context, permission: String): Boolean {
        return (ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED)
    }


    /**
     * 是否需要动态请求权限
     */
    fun isNeedCheck(context: Context): Boolean {
        val targetSdkVersion: Int = context.getApplicationInfo().targetSdkVersion
        val sdkVersionM = Build.VERSION_CODES.M
        return Build.VERSION.SDK_INT >= sdkVersionM && targetSdkVersion >= sdkVersionM
    }

    /**
     * 通知权限申请
     * 跳到通知栏设置界面
     * @param context
     */
    fun requestNotify(context: Context) {
        val localIntent = Intent()
        ///< 直接跳转到应用通知设置的代码
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            localIntent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            localIntent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
            Build.VERSION.SDK_INT < Build.VERSION_CODES.O
        ) {
            localIntent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            localIntent.putExtra("app_package", context.packageName)
            localIntent.putExtra("app_uid", context.applicationInfo.uid)
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            localIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            localIntent.addCategory(Intent.CATEGORY_DEFAULT)
            localIntent.data = Uri.parse("package:" + context.packageName)
        } else {
            ///< 4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                localIntent.setData(Uri.fromParts("package", context.packageName, null))
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.action = Intent.ACTION_VIEW
                localIntent.setClassName(
                    "com.android.settings",
                    "com.android.setting.InstalledAppDetails"
                )
                localIntent.putExtra(
                    "com.android.settings.ApplicationPkgName",
                    context.packageName
                )
            }
        }
        context.startActivity(localIntent)
    }
}