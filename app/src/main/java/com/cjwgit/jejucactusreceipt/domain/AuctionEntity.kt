package com.cjwgit.jejucactusreceipt.domain

import com.cjwgit.jejucactusreceipt.ui.adapter.common.IUniquable

data class AuctionEntity(
    val name: String,
    // 한 박스에 선인장 몇개 있는지
    val amount: Long,
    val price: Long,
    val uid: Long = -1,
    val order: Long = -1,
) : IUniquable {
    override fun getUid(): String {
        return uid.toString()
    }

}
