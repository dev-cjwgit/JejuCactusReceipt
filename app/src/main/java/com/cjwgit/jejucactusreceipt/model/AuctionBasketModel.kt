package com.cjwgit.jejucactusreceipt.model

import com.cjwgit.jejucactusreceipt.domain.AuctionBasketVO
import com.cjwgit.jejucactusreceipt.model.common.BasketBaseModel

class AuctionBasketModel : BasketBaseModel<AuctionBasketVO>() {
    override fun getTotalPrice(): Long {
        TODO("Not yet implemented")
    }

    override fun getTotalBoxCount(): Long {
        return getItems().sumOf { it.boxCount }
    }
}