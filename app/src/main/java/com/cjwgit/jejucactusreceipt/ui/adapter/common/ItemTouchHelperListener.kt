package com.cjwgit.jejucactusreceipt.ui.adapter.common

interface ItemTouchHelperListener {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean

    fun onItemSwipe(position: Int)
}