package com.cjwgit.jejucactusreceipt.repository.common

interface BaseRepository<T> {
    suspend fun swipeItem(from: Int, to: Int)

    suspend fun getItemToOrder(order: Int): T

    suspend fun getItems(): List<T>

    suspend fun addItem(item: T)

    suspend fun removeItemToOrder(order: Int)

    suspend fun updateItem(item: T)
}