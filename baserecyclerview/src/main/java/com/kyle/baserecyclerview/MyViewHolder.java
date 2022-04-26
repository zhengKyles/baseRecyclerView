package com.kyle.baserecyclerview;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.view.View;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;


/**
 * Created by Kyle on 2018/9/13.
 */

public class MyViewHolder<D extends ViewDataBinding> extends BaseViewHolder {
    public D binding;

    public MyViewHolder(View view) {
        super(view);
        binding = DataBindingUtil.bind(view);
    }

}
