package com.cjwgit.jejucactusreceipt.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(
    val context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_NAME = FolderPath.ITEM_DB_PATH + "/data.db"
        private const val DATABASE_VERSION = 1

        private const val CACTUS_SQL = "CREATE TABLE if not exists `cactus_item`(" +
                "`uid` INTEGER PRIMARY KEY autoincrement," +
                "`order` LONG NOT NULL," +
                "`name` varchar(100) NOT NULL," +
                "`price` LONG NOT NULL);"

        private const val AUCTION_SQL = "CREATE TABLE if not exists `auction_item`(" +
                "`uid` INTEGER PRIMARY KEY autoincrement," +
                "`order` LONG NOT NULL," +
                "`name` varchar(100) NOT NULL," +
                "`amount` LONG NOT NULL," +
                "`price` LONG NOT NULL);"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CACTUS_SQL)
        db.execSQL(AUCTION_SQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE cactus_item;")
        db.execSQL("DROP TABLE auction_item;")

        onCreate(db)
    }

    fun execute(sql: String) {
        val sqlite = writableDatabase
        sqlite.execSQL(sql)
    }

    fun executeAll(sql: String): List<Map<String, String>> {
        val results: MutableList<Map<String, String>> = mutableListOf()
        val sqlite = readableDatabase
        val cursor = sqlite.rawQuery(sql, null)
        cursor.use { cur ->
            val columnNames = cur.columnNames
            while (cur.moveToNext()) {
                val row = mutableMapOf<String, String>()
                for (i in columnNames.indices) {
                    row[columnNames[i]] = cur.getString(i) ?: ""
                }
                results.add(row)
            }
        }
        return results
    }

    fun executeOne(sql: String): Map<String, String> {
        val sqlite = readableDatabase
        val cursor = sqlite.rawQuery(sql, null)
        val result = mutableMapOf<String, String>()
        cursor.use {
            if (it.moveToFirst()) {
                for (i in 0 until it.columnCount) {
                    result[it.getColumnName(i)] = it.getString(i) ?: "null"
                }
            }
        }
        return result
    }
}