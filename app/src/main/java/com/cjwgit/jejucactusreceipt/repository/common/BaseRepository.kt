package com.cjwgit.jejucactusreceipt.repository.common

interface BaseRepository<T> {
    fun getItemToOrder(order: Int): T

    fun getItems(): List<T>

    fun addItem(item: T)

    fun removeItemToOrder(order: Int)
    fun removeItemToUid(uid: Int)

    fun updateItem(item: T)
}