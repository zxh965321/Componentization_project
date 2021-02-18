package com.karson.lib_commen.ui.wight.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.karson.lib_commen.R
import com.karson.lib_commen.app.CommenApp

/**
 * @Author karson
 * @Date 2021/1/29-15:27
 * @desc 弹窗的帮助类
 */
object DialogHelper {

    fun getProGressDialog( ctx: Context): Dialog {
        var view = LayoutInflater.from(ctx).inflate(R.layout.layout_dialog_progress, null, false);
        var dialog = CustomDailog.getBuilder(ctx).setContentView(view).setIsBackGrund(true)
            .setIsCancleOutside(true).build()
        return dialog;
    }
}