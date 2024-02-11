package com.cjwgit.jejucactusreceipt.domain

data class AuctionEntity(
    val name: String,
    // 한 박스에 선인장 몇개 있는지
    val amount: Long,
    val price: Long,
    val uid: Long = -1,
    val order: Long = -1,
)
