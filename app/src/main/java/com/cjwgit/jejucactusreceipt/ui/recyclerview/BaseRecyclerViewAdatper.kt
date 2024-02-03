package com.cjwgit.jejucactusreceipt.ui.recyclerview


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

    fun initData(itemList: MutableList<DTO>) {
        items.clear()
        items.addAll(itemList)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyItemRangeRemoved(0, itemCount)
    }

    fun add(data: DTO) {
        items.add(data)
        notifyItemInserted(itemCount)
    }

    fun remove(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun remove(data: DTO) {
        val position = items.indexOf(data)
        if (position != -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}