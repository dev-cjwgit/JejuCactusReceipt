package com.cjwgit.jejucactusreceipt.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(
    val context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "data.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE if not exists `cactus`(" +
                "`uid` LONG PRIMARY KEY autoincrement," +
                "`name` varchar(100) NOT NULL DEFAULT ''," +
                "`count` LONG NOT NULL," +
                "`price` LONG NOT NULL," +
                "`order` LONG NOT NULL);"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE cactus;"
        db.execSQL(sql)

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