package com.cjwgit.jejucactusreceipt.model

import com.cjwgit.jejucactusreceipt.domain.CactusBasketVO
import com.cjwgit.jejucactusreceipt.model.common.BasketBaseModel

class CactusBasketModel : BasketBaseModel<CactusBasketVO>() {
    override fun getTotalPrice(): Long {
        return getItems().sumOf { it.total }

    }

    override fun getTotalBoxCount(): Long {
        return getItems().sumOf { it.boxCount }
    }
}