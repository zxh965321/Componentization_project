package com.karson.lib_commen.app

import com.karson.lib_commen.util.Preference

/**
 * @Author karson
 * @Date 2021/1/6-17:35
 * @desc 常量类
 */
object Constant {

    //腾讯 Bugly APP id
    val BUGLY_APPID = ""

    //sp 存储的文件名

    /*********** 公共请求头head  */
    const val HEAD_PLATFORM = "platform"
    const val HEAD_DOWNLOAD_CHANNEL = "downloadChannel"
    const val HEAD_VERSIONNAME = "versionName"
    const val HEAD_TOKEN = "appletToken"
    const val HEAD_JPUSHTOKEN = "jpushToken"
    const val HEAD_DEVICE_ID = "deviceId"
    const val HEAD_DEVICE_TYPE = "deviceType"
    const val HEAD_OSVERSION = "osVersion"
    const val HEAD_OSBRANCH = "osBranch"
    const val HEAD_ANDROIDID = "androidId"

    /*********** 用户体系  */ //用户id
    const val USER_ID = "userId"

    //token
    const val TOKEN = "appletToken"

    //refresh token
    const val REFRESH_TOKEN = "refresh_token"

    //用户信息
    const val USER_INFO = "user_info"

    //用户手机号
    const val USER_MOBILE = "user_mobile"

    //引导页
    const val IS_FIRST_GUIDE = "is_first_guide"

    //绑定微信
    const val IS_BIND_WECHAT = "is_bind_wechat"

    //绑定手机号
    const val IS_BIND_MOBILE = "is_bind_mobile"

    //首次登陆
    const val WEDDINGNIGHT = "wedding_night"

    //sp存储
    var spUserId by Preference(CommenApp.getContext(), USER_ID, "")
    var spToken by Preference(CommenApp.getContext(), TOKEN, "")
    var spRefreshToken by Preference(CommenApp.getContext(), REFRESH_TOKEN, "")
    var spUserInfo by Preference(CommenApp.getContext(), USER_INFO, "")
    var spUserMobile by Preference(CommenApp.getContext(), USER_MOBILE, "")
    var spFristGuide by Preference(CommenApp.getContext(), IS_FIRST_GUIDE, true)
    var spBindWechat by Preference(CommenApp.getContext(), IS_BIND_WECHAT, false)
    var spBindMobile by Preference(CommenApp.getContext(), IS_BIND_MOBILE, false)
    var spWeddingNight by Preference(CommenApp.getContext(), WEDDINGNIGHT, true)

}