package com.cjwgit.jejucactusreceipt.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CactusBasketVO(
    val uid: Long,
    val name: String,
    val price: Long,
    val boxCount: Long,
    val total: Long
) : Parcelable
