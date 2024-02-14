package com.cjwgit.jejucactusreceipt.repository

import com.cjwgit.jejucactusreceipt.domain.AuctionEntity
import com.cjwgit.jejucactusreceipt.repository.common.BaseRepository
import com.cjwgit.jejucactusreceipt.utils.SQLiteHelper

class AuctionRepository(
    private val conn: SQLiteHelper
) : BaseRepository<AuctionEntity> {
    private val DB_NAME = "auction_item"
    override fun getItemToOrder(order: Int): AuctionEntity {
        val item = conn.executeOne("SELECT uid, order, name, amount, price FROM $DB_NAME WHERE order = $order;")

        return AuctionEntity(
            item["name"].toString(),
            item["amount"].toString().toLong(),
            item["price"].toString().toLong(),
            item["uid"].toString().toLong(),
            item["order"].toString().toLong()
        )
    }


    override fun getItems(): List<AuctionEntity> {
        val result = conn.executeAll("SELECT uid, order, name, amount, price FROM $DB_NAME ORDER BY `order` ASC;")

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

    override fun removeItemToOrder(order: Int) {
        conn.execute("DELETE FROM $DB_NAME WHERE `order` = $order")
        conn.execute("UPDATE $DB_NAME SET `order` = `order` - 1 WHERE  `order` > $order")
    }

    override fun updateItem(item: AuctionEntity) {
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