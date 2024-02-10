package com.cjwgit.jejucactusreceipt.repository

import com.cjwgit.jejucactusreceipt.domain.CactusEntity
import com.cjwgit.jejucactusreceipt.repository.common.BaseRepository
import com.cjwgit.jejucactusreceipt.utils.SQLiteHelper

class AuctionRepository(
    private val conn: SQLiteHelper
) : BaseRepository<CactusEntity> {
    override fun getItems(): List<CactusEntity> {
        TODO("Not yet implemented")
    }

    override fun updateItem(item: CactusEntity) {
        TODO("Not yet implemented")
    }

    override fun removeItem(item: CactusEntity) {
        TODO("Not yet implemented")
    }

    override fun addItem(item: CactusEntity) {
        TODO("Not yet implemented")
    }
}