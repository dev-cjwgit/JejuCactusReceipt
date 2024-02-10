package com.cjwgit.jejucactusreceipt.model.common

interface BasketModel<T> {
    fun addItem(item: T)

    fun removeItem(pos: Int)
    fun removeItem(item: T)


    fun getItems(): List<T>
    fun getItem(pos: Int): T

    fun getSize(): Int

    fun clear()

    // 두 메소드는 ISP 륾 만족 하지 못 했지만, 필요 없는 클래스가 늘어 나는 것을 방지 하기 위해 만들었다.
    // 추후 기능 확장 시 ISP 를 만족 하도록 개발 해야 한다.
    fun getTotalPrice(): Long
    fun getTotalBoxCount(): Long
}