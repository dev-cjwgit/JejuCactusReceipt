package com.cjwgit.jejucactusreceipt.utils

import android.os.Environment

object FolderPath {
    private val sdcard = Environment.getExternalStorageDirectory()

    val ITEM_DB_PATH = "${sdcard.absolutePath}/jeju/"
}