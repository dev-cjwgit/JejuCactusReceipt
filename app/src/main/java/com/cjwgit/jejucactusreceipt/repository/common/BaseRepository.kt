package com.cjwgit.jejucactusreceipt.repository.common

interface BaseRepository<T> {
    fun swipeItem(from: Int, to: Int)

    fun getItemToOrder(order: Int): T

    fun getItems(): List<T>

    fun addItem(item: T)

    fun removeItemToOrder(order: Int)

    fun updateItem(item: T)
}