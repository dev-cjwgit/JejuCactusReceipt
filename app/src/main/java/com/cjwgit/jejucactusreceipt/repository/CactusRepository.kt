package com.cjwgit.jejucactusreceipt.repository

import com.cjwgit.jejucactusreceipt.domain.CactusEntity
import com.cjwgit.jejucactusreceipt.repository.common.BaseRepository
import com.cjwgit.jejucactusreceipt.utils.SQLiteHelper

class CactusRepository(
    private val conn: SQLiteHelper
) : BaseRepository<CactusEntity> {
    private val DB_NAME = "cactus_item"
    override fun getItemToOrder(order: Int): CactusEntity {
        val item = conn.executeOne("SELECT uid, order, name, price FROM $DB_NAME WHERE order = $order;")

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
        conn.execute("DELETE FROM $DB_NAME WHERE `order` = $order")
        conn.execute("UPDATE $DB_NAME SET `order` = `order` - 1 WHERE  `order` > $order")
    }

    override fun updateItem(item: CactusEntity) {
        TODO("Not yet implemented")
    }

    override fun addItem(item: CactusEntity) {
        val order = conn.executeOne("SELECT COUNT(*) FROM $DB_NAME;")["COUNT(*)"]?.toLong() ?: 0L

        conn.execute(
            "INSERT INTO " +
                    "$DB_NAME(`order`, `name`, `price`) " +
                    "VALUES " +
                    "(${order}, \"${item.name}\", ${item.price});"
        )
    }
}

