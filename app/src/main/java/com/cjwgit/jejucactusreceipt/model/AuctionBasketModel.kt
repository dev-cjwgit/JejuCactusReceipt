package com.cjwgit.jejucactusreceipt.model

import com.cjwgit.jejucactusreceipt.domain.AuctionBasketVO
import com.cjwgit.jejucactusreceipt.model.common.BasketBaseModel

class AuctionBasketModel : BasketBaseModel<AuctionBasketVO>() {
    fun getItemsToPadding(): List<AuctionBasketVO> {
        val paddingSize = MAX_ITEM_SIZE - getSize()

        val items = super.getItems() as ArrayList<AuctionBasketVO>
        items.addAll(List(paddingSize) { AuctionBasketVO(-1, "", 0, 0, 0) })
        return items
    }

    override fun getTotalPrice(): Long {
        TODO("Not yet implemented")
    }

    override fun getTotalBoxCount(): Long {
        return getItems().sumOf { it.boxCount }
    }
}