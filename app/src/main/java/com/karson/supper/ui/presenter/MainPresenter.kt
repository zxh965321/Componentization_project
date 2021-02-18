package com.karson.supper.ui.presenter

import com.karson.lib_commen.net.bean.BaseDataBean
import com.karson.lib_commen.net.rx.ErrorHandleSubscriber
import com.karson.supper.net.Network.network
import com.karson.supper.net.entity.HomeBean
import com.karson.supper.ui.constract.MainConstract

/**
 * @Author karson
 * @Date 2021/2/3-18:45
 * @desc
 */
class MainPresenter: MainConstract.Presenter() {
    override fun getNetData() {
        var map = mutableMapOf<Any,Any>()
        network.getHomeBannerList(map = map).compose(applyNoLoading()).`as`(bindLifecycle())
            .subscribe(object:ErrorHandleSubscriber<BaseDataBean<HomeBean>>(){
                override fun onNext(t: BaseDataBean<HomeBean>) {
                    //TODO 返回数据设置
                }

            })
    }
}