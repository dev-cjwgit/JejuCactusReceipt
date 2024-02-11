package com.cjwgit.jejucactusreceipt.domain

data class CactusEntity(
    val name: String,
    val price: Long,
    val uid: Long = -1,
    val order: Long = -1,
)
