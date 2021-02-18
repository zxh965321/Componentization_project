package com.karson.lib_commen.ui.onclick

import android.view.View

/**
 * @Author karson
 * @Date 2021/2/3-17:01
 * @desc
 */
class SingleClickImpl : OnSingleClickLisener {
    private lateinit var click: OnSingleClick

    constructor(click: OnSingleClick) : super(){
        this.click = click
    }

    override fun onSingleClickLisener(v: View?,vararg params:String?) {

        click.onSinglC(v, *params)
    }


}