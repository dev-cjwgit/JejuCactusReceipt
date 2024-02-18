package com.cjwgit.jejucactusreceipt.model.common

interface ProductModel<T> {
    suspend fun getItem(position: Int): T
    suspend fun getItems(): List<T>

    suspend fun addItem(item: T)

    suspend fun removeItem(position: Int)

    suspend fun updateItem(item: T)

    suspend fun swipe(from: Int, to: Int)
}