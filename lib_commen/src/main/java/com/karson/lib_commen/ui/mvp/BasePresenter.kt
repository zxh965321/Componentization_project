package com.karson.lib_commen.ui.mvp

import android.app.Dialog
import android.content.Context
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.karson.lib_commen.app.CommenApp
import com.karson.lib_commen.net.rx.RxUtil
import com.karson.lib_commen.ui.wight.dialog.DialogHelper
import com.uber.autodispose.AutoDisposeConverter
import io.reactivex.ObservableTransformer
import com.karson.lib_commen.util.getViewModel
/**
 * @Author karson
 * @Date 2021/1/29-14:02
 * @desc mvp模式下控制器的顶级类
 */
abstract class BasePresenter<VIEW:IBaseView>:IPresenter<VIEW>  {
    private lateinit var progressDialog : Dialog
    protected var mOwner: LifecycleOwner? = null;
    protected var mRootView: VIEW? = null;
    var mContext: Context? = null

    override fun attach(view: VIEW, @Nullable context: Context) {
        this.mRootView = view
        this.mContext = context
        progressDialog = DialogHelper.getProGressDialog(mContext!!)
        if (context is LifecycleOwner) {
            this.mOwner = context
        }
    }

    //生命周期绑定
    fun <T> bindLifecycle(): AutoDisposeConverter<T> {
        if (this.mOwner == null)
            throw  NullPointerException("lifecycleOwner == null");
        return RxUtil.bindLifeDestory(this.mOwner!!)
    }

    fun <VM : ViewModel> getViewModel(clazz: Class<out ViewModel>): VM {
        return getViewModel(mContext!!, clazz)
    }

    /**
     * 线程切换处理
     */
    fun <T> apply(): ObservableTransformer<T, T> {
        return RxUtil.apply(mRootView!!, mOwner!!)
    }

    /**
     * 生命周期绑定
     */
    fun <T> applyNoLoading(): ObservableTransformer<T, T> {
        return RxUtil.applyNoLoading(mOwner!!)
    }

    fun <T> applyTrans(): ObservableTransformer<T, T> {
        return RxUtil.applyTrans(mRootView!!, mOwner!!)
    }

    //初始化
    override fun onCreate() {

    }

    //销毁 回调
    override fun onDestory() {
        mRootView = null
        mOwner = null
        mContext = null
    }

    fun showProgressDialog() {
        progressDialog?.let {
            if (!it.isShowing)
                it.show()
        }
    }

    fun hideProgressDialog() {
        progressDialog?.let {
            if (it.isShowing)
                it.dismiss();
        }
    }
}