package com.cjwgit.jejucactusreceipt.model

import com.cjwgit.jejucactusreceipt.domain.CactusEntity
import com.cjwgit.jejucactusreceipt.model.common.ProductModel
import com.cjwgit.jejucactusreceipt.repository.CactusRepository

class CactusProductModel(
    private val repository: CactusRepository
) : ProductModel<CactusEntity> {
    override fun getItems(): List<CactusEntity> {
        return repository.getItems()
    }

    override fun swipe(from: CactusEntity, to: CactusEntity) {
        TODO("Not yet implemented")
    }

    override fun updateItem(item: CactusEntity) {
        TODO("Not yet implemented")
    }

    override fun removeItem(item: CactusEntity) {
        TODO("Not yet implemented")
    }

    override fun addItem(item: CactusEntity) {
        repository.addItem(item)
    }
}