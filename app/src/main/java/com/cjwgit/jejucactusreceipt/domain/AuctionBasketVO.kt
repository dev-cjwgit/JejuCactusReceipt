package com.cjwgit.jejucactusreceipt.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuctionBasketVO(
    val uid: Long,
    val name: String,
    val amount: Long,
    val boxCount: Long,
    val price: Long,
) : Parcelable
