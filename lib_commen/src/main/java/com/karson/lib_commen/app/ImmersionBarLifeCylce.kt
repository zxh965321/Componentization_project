package com.karson.lib_commen.app

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.gyf.immersionbar.ImmersionBar
import com.karson.lib_commen.R
import com.karson.lib_commen.ui.activity.BaseActivity
import com.karson.lib_commen.ui.onclick.OnSingleClick
import com.karson.lib_commen.ui.onclick.SingleClickImpl
import com.karson.lib_commen.util.ToastUtil

/**
 * @Author karson
 * @Date 2020/4/13-下午3:13
 * @desc 沉浸式开发
 */
class ImmersionBarLifeCylce : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity) {
        Log.i("ImmersionBarLifeCylce:${activity}", "-onActivityPaused")
    }

    override fun onActivityStarted(activity: Activity) {
        Log.i("ImmersionBarLifeCylce:${activity}", "-onActivityStarted")

        if (!activity.intent.getBooleanExtra("isInitToolbar", false)) {
            //由于加强框架的兼容性,故将 setContentView 放到 onActivityCreated 之后,onActivityStarted 之前执行
            //而 findViewById 必须在 Activity setContentView() 后才有效,所以将以下代码从之前的 onActivityCreated 中移动到 onActivityStarted 中执行
            activity.intent.putExtra("isInitToolbar", true);
            //这里全局给Activity设置toolbar和title,你想象力有多丰富,这里就有多强大,以前放到BaseActivity的操作都可以放到这里
            if (activity is BaseActivity) {
                var toolbar: Toolbar? = activity?.findViewById<Toolbar>(R.id.toolbar)
                toolbar?.let { toolbar ->
                    when {
                        activity is AppCompatActivity -> {
                            activity.setSupportActionBar(toolbar)
                            activity.supportActionBar?.setDisplayShowTitleEnabled(false)
                        }
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                            activity.setActionBar(activity.findViewById(R.id.toolbar));
                            activity.actionBar?.setDisplayShowTitleEnabled(false);
                        }
                        else -> null
                    }
                    var view = View(activity)
                    view.layoutParams =
                        ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
                    view.background = toolbar.background
                    ImmersionBar.with(activity)
                        .statusBarView(view)
                        .statusBarDarkFont(true, 0.2f)
                        .titleBar(toolbar)
                        .keyboardEnable(true)
                        .init()


                }
            }


            if (activity.findViewById<ImageView>(R.id.baseBack) != null) {
                activity.run {
                    findViewById<ImageView>(R.id.baseBack)
                        .setOnClickListener(SingleClickImpl(object : OnSingleClick {
                            override fun onSinglC(view: View?, params: Array<out String?>) {
                                activity.onBackPressed()
                            }
                        }))
                }
            }
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.i("ImmersionBarLifeCylce${activity.toString()}", "-onActivityDestroyed")
//        ImmersionBar.with(activity).destroy() //必须调用该方法，防止内存泄漏

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.i("ImmersionBarLifeCylce${activity.toString()}", "-onActivitySaveInstanceState")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.i("ImmersionBarLifeCylce${activity.toString()}", "-onActivityStopped")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.i("ImmersionBarLifeCylce${activity.toString()}", "-onActivityCreated")
    }

    override fun onActivityResumed(activity: Activity) {
        Log.i("ImmersionBarLifeCylce${activity.toString()}", "-onActivityResumed")
    }
}
