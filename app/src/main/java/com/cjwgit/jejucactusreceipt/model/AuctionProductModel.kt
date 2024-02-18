package com.cjwgit.jejucactusreceipt.model

import com.cjwgit.jejucactusreceipt.domain.AuctionEntity
import com.cjwgit.jejucactusreceipt.model.common.ProductModel
import com.cjwgit.jejucactusreceipt.repository.AuctionRepository

class AuctionProductModel(
    private val repository: AuctionRepository
) : ProductModel<AuctionEntity> {
    override suspend fun getItem(position: Int): AuctionEntity {
        return repository.getItemToOrder(position)
    }

    override suspend fun getItems(): List<AuctionEntity> {
        return repository.getItems()
    }

    override suspend fun removeItem(position: Int) {
        repository.removeItemToOrder(position)
    }

    override suspend fun swipe(from: Int, to: Int) {
        repository.swipeItem(from, to)
    }

    override suspend fun updateItem(item: AuctionEntity) {
        repository.updateItem(item)
    }

    override suspend fun addItem(item: AuctionEntity) {
        repository.addItem(item)
    }
}