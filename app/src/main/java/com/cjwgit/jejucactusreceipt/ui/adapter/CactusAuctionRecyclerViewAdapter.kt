package com.cjwgit.jejucactusreceipt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cjwgit.jejucactusreceipt.R
import com.cjwgit.jejucactusreceipt.databinding.TemplateAuctionBinding
import com.cjwgit.jejucactusreceipt.domain.CactusAuctionEntity
import com.cjwgit.jejucactusreceipt.ui.adapter.common.BaseRecyclerViewAdapter

class CactusAuctionRecyclerViewAdapter(
) : BaseRecyclerViewAdapter<TemplateAuctionBinding, CactusAuctionEntity>(
    mutableListOf()
) {
    private lateinit var onClickListener: (item: CactusAuctionEntity) -> Unit
    fun setOnClickListener(listener: (item: CactusAuctionEntity) -> Unit) {
        onClickListener = listener
    }
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<TemplateAuctionBinding> {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.template_auction, viewGroup, false)
        val bind = TemplateAuctionBinding.bind(view)
        val holder = BaseRecyclerViewHolder(bind)
        view.setOnClickListener {
            onClickListener.invoke(getItem(holder.adapterPosition))
        }
        return holder
    }

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<TemplateAuctionBinding>,
        position: Int
    ) {
        val items = getItems()
        if (items.isNotEmpty()) {
            val listposition = items[position]
            // 데이터 주입
            holder.binding.item = listposition

        }
    }
}