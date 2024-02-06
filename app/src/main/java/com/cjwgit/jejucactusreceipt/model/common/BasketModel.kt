package com.cjwgit.jejucactusreceipt.model.common

interface BasketModel<T> {
    fun addItem(item: T)

    fun removeItem(pos: Int)
    fun removeItem(item: T)

    fun getItems(): List<T>
    fun getItem(pos: Int): T

    fun clear()
}