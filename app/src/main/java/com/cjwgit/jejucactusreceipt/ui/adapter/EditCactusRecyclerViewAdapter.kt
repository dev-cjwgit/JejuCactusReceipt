package com.cjwgit.jejucactusreceipt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cjwgit.jejucactusreceipt.R
import com.cjwgit.jejucactusreceipt.databinding.TemplateCactusBinding
import com.cjwgit.jejucactusreceipt.domain.CactusEntity
import com.cjwgit.jejucactusreceipt.ui.adapter.common.BaseListAdapter

class EditCactusRecyclerViewAdapter : BaseListAdapter<TemplateCactusBinding, CactusEntity>() {
    private lateinit var onClickListener: (item: CactusEntity) -> Unit
    fun setOnClickListener(listener: (item: CactusEntity) -> Unit) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): BaseViewHolder<TemplateCactusBinding> {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.template_cactus, viewGroup, false)
        val bind = TemplateCactusBinding.bind(view)
        val holder = BaseViewHolder(bind)
        view.setOnClickListener {
            onClickListener.invoke(getItem(holder.adapterPosition))
        }
        return holder
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<TemplateCactusBinding>,
        position: Int
    ) {
        val item = getItem(holder.adapterPosition)
        // 데이터 주입
        holder.binding.item = item
    }
}