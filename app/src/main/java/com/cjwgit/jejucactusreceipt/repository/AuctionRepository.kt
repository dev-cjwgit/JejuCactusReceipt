package com.cjwgit.jejucactusreceipt.repository

import com.cjwgit.jejucactusreceipt.domain.AuctionEntity
import com.cjwgit.jejucactusreceipt.repository.common.BaseRepository
import com.cjwgit.jejucactusreceipt.utils.SQLiteHelper

class AuctionRepository(
    private val conn: SQLiteHelper
) : BaseRepository<AuctionEntity> {
    private val DB_NAME = "auction_item"


    override fun getItems(): List<AuctionEntity> {
        val result = conn.executeAll("SELECT * FROM $DB_NAME ORDER BY `order` ASC;")

        return result.map { item ->
            AuctionEntity(
                item["name"].toString(),
                item["amount"].toString().toLong(),
                item["price"].toString().toLong(),
                item["uid"].toString().toLong(),
                item["order"].toString().toLong()
            )
        }
    }

    override fun updateItem(item: AuctionEntity) {
        TODO("Not yet implemented")
    }

    override fun removeItem(item: AuctionEntity) {
        TODO("Not yet implemented")
    }

    override fun addItem(item: AuctionEntity) {
        val order = conn.executeOne("SELECT COUNT(*) FROM $DB_NAME;")["count(*)"]?.toLong() ?: 0L

        conn.execute(
            "INSERT INTO " +
                    "$DB_NAME(`order`, `name`, `amount`, `price`) " +
                    "VALUES " +
                    "(${order}, \"${item.name}\",${item.amount}, ${item.price});"
        )
    }
}