package com.karson.lib_commen.net.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @Author karson
 * @Date 2021/1/6-17:13
 * @desc 版本更新实体类
 */
data class VersionBean(
    val downLoadUrl: String,
    val versionName: String,
    val versionPermission: String,
    val versionUpdataInfo: String

) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString().toString(),
        source.readString().toString(),
        source.readString().toString(),
        source.readString().toString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(downLoadUrl)
        writeString(versionName)
        writeString(versionPermission)
        writeString(versionUpdataInfo)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<VersionBean> = object : Parcelable.Creator<VersionBean> {
            override fun createFromParcel(source: Parcel): VersionBean = VersionBean(source)
            override fun newArray(size: Int): Array<VersionBean?> = arrayOfNulls(size)
        }
    }
}