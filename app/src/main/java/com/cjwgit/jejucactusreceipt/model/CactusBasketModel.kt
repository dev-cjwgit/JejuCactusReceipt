package com.cjwgit.jejucactusreceipt.model

import com.cjwgit.jejucactusreceipt.domain.CactusBasketVO
import com.cjwgit.jejucactusreceipt.model.common.BasketBaseModel

class CactusBasketModel : BasketBaseModel<CactusBasketVO>() {
    fun getItemsToPadding(): List<CactusBasketVO> {
        val paddingSize = MAX_ITEM_SIZE - getSize()

        val items = super.getItems() as ArrayList<CactusBasketVO>
        items.addAll(List(paddingSize) { CactusBasketVO(-1, "", 0, 0, 0) })
        return items
    }

    override fun getTotalPrice(): Long {
        return getItems().sumOf { it.total }

    }

    override fun getTotalBoxCount(): Long {
        return getItems().sumOf { it.boxCount }
    }
}