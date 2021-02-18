package com.karson.supper.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.karson.lib_commen.ui.activity.BaseActivity
import com.karson.supper.R
import com.karson.supper.ui.constract.MainConstract
import com.karson.supper.ui.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主页
 */
class MainActivity : BaseActivity() ,MainConstract.View{

private lateinit var presenter : MainPresenter
    /**
     * 开启主页
     */
    override fun startSelf(ctx: Context, vararg parms: String) {
        var intent: Intent = Intent(ctx, MainActivity::class.java)
        ctx.startActivity(intent)
    }

    override fun setLayoutRes(): Int = R.layout.activity_main


    override fun initViews() {
//        TODO("Not yet implemented")
        presenter = MainPresenter()
        presenter.attach(this,this)
        titleLayout.setBackgroundResource(R.drawable.galident_title)
        presenter.showProgressDialog()
    }

    override fun initData(savedInstanceState: Bundle?) {
//        TODO("Not yet implemented")
    }

    override fun updataUi() {

    }

    override fun showProgressDialog() {
        presenter.showProgressDialog()
    }

    override fun hideProgressDialog() {
        presenter.hideProgressDialog()
    }

}