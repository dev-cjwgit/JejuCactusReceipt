package com.cjwgit.jejucactusreceipt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cjwgit.jejucactusreceipt.R
import com.cjwgit.jejucactusreceipt.databinding.TemplateCactusAuctionBasketBinding
import com.cjwgit.jejucactusreceipt.domain.CactusAuctionBasketVO
import com.cjwgit.jejucactusreceipt.ui.recyclerview.BaseRecyclerViewAdapter

class CactusAuctionBasketRecyclerViewAdapter(
    private val onRemoveClick: (item: CactusAuctionBasketVO) -> Unit,
) :
    BaseRecyclerViewAdapter<TemplateCactusAuctionBasketBinding, CactusAuctionBasketVO>(
        mutableListOf()
    ) {
    init {
        println("")
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<TemplateCactusAuctionBasketBinding> {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.template_cactus_auction_basket, viewGroup, false)
        val bind = TemplateCactusAuctionBasketBinding.bind(view)
        val holder = BaseRecyclerViewHolder(bind)
        view.setOnLongClickListener {
            onRemoveClick.invoke(getItem(holder.adapterPosition))
            remove(holder.adapterPosition)
            true
        }
        return holder
    }

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<TemplateCactusAuctionBasketBinding>,
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