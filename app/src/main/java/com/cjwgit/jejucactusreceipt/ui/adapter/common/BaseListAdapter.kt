package com.cjwgit.jejucactusreceipt.ui.adapter.common

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter<BIND : ViewDataBinding, DTO : IUniquable> : ListAdapter<DTO, BaseListAdapter.BaseViewHolder<BIND>>(MyItemDiffCallback<DTO>()) {
    class BaseViewHolder<BIND : ViewDataBinding>(val binding: BIND) : RecyclerView.ViewHolder(binding.root)

    class MyItemDiffCallback<DTO : IUniquable> : DiffUtil.ItemCallback<DTO>() {
        override fun areContentsTheSame(oldItem: DTO, newItem: DTO): Boolean {
            return oldItem.getUid() == newItem.getUid()
        }

        override fun areItemsTheSame(oldItem: DTO, newItem: DTO): Boolean {
            return oldItem == newItem
        }
    }
}