package com.cjwgit.jejucactusreceipt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cjwgit.jejucactusreceipt.R
import com.cjwgit.jejucactusreceipt.databinding.TemplateCactusBinding
import com.cjwgit.jejucactusreceipt.domain.CactusEntity
import com.cjwgit.jejucactusreceipt.ui.adapter.common.BaseListAdapter

class EditCactusRecyclerViewAdapter(
) : BaseListAdapter<TemplateCactusBinding, CactusEntity>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): BaseViewHolder<TemplateCactusBinding> {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.template_cactus, viewGroup, false)
        val bind = TemplateCactusBinding.bind(view)
        val holder = BaseViewHolder(bind)
        view.setOnClickListener {

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