package com.karson.lib_commen.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author karson
 * @Date 2020/11/18-18:40
 * @desc recyclerview适配器基类
 */
open abstract class BaseAdapter<VH : RecyclerView.ViewHolder, T : Any?> : RecyclerView.Adapter<VH>() {

    protected lateinit var mDataList: ArrayList<T>
    protected lateinit var holder: VH

    /**
     * 设置数据
     */
    fun setData(list: ArrayList<T>?) {
        mDataList.let { mDataList = ArrayList() }
        list?.let {
            mDataList?.clear()
            mDataList?.addAll(list)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return holder
    }

    override fun getItemCount(): Int {
        return if (mDataList == null) 0 else mDataList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        convertHolder(holder,position)
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        convertHolder(holder,position,payloads)
    }

    /**
     * normal 数据设置
     */
    abstract fun convertHolder(holder: VH, position: Int)

    /**
     * 单条刷新
     */
    fun convertHolder(holder: VH,position: Int, payloads: MutableList<Any>){}

}