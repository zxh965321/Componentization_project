package com.karson.supper.ui.constract

import com.karson.lib_commen.ui.mvp.BasePresenter
import com.karson.lib_commen.ui.mvp.IBaseView

/**
 * @Author karson
 * @Date 2021/2/3-18:07
 * @desc
 */
interface MainConstract {

    interface View : IBaseView{
        abstract fun updataUi()
    }
    abstract class Presenter : BasePresenter<View>() {
        abstract fun getNetData()
    }
}