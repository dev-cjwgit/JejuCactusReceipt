package com.cjwgit.jejucactusreceipt.repository

import com.cjwgit.jejucactusreceipt.domain.AuctionEntity
import com.cjwgit.jejucactusreceipt.exec.CactusException
import com.cjwgit.jejucactusreceipt.exec.ErrorMessage
import com.cjwgit.jejucactusreceipt.repository.common.BaseRepository
import com.cjwgit.jejucactusreceipt.utils.SQLiteHelper

class AuctionRepository(
    private val sqlite: SQLiteHelper
) : BaseRepository<AuctionEntity> {
    private val DB_NAME = "auction_item"
    override fun swipeItem(from: Int, to: Int) {
        sqlite.use { conn ->
            val fromUid = conn.executeOne("SELECT `uid` FROM $DB_NAME WHERE `order` = $from")["uid"]?.toLong()
            fromUid?.let {
                if (from > to) {
                    // 항목 위로
                    conn.execute("UPDATE $DB_NAME SET `order` = `order` + 1 WHERE `order` BETWEEN $to AND ${from - 1}")
                    conn.execute("UPDATE $DB_NAME SET `order` = $to WHERE `uid` = $it")
                } else if (from < to) {
                    // 항목 아래로
                    conn.execute("UPDATE $DB_NAME SET `order` = `order` - 1 WHERE `order` BETWEEN ${from + 1} AND $to")
                    conn.execute("UPDATE $DB_NAME SET `order` = $to WHERE `uid` = $it")
                }
            } ?: {
                // from 항목이 존재하지 않음
                throw CactusException(ErrorMessage.NOT_SELECT_ITEM)
            }
        }
    }

    override fun getItemToOrder(order: Int): AuctionEntity {
        val item = sqlite.executeOne("SELECT `uid`, `order`, `name`, `amount`, `price` FROM $DB_NAME WHERE `order` = $order;")

        return AuctionEntity(
            item["name"].toString(),
            item["amount"].toString().toLong(),
            item["price"].toString().toLong(),
            item["uid"].toString().toLong(),
            item["order"].toString().toLong()
        )
    }


    override fun getItems(): List<AuctionEntity> {
        val result = sqlite.executeAll("SELECT `uid`, `order`, `name`, `amount`, `price` FROM $DB_NAME ORDER BY `order` ASC;")

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
        sqlite.use { conn ->
            conn.execute("DELETE FROM $DB_NAME WHERE `order` = $order")
            conn.execute("UPDATE $DB_NAME SET `order` = `order` - 1 WHERE  `order` > $order")
        }
    }

    override fun updateItem(item: AuctionEntity) {
        sqlite.use { conn ->
            conn.execute("UPDATE $DB_NAME SET `name` = \"${item.name}\", `amount` = ${item.amount}, `price` = ${item.price} WHERE `uid` = ${item.uid}")
        }
    }

    override fun addItem(item: AuctionEntity) {
        sqlite.use { conn ->
            val order = conn.executeOne("SELECT COUNT(*) FROM $DB_NAME;")["COUNT(*)"]?.toLong() ?: 0L

            conn.execute(
                "INSERT INTO " +
                        "$DB_NAME(`order`, `name`, `amount`, `price`) " +
                        "VALUES " +
                        "(${order}, \"${item.name}\",${item.amount}, ${item.price});"
            )
        }
    }
}