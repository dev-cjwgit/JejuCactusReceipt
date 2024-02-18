package com.cjwgit.jejucactusreceipt.model

import com.cjwgit.jejucactusreceipt.domain.CactusEntity
import com.cjwgit.jejucactusreceipt.model.common.ProductModel
import com.cjwgit.jejucactusreceipt.repository.CactusRepository

class CactusProductModel(
    private val repository: CactusRepository
) : ProductModel<CactusEntity> {
    override suspend fun getItem(position: Int): CactusEntity {
        return repository.getItemToOrder(position)
    }

    override suspend fun getItems(): List<CactusEntity> {
        return repository.getItems()
    }

    override suspend fun removeItem(position: Int) {
        repository.removeItemToOrder(position)
    }

    override suspend fun swipe(from: Int, to: Int) {
        repository.swipeItem(from, to)
    }

    override suspend fun updateItem(item: CactusEntity) {
        repository.updateItem(item)
    }


    override suspend fun addItem(item: CactusEntity) {
        repository.addItem(item)
    }
}