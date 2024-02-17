package com.cjwgit.jejucactusreceipt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cjwgit.jejucactusreceipt.R
import com.cjwgit.jejucactusreceipt.databinding.TemplateAuctionBinding
import com.cjwgit.jejucactusreceipt.domain.AuctionEntity
import com.cjwgit.jejucactusreceipt.ui.adapter.common.BaseListAdapter

class EditAuctionRecyclerViewAdapter : BaseListAdapter<TemplateAuctionBinding, AuctionEntity>() {
    private lateinit var onClickListener: (item: AuctionEntity) -> Unit
    fun setOnClickListener(listener: (item: AuctionEntity) -> Unit) {
        onClickListener = listener
    }
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): BaseViewHolder<TemplateAuctionBinding> {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.template_auction, viewGroup, false)
        val bind = TemplateAuctionBinding.bind(view)
        val holder = BaseViewHolder(bind)
        view.setOnClickListener {
            onClickListener.invoke(getItem(holder.adapterPosition))
        }
        return holder
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<TemplateAuctionBinding>,
        position: Int
    ) {
        val item = getItem(holder.adapterPosition)
        // 데이터 주입
        holder.binding.item = item
    }
}