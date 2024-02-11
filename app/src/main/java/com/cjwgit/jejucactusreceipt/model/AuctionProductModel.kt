package com.cjwgit.jejucactusreceipt.model

import com.cjwgit.jejucactusreceipt.domain.AuctionEntity
import com.cjwgit.jejucactusreceipt.model.common.ProductModel
import com.cjwgit.jejucactusreceipt.repository.AuctionRepository

class AuctionProductModel(
    private val repository: AuctionRepository
) : ProductModel<AuctionEntity> {
    override fun getItems(): List<AuctionEntity> {
        return repository.getItems()
    }

    override fun swipe(from: AuctionEntity, to: AuctionEntity) {
        TODO("Not yet implemented")
    }

    override fun updateItem(item: AuctionEntity) {
        TODO("Not yet implemented")
    }

    override fun removeItem(item: AuctionEntity) {
        TODO("Not yet implemented")
    }

    override fun addItem(item: AuctionEntity) {
        repository.addItem(item)
    }
}