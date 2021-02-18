package com.karson.lib_commen.ui.fragment

import androidx.recyclerview.widget.RecyclerView
import com.karson.lib_commen.ui.adapter.BaseAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * @Author karson
 * @Date 2020/11/19-13:58
 * @desc listfragment 基类
 */
open abstract class BaseListFragment<T, VH : RecyclerView.ViewHolder> : BaseFragment() {

    protected lateinit var mSmart: SmartRefreshLayout
    protected lateinit var recyclerView: RecyclerView
    protected var mList: ArrayList<T> = ArrayList()
    protected var mAdapter: BaseAdapter<VH, T>? = null

    protected var pageIndex: Int = 1
    protected var pageSize: Int = 20

    override fun initViews() {
        mSmart = getSmart()
        mSmart.setOnRefreshListener {
            pageIndex = 1
            mList.clear()
            it.autoRefresh()
            onFresh()
        }
        mSmart.setOnLoadMoreListener {
            mList.clear()
            it.autoLoadMore()
            onLoadMore()
        }
        recyclerView = getRecycler()
        recyclerView.adapter = mAdapter


    }

    /**获取recyclerview*/
    abstract fun getRecycler(): RecyclerView

    /**获取smartRefreshlayout*/
    abstract fun getSmart(): SmartRefreshLayout

    /**刷新*/
    abstract fun onFresh()

    /**加载更多*/
    abstract fun onLoadMore()

}