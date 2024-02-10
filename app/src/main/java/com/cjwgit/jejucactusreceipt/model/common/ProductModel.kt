package com.cjwgit.jejucactusreceipt.model.common

interface ProductModel<T> {
    fun getItems(): List<T>

    fun addItem(item: T)

    fun removeItem(item: T)

    fun updateItem(item: T)

    fun swipe(from: T, to: T)
}