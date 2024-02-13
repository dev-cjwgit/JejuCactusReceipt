package com.cjwgit.jejucactusreceipt.domain

import com.cjwgit.jejucactusreceipt.ui.adapter.common.IUniquable

data class CactusBasketVO(
    val uid: Long,
    val name: String,
    val price: Long,
    val boxCount: Long,
    val total: Long
) : IUniquable {
    override fun getUid(): String {
        return uid.toString()
    }

}
