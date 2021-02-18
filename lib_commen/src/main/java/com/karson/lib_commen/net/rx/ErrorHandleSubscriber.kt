package com.karson.lib_commen.net.rx;

import com.karson.lib_commen.net.exception.ExceptionHandle
import com.karson.lib_commen.util.toast
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * rx异常处理
 */
 abstract class ErrorHandleSubscriber<T> : Observer<T> {

    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        ExceptionHandle.handleException(e).toast()
    }
}