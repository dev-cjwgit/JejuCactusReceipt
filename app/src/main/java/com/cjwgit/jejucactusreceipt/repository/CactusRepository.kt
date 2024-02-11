package com.cjwgit.jejucactusreceipt.repository

import com.cjwgit.jejucactusreceipt.domain.CactusEntity
import com.cjwgit.jejucactusreceipt.repository.common.BaseRepository
import com.cjwgit.jejucactusreceipt.utils.SQLiteHelper

class CactusRepository(
    private val conn: SQLiteHelper
) : BaseRepository<CactusEntity> {
    private val DB_NAME = "cactus_item"
    override fun getItems(): List<CactusEntity> {
        val result = conn.executeAll("SELECT * FROM cactus_item ORDER BY `order` ASC;")

        val temp: List<CactusEntity> = result.map { item ->
            CactusEntity(
                item["name"].toString(),
                item["price"].toString().toLong()
            )
        }
        return temp
    }

    override fun updateItem(item: CactusEntity) {
        TODO("Not yet implemented")
    }

    override fun removeItem(item: CactusEntity) {
        TODO("Not yet implemented")
    }

    override fun addItem(item: CactusEntity) {
        val order = conn.executeOne("SELECT COUNT(*) FROM cactus_item;")["count(*)"]?.toLong() ?: 0L

        conn.execute(
            "INSERT INTO " +
                    "$DB_NAME(`order`, `name`, `price`) " +
                    "VALUES " +
                    "(${order}, \"${item.name}\", ${item.price});"
        )
    }
}

