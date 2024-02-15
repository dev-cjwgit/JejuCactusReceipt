package com.cjwgit.jejucactusreceipt.model.common

interface ProductModel<T> {
    fun getItem(position: Int): T
    fun getItems(): List<T>

    fun addItem(item: T)

    fun removeItem(position: Int)

    fun updateItem(item: T)

    fun swipe(from: Int, to: Int)
}