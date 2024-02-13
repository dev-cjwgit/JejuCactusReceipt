package com.cjwgit.jejucactusreceipt.ui.adapter.common

interface IUniquable {
    fun getUid(): String

    override fun equals(other: Any?): Boolean
}