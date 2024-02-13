package com.cjwgit.jejucactusreceipt.domain

import com.cjwgit.jejucactusreceipt.ui.adapter.common.IUniquable

data class CactusEntity(
    val name: String,
    val price: Long,
    val uid: Long = -1,
    val order: Long = -1,
) : IUniquable {
    override fun getUid(): String {
        return uid.toString()
    }

}
