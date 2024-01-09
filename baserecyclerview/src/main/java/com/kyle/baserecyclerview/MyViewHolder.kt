package com.kyle.baserecyclerview

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * Created by Kyle on 2018/9/13.
 */
class MyViewHolder<D : ViewBinding>(view: View) :
    BaseViewHolder(view) {
    var binding: D?

    init {
        binding = DataBindingUtil.bind(view)
    }
}