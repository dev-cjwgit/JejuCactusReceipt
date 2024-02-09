package com.cjwgit.jejucactusreceipt.ui.adapter.common


import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<BIND : ViewDataBinding, DTO>(
    private val items: MutableList<DTO> = mutableListOf(),
) : RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseRecyclerViewHolder<BIND>>() {

    class BaseRecyclerViewHolder<BIND : ViewDataBinding>(var binding: BIND) :
        RecyclerView.ViewHolder(binding.root)

    abstract override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<BIND>

    override fun getItemCount(): Int = items.size


    fun getItem(pos: Int): DTO {
        return items[pos]
    }

    fun getItems(): MutableList<DTO> {
        return items
    }

    fun setData(itemList: MutableList<DTO>) {
        items.clear()
        items.addAll(itemList)
        notifyDataSetChanged()
    }

    fun clear() {
        val count = items.size
        items.clear()
        notifyItemRangeRemoved(0, count)
    }
}