package com.karson.lib_commen.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.components.ImmersionOwner
import com.gyf.immersionbar.components.ImmersionProxy
import com.karson.lib_commen.R

/**
 * @Author karson
 * @Date 2020/11/19-09:40
 * @desc fragment 基类
 */
open abstract class BaseFragment : Fragment() , ImmersionOwner {

    private lateinit var fragBaseTitle: ViewStub
    private lateinit var fragBaseContent: ViewStub
    private lateinit var fragBaseEmpty: ViewStub

    private lateinit var mContext: Context

    private lateinit var process:ImmersionProxy
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        process = ImmersionProxy(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v: View = inflater.inflate(R.layout.layout_base, container)
        fragBaseContent = v.findViewById(R.id.baseContent)
        fragBaseEmpty = v.findViewById(R.id.baseEmpty)
        fragBaseTitle = v.findViewById(R.id.toolbar)
        if (loadView() != 0) {
            fragBaseContent.layoutResource = loadView()
            fragBaseContent.visibility = View.VISIBLE
            fragBaseEmpty.visibility = View.GONE
        }else{
            fragBaseEmpty.visibility = View.VISIBLE
            fragBaseContent.visibility = View.GONE

        }

        return v/*super.onCreateView(inflater, container, savedInstanceState)*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
    }

    /**fragment获取实例*/
    abstract fun getInstance(bundle: Bundle): BaseFragment

    /**初始化views*/
    abstract fun initViews()

    /**加载布局*/
    abstract fun loadView(): Int

    /** 初始化数据 */
    abstract fun initData()

    /**显示空布局*/
    fun showEmptyLayout() {
        fragBaseEmpty.visibility = View.VISIBLE
        fragBaseContent.visibility = View.GONE
    }

    /**显示数据布局*/
    fun showContentLayout() {
        fragBaseContent.visibility = View.VISIBLE
        fragBaseEmpty.visibility = View.GONE
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        process.isUserVisibleHint = isVisibleToUser
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        process.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        process.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        process.onResume()
    }

    override fun onPause() {
        super.onPause()
        process.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        process.onDestroy()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        process.onHiddenChanged(hidden)
    }

    /**
     * 懒加载，在view初始化完成之前执行
     * On lazy after view.
     */
    override fun onLazyBeforeView() {}

    /**
     * 懒加载，在view初始化完成之后执行
     * On lazy before view.
     */
    override fun onLazyAfterView() {}

    /**
     * Fragment用户可见时候调用
     * On visible.
     */
    override fun onVisible() {}

    /**
     * Fragment用户不可见时候调用
     * On invisible.
     */
    override fun onInvisible() {}

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    override fun immersionBarEnabled(): Boolean {
        return true
    }
}

