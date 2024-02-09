package com.cjwgit.jejucactusreceipt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cjwgit.jejucactusreceipt.R
import com.cjwgit.jejucactusreceipt.databinding.TemplateAuctionBasketBinding
import com.cjwgit.jejucactusreceipt.domain.AuctionBasketVO
import com.cjwgit.jejucactusreceipt.ui.recyclerview.BaseRecyclerViewAdapter

class CactusAuctionBasketRecyclerViewAdapter :
    BaseRecyclerViewAdapter<TemplateAuctionBasketBinding, AuctionBasketVO>(
        mutableListOf()
    ) {
    private lateinit var onRemoveClick: (item: AuctionBasketVO) -> Unit

    init {
        println("이건 왜 계속 호출 되냐고")
    }

    fun setOnRemoveClickListener(listener: (item: AuctionBasketVO) -> Unit) {
        onRemoveClick = listener
    }

    init {
        println("")
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<TemplateAuctionBasketBinding> {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.template_auction_basket, viewGroup, false)
        val bind = TemplateAuctionBasketBinding.bind(view)
        val holder = BaseRecyclerViewHolder(bind)
        view.setOnLongClickListener {
            onRemoveClick.invoke(getItem(holder.adapterPosition))
            true
        }
        return holder
    }

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<TemplateAuctionBasketBinding>,
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