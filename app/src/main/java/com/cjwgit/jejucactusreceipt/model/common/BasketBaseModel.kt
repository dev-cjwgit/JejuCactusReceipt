package com.cjwgit.jejucactusreceipt.model.common

abstract class BasketBaseModel<T> : BasketModel<T> {
    companion object {
        const val MAX_ITEM_SIZE = 24
    }

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

    /**
     * Read Only
     */
    override fun getItems(): List<T> {
        // 데이터를 가져가서 변경해도 원본이 수정되지 않게 처리 새로운 인스턴스를 반환
        return ArrayList(items)
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