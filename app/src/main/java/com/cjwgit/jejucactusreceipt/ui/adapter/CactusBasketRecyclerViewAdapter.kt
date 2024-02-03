package com.cjwgit.jejucactusreceipt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cjwgit.jejucactusreceipt.R
import com.cjwgit.jejucactusreceipt.databinding.TemplateCactusBinding
import com.cjwgit.jejucactusreceipt.domain.CactusEntity
import com.cjwgit.jejucactusreceipt.ui.recyclerview.BaseRecyclerViewAdapter

class CactusBasketRecyclerViewAdapter(
    private val onClickListener: (item: CactusEntity) -> Unit,
) : BaseRecyclerViewAdapter<TemplateCactusBinding, CactusEntity>(
    mutableListOf()
) {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<TemplateCactusBinding> {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.template_cactus, viewGroup, false) //내가 각아이템에 사용하는 view
        val bind = TemplateCactusBinding.bind(view)
        val holder = BaseRecyclerViewHolder(bind)
        view.setOnClickListener {
            onClickListener.invoke(getItem(holder.adapterPosition))
        }
        return holder
    }

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<TemplateCactusBinding>,
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