package com.cjwgit.jejucactusreceipt.model

import com.cjwgit.jejucactusreceipt.domain.CactusEntity
import com.cjwgit.jejucactusreceipt.model.common.ProductModel
import com.cjwgit.jejucactusreceipt.repository.CactusRepository

class CactusProductModel(
    private val repository: CactusRepository
) : ProductModel<CactusEntity> {
    override fun getItem(position: Int): CactusEntity {
        return repository.getItemToOrder(position)
    }

    override fun getItems(): List<CactusEntity> {
        return repository.getItems()
    }

    override fun removeItem(position: Int) {
        repository.removeItemToOrder(position)
    }

    override fun swipe(from: Int, to: Int) {
        repository.swipeItem(from, to)
    }

    override fun updateItem(item: CactusEntity) {
        repository.updateItem(item)
    }


    override fun addItem(item: CactusEntity) {
        repository.addItem(item)
    }
}