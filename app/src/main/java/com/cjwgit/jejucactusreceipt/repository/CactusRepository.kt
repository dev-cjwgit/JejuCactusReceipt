package com.cjwgit.jejucactusreceipt.repository

import com.cjwgit.jejucactusreceipt.domain.CactusEntity
import com.cjwgit.jejucactusreceipt.exec.CactusException
import com.cjwgit.jejucactusreceipt.exec.ErrorMessage
import com.cjwgit.jejucactusreceipt.repository.common.BaseRepository
import com.cjwgit.jejucactusreceipt.utils.SQLiteHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CactusRepository(
    private val sqlite: SQLiteHelper
) : BaseRepository<CactusEntity> {
    private val DB_NAME = "cactus_item"
    override suspend fun swipeItem(from: Int, to: Int) = withContext(Dispatchers.IO) {
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
            } ?: run {
                // from 항목이 존재하지 않음
                throw CactusException(ErrorMessage.NOT_SELECT_ITEM)
            }
        }
    }

    override suspend fun getItemToOrder(order: Int): CactusEntity = withContext(Dispatchers.IO) {
        val item = sqlite.executeOne("SELECT `uid`, `order`, `name`, `price` FROM $DB_NAME WHERE `order` = $order;")

        return@withContext CactusEntity(
            item["name"].toString(),
            item["price"].toString().toLong(),
            item["uid"].toString().toLong(),
            item["order"].toString().toLong()
        )

    }

    override suspend fun getItems(): List<CactusEntity> = withContext(Dispatchers.IO) {
        val result = sqlite.executeAll("SELECT * FROM $DB_NAME ORDER BY `order` ASC;")

        return@withContext result.map { item ->
            CactusEntity(
                item["name"].toString(),
                item["price"].toString().toLong(),
                item["uid"].toString().toLong(),
                item["order"].toString().toLong()
            )
        }
    }

    override suspend fun removeItemToOrder(order: Int) = withContext(Dispatchers.IO) {
        sqlite.use { conn ->
            conn.execute("DELETE FROM $DB_NAME WHERE `order` = $order")
            conn.execute("UPDATE $DB_NAME SET `order` = `order` - 1 WHERE  `order` > $order")
        }
    }

    override suspend fun updateItem(item: CactusEntity) = withContext(Dispatchers.IO) {
        sqlite.use { conn ->
            conn.execute("UPDATE $DB_NAME SET `name` = \"${item.name}\", `price` = ${item.price} WHERE `uid` = ${item.uid}")
        }
    }

    override suspend fun addItem(item: CactusEntity) = withContext(Dispatchers.IO) {
        sqlite.use {
            val order = sqlite.executeOne("SELECT COUNT(*) FROM $DB_NAME;")["COUNT(*)"]?.toLong() ?: 0L

            sqlite.execute(
                "INSERT INTO " +
                        "$DB_NAME(`order`, `name`, `price`) " +
                        "VALUES " +
                        "(${order}, \"${item.name}\", ${item.price});"
            )
        }
    }
}

