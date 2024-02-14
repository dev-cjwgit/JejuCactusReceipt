package com.cjwgit.jejucactusreceipt.model

import com.cjwgit.jejucactusreceipt.domain.AuctionEntity
import com.cjwgit.jejucactusreceipt.model.common.ProductModel
import com.cjwgit.jejucactusreceipt.repository.AuctionRepository

class AuctionProductModel(
    private val repository: AuctionRepository
) : ProductModel<AuctionEntity> {
    override fun getItem(position: Int): AuctionEntity {
        return repository.getItemToOrder(position)
    }

    override fun getItems(): List<AuctionEntity> {
        return repository.getItems()
    }

    override fun removeItem(position: Int) {
        repository.removeItemToOrder(position)
    }

    override fun swipe(from: AuctionEntity, to: AuctionEntity) {
        TODO("Not yet implemented")
    }

    override fun updateItem(item: AuctionEntity) {
        TODO("Not yet implemented")
    }

    override fun addItem(item: AuctionEntity) {
        repository.addItem(item)
    }
}