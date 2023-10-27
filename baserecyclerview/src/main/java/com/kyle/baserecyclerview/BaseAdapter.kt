package com.kyle.baserecyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * Created by Kyle on 2018/9/13.
 */
abstract class BaseAdapter<T,D:ViewBinding> :
    BaseQuickAdapter<T, BaseAdapter.MyViewHolder<D>>() {

     class MyViewHolder<D:ViewBinding>(
        val binding: D,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder<D> {
        return MyViewHolder(initViewBinding(parent, LayoutInflater.from(parent.context)))
    }

    abstract fun initViewBinding(parent: ViewGroup, inflater: LayoutInflater): D
    override fun onBindViewHolder(holder: MyViewHolder<D>, position: Int, item: T?) {
        convert(holder.binding, position, item)
    }

    protected abstract fun convert(binding: D, position: Int, item: T?)
}