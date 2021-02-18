package com.karson.lib_commen.ui.activity

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewStub
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.karson.lib_commen.R
import com.karson.lib_commen.app.BuildConfig
import com.karson.lib_commen.util.KeyBoardUtil
import com.karson.lib_commen.util.ToastUtil

/**
 * @Author karson
 * @Date 2020/11/4-14:08
 * @desc activity 基类
 * 规则 ：进入页面需要等待窗，无需耗时在initdate中hide掉
 */
open abstract class BaseActivity : AppCompatActivity() {

    private var lastTime: Long = 0
    private val LIMIT_TIME: Long = 800

    lateinit var baseTitle: Toolbar
    protected lateinit var titleLayout: RelativeLayout
    private lateinit var baseContent: ViewStub
    private lateinit var baseEmpty: ViewStub
    lateinit var progressDialog: Dialog
    lateinit var title: TextView
    lateinit var ivBack: ImageView
    lateinit var ivFunction: ImageView
    lateinit var tvFunction: TextView

    companion object {
        const val is_application = BuildConfig.is_application
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("小猫一两只")
        setContentView(R.layout.layout_base)
        baseTitle = findViewById(R.id.toolbar)
        titleLayout = findViewById(R.id.titleLayout)
        baseContent = findViewById(R.id.baseContent)
        baseEmpty = findViewById(R.id.baseEmpty)
        //设置基础头部控件获取，子类按需实现其点击事件
        title = findViewById(R.id.title)
        ivBack = findViewById(R.id.baseBack)
        ivFunction = findViewById(R.id.baseFunctionIv)
        tvFunction = findViewById(R.id.baseFunctionTv)
        if (setLayoutRes() != 0) {
            baseContent.layoutResource = setLayoutRes()
            baseContent.visibility = View.VISIBLE
            baseEmpty.visibility = View.GONE
        } else{
            baseContent.visibility = View.GONE
            baseEmpty.visibility = View.VISIBLE
        }
        initViews()
        initData(savedInstanceState)
    }

    open abstract fun startSelf(ctx: Context, vararg parms: String)

    /**设置布局*/
    abstract fun setLayoutRes(): Int

    /**初始化views*/
    abstract fun initViews()

    /**初始化数据*/
    abstract fun initData(savedInstanceState: Bundle?)

    /**显示空布局*/
    fun showEmptyLayout() {
        baseEmpty.visibility = View.VISIBLE
        baseContent.visibility = View.GONE
    }

    /**显示数据布局*/
    fun showContent() {
        baseContent.visibility = View.VISIBLE
        baseEmpty.visibility = View.GONE
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev!!.action === MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (KeyBoardUtil.isShouldHideKeyboard(v, ev!!)) {
                KeyBoardUtil.hideKeyboard(this, v?.windowToken)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onBackPressed() {
        if (lastTime == null){
            lastTime = System.currentTimeMillis()
            super.onBackPressed()
        }else{
            if ((System.currentTimeMillis() - lastTime) > LIMIT_TIME) {
                lastTime = System.currentTimeMillis()
                super.onBackPressed()
            }
        }
    }
}