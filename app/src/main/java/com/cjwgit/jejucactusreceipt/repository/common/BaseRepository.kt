package com.cjwgit.jejucactusreceipt.repository.common

interface BaseRepository<T> {
    fun getItems(): List<T>

    fun addItem(item: T)

    fun removeItem(item: T)

    fun updateItem(item: T)
}