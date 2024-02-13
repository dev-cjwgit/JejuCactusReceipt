package com.cjwgit.jejucactusreceipt.domain

import android.os.Parcelable
import com.cjwgit.jejucactusreceipt.ui.adapter.common.IUniquable
import kotlinx.parcelize.Parcelize

data class AuctionBasketVO(
    val uid: Long,
    val name: String,
    val amount: Long,
    val boxCount: Long,
    val price: Long,
) : IUniquable {
    override fun getUid(): String {
        return uid.toString()
    }
}
