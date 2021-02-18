package com.karson.lib_commen.ui.activity

import androidx.recyclerview.widget.RecyclerView
import com.karson.lib_commen.ui.adapter.BaseAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * @Author karson
 * @Date 2020/11/18-18:26
 * @desc 列表活动基类
 * @param T bean VH viewholder
 */
open abstract class BaseListActivity<T, VH : RecyclerView.ViewHolder> : BaseActivity() {

    protected lateinit var mSmart: SmartRefreshLayout

    //此处声明recyclerview但本类不设置manager和数据源
    protected lateinit var recyclerView: RecyclerView
    protected var mDataList: ArrayList<T> = ArrayList<T>()
    protected var mAdapter: BaseAdapter<VH, T>? = null

    //分页
    protected var pageIndex: Int = 1
    protected var pageSize: Int = 20

    override fun initViews() {
        mSmart = getSmart()
        mSmart.setOnLoadMoreListener {
            mDataList.clear()
            it.autoLoadMore()
            onLoadMore()
        }
        mSmart.setOnRefreshListener {
            pageIndex = 1
            mDataList.clear()
            it.autoRefresh()
            onFresh()
        }
        recyclerView = getRecycleView()
        recyclerView.adapter = mAdapter
    }

    /**
     * 获取recyclerview
     * 需设置recyclerview的layoutmanager
     */
    abstract fun getRecycleView(): RecyclerView

    /**绑定smart*/
    abstract fun getSmart(): SmartRefreshLayout

    /**刷新*/
    abstract fun onFresh()

    /**加载更多*/
    abstract fun onLoadMore()
}