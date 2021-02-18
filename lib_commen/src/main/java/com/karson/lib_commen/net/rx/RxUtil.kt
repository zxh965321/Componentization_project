package com.karson.lib_commen.net.rx;

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.karson.lib_commen.ui.mvp.IBaseView
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.ObservableConverter
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author chentong
 * 惯用方法，让代买简单
 *
 * 采用livedata取代rxjava
 * livedata可以感知当前页面生命周期
 * 建议在学习lifecycle后再添加
 *
 */
object RxUtil {

    //demo
    fun test(owner: LifecycleOwner) {
        Observable.just(1, 2, 3)
            .`as`(bindLifecycle(owner))
            .subscribe()
    }

//    fun <T> subscibeUi(observable: Observable<T>,view: IBaseView, owner: LifecycleOwner, consumer: ErrorHandleSubscriber<T>) {
//        observable.compose(applySchedulers())
//            .compose(applyRetry())
//            .compose(applyLoading(view))
//            .`as`(bindLifecycle(owner))
//            .subscribe(consumer)
//    }
//
//    fun <T> subscibeUiNoLoading(observable: Observable<T>, owner: LifecycleOwner,  consumer: ErrorHandleSubscriber<T>) {
//        observable.compose(applySchedulers())
//            .compose(applyRetry())
//            .`as`(bindLifecycle(owner))
//            .subscribe(consumer)
//    }

    /**
     * 线程切换处理
     */
    fun <T> apply(baseView: IBaseView, owner: LifecycleOwner): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.compose(applySchedulers())
                .compose(applyLoading(baseView))
                .compose(applyRetry())
//                .compose (applyLifecycle(owner))
        };
    }

    fun <T> applyNoLoading(owner: LifecycleOwner): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.compose(applySchedulers())
                .compose(applyRetry())
//                .compose (applyLifecycle(owner))
        };
    }

    fun <T> applyTrans(view: IBaseView, owner: LifecycleOwner): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.compose(applySchedulers())
                .compose(applyLoading(view))
//                .compose (applyLifecycle(owner))
        };
    }

    /**
     * AutoDispose 自动解绑
     */
    fun <T> bindLifecycle(owner: LifecycleOwner): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner))
    }

    fun <T> bindLifeDestory(owner: LifecycleOwner): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY))
    }

    // lifecycle
    fun <T> applyLifecycle(owner: LifecycleOwner): ObservableTransformer<T, T>? {
        return ObservableTransformer { observable ->
            observable.`as`(bindLifeDestory<T>(owner) as ObservableConverter<T, out ObservableSubscribeProxy<T>?>) as Observable<T>
        }
    }

    /**
     * 线程切换处理
     */
    fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        }
    }

    // retry
    fun <T> applyRetry(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.compose(applySchedulers())
                .retryWhen(
                    RetryWithDelay(
                        RetryWithDelay.REQUEST_MAXRETRIES,
                        RetryWithDelay.REQUEST_RETRYDELAYSECOND
                    )
                )
        };
    }

    /**
     * loading
     */
    fun <T> applyLoading(view: IBaseView): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.doOnSubscribe { disposable ->
                    view.showProgressDialog()
                }
                .doFinally {
                    view.hideProgressDialog()
                }
        };
    }

    /**
     * 线程切换处理
     */
    fun <T> apply(view: IBaseView): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.compose(applySchedulers())
                .retryWhen(
                    RetryWithDelay(
                        RetryWithDelay.REQUEST_MAXRETRIES,
                        RetryWithDelay.REQUEST_RETRYDELAYSECOND
                    )
                )
                .doOnSubscribe { disposable ->
                    view.showProgressDialog()
                }
                .doFinally {
                    view.hideProgressDialog()
                }
        };
    }

}