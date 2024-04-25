package com.kyle.baserecyclerview

import android.view.View
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * Created by Kyle on 2018/9/13.
 */
abstract class BaseAdapter<T, D : ViewBinding>(layoutResId: Int) :
    BaseQuickAdapter<T, BaseViewHolder>(layoutResId, ArrayList()) {
    override fun createBaseViewHolder(view: View): BaseViewHolder {
        return BaseViewHolder(view)
    }

    override fun convert(holder: BaseViewHolder, item: T) {
        val binding = initBinding(holder.itemView)
        convert(binding, holder.absoluteAdapterPosition, item)
    }

    abstract fun initBinding(itemView: View): D

    protected abstract fun convert(binding: D, position: Int, item: T)
}