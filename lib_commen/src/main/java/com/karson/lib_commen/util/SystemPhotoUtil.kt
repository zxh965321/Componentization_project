package com.karson.lib_commen.util

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.karson.lib_commen.R
import com.karson.lib_commen.ui.wight.dialog.CustomDailog

import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
//import com.yuyh.library.imgsel.ISNav
//import com.yuyh.library.imgsel.config.ISCameraConfig
//import com.yuyh.library.imgsel.config.ISListConfig

/**
 * @Author karson
 * @Date 2020/6/30-下午4:58
 * @desc
 */
class SystemPhotoUtil {
   /* companion object{
        class Builder{
             val REQUEST_CAMERA_CODE = 1
             val REQUEST_LIST_CODE = 0
            private var takePhotoDialog : CustomDailog? = null
            private lateinit var view: View
            private lateinit var context: Context
            constructor(context: Context){
                this.context = context
                ISNav.getInstance()
                    .init { context, path, imageView ->
                        Glide.with(context).load(path).into(imageView)
                    }
                view = LayoutInflater.from(context).inflate(R.layout.item_dialog_takephoto,null,false)
                view.findViewById<TextView>(R.id.dialogTakePhotoCamera).setOnClickListener {
                    //照相
                    takePhoto(context)
                    takePhotoDialog?.dismiss()
                }
                view.findViewById<TextView>(R.id.dialogTakePhotoLauncher).setOnClickListener {
                    //相册
                    pickPhoto(context = context, mutiSelect = false, needCamera = false)
                    takePhotoDialog?.dismiss()
                }
                 view.findViewById<TextView>(R.id.dialogDissmiss).setOnClickListener {
                    //相册
                    takePhotoDialog?.dismiss()
                }
                takePhotoDialog = CustomDailog.Companion.Builder(context).setIsCancleOutside(true).setIsBackGrund(true).setContentView(view).setShowPosition(
                    Gravity.BOTTOM).build()

            }

            fun start(){
                AndPermission.with(context)
                    .runtime()
                    .permission(Permission.Group.CAMERA, Permission.Group.STORAGE)
                    .onGranted {
                        if (takePhotoDialog!=null&&!takePhotoDialog!!.isShowing)
                            takePhotoDialog!!.show()
                    }.onDenied {

                    }.start()
            }

            *//**
             * 拍照
             *//*
            private fun takePhoto(context: Context) {
                val config: ISCameraConfig = ISCameraConfig.Builder()
                    .needCrop(false)
                    .build()

                ISNav.getInstance().toCameraActivity(
                    context,
                    config,
                    REQUEST_CAMERA_CODE
                )
            }

            *//**
             * 从相册选择
             *//*
            private fun pickPhoto(context: Context,mutiSelect:Boolean,needCamera:Boolean) {
                val config = ISListConfig.Builder()
                    .multiSelect(mutiSelect)// 是否多选
                    .btnTextColor(Color.WHITE) // 确定按钮文字颜色
                    .statusBarColor(Color.parseColor("#FFBF52")) // 使用沉浸式状态栏
                    .backResId(R.mipmap.ic_back)// 返回图标ResId
                    .title("相册")
                    .titleColor(Color.WHITE)
                    .titleBgColor(Color.parseColor("#FFBF52"))
                    .allImagesText("所有照片")
                    .needCrop(false)
                    .needCamera(needCamera) // 第一个是否显示相机
                    .build()

                ISNav.getInstance().toListActivity(
                    context,
                    config,
                    REQUEST_LIST_CODE
                )
            }
        }

    }*/
}