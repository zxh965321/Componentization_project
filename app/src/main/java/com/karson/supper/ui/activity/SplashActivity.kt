package com.karson.supper.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.karson.lib_commen.ui.activity.BaseActivity
import com.karson.supper.R

/**
 * @Author karson
 * @Date 2021/1/6-09:51
 * @desc 闪屏页面
 */
class SplashActivity : BaseActivity() {
    override fun startSelf(ctx: Context, vararg parms: String) {
        var intent = Intent(ctx, SplashActivity::class.java)
        ctx.startActivity(intent)
    }

    override fun setLayoutRes(): Int = R.layout.activity_splash;

    override fun initViews() {
//        TODO("Not yet implemented")
    }

    override fun initData(savedInstanceState: Bundle?) {
//        TODO("Not yet implemented")
    }
}