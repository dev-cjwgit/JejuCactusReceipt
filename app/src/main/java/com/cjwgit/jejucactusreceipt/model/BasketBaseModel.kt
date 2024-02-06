package com.cjwgit.jejucactusreceipt.model

import com.cjwgit.jejucactusreceipt.model.common.BasketModel

class BasketBaseModel<T> : BasketModel<T> {
    private val MAX_ITEM_SIZE = 24

    private val items: ArrayList<T> = arrayListOf()
    override fun addItem(item: T) {

        items.add(item)
    }

    override fun removeItem(pos: Int) {
        items.removeAt(pos)
    }

    override fun removeItem(item: T) {
        items.remove(item)
    }

    override fun getItems(): List<T> {
        return items
    }

    override fun getItem(pos: Int): T {
        return items[pos]
    }

    override fun getSize(): Int {
        return items.size
    }

    override fun clear() {
        items.clear()
    }

}