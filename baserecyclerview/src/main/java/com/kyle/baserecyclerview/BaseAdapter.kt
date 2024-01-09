package com.kyle.baserecyclerview

import android.view.View
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * Created by Kyle on 2018/9/13.
 */
abstract class BaseAdapter<T, D : ViewBinding>(layoutResId: Int) :
    BaseQuickAdapter<T, MyViewHolder<D>>(layoutResId, ArrayList()) {
    override fun createBaseViewHolder(view: View): MyViewHolder<D> {
        return MyViewHolder(view)
    }

    override fun convert(helper: MyViewHolder<D>, item: T) {
        convert(helper.binding as D, helper.adapterPosition, item)
    }

    protected abstract fun convert(binding: D, position: Int, item: T)
}