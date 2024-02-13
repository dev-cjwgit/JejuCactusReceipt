package com.cjwgit.jejucactusreceipt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cjwgit.jejucactusreceipt.R
import com.cjwgit.jejucactusreceipt.databinding.TemplateCactusBasketBinding
import com.cjwgit.jejucactusreceipt.domain.CactusBasketVO
import com.cjwgit.jejucactusreceipt.ui.adapter.common.BaseListAdapter

class CactusBasketRecyclerViewAdapter(
) :
    BaseListAdapter<TemplateCactusBasketBinding, CactusBasketVO>() {
    private lateinit var onRemoveClick: (item: CactusBasketVO) -> Unit

    init {
        println("이건 한 번만 호출이 되는데")
    }

    fun setOnRemoveClickListener(listener: (item: CactusBasketVO) -> Unit) {
        onRemoveClick = listener
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): BaseViewHolder<TemplateCactusBasketBinding> {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.template_cactus_basket, viewGroup, false)
        val bind = TemplateCactusBasketBinding.bind(view)
        val holder = BaseViewHolder(bind)
        view.setOnLongClickListener {
            onRemoveClick.invoke(getItem(holder.adapterPosition))
            true
        }
        return holder
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<TemplateCactusBasketBinding>,
        position: Int
    ) {
        val item = getItem(holder.adapterPosition)
        // 데이터 주입
        holder.binding.item = item
    }
}