package com.cjwgit.jejucactusreceipt.repository

import com.cjwgit.jejucactusreceipt.domain.CactusEntity
import com.cjwgit.jejucactusreceipt.exec.CactusException
import com.cjwgit.jejucactusreceipt.exec.ErrorMessage
import com.cjwgit.jejucactusreceipt.repository.common.BaseRepository
import com.cjwgit.jejucactusreceipt.utils.SQLiteHelper

class CactusRepository(
    private val conn: SQLiteHelper
) : BaseRepository<CactusEntity> {
    private val DB_NAME = "cactus_item"
    override fun swipeItem(from: Int, to: Int) {
        conn.use {
            val fromUid = it.executeOne("SELECT `uid` FROM $DB_NAME WHERE `order` = $from")["uid"]?.toLong()
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

    override fun getItemToOrder(order: Int): CactusEntity {
        val item = conn.executeOne("SELECT `uid`, `order`, `name`, `price` FROM $DB_NAME WHERE `order` = $order;")

        return CactusEntity(
            item["name"].toString(),
            item["price"].toString().toLong(),
            item["uid"].toString().toLong(),
            item["order"].toString().toLong()
        )

    }

    override fun getItems(): List<CactusEntity> {
        val result = conn.executeAll("SELECT * FROM $DB_NAME ORDER BY `order` ASC;")

        return result.map { item ->
            CactusEntity(
                item["name"].toString(),
                item["price"].toString().toLong(),
                item["uid"].toString().toLong(),
                item["order"].toString().toLong()
            )
        }
    }

    override fun removeItemToOrder(order: Int) {
        conn.use {
            conn.execute("DELETE FROM $DB_NAME WHERE `order` = $order")
            conn.execute("UPDATE $DB_NAME SET `order` = `order` - 1 WHERE  `order` > $order")
        }
    }

    override fun updateItem(item: CactusEntity) {
        conn.use {
            conn.execute("UPDATE $DB_NAME SET `name` = \"${item.name}\", `price` = ${item.price} WHERE `uid` = ${item.uid}")
        }
    }

    override fun addItem(item: CactusEntity) {
        conn.use {
            val order = conn.executeOne("SELECT COUNT(*) FROM $DB_NAME;")["COUNT(*)"]?.toLong() ?: 0L

            conn.execute(
                "INSERT INTO " +
                        "$DB_NAME(`order`, `name`, `price`) " +
                        "VALUES " +
                        "(${order}, \"${item.name}\", ${item.price});"
            )
        }
    }
}

