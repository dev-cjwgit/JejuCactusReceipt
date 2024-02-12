package com.cjwgit.jejucactusreceipt.ui.adapter.common

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(
    private val listener: ItemTouchHelperListener
) : ItemTouchHelper.Callback() {
    private var fromPosition: Int = -1
    private var toPosition: Int = -1
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END

        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        if (fromPosition == -1) {
            fromPosition = viewHolder.adapterPosition
        }
        toPosition = target.adapterPosition
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onItemSwipe(viewHolder.adapterPosition)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        if (fromPosition != -1 && toPosition != -1 && fromPosition != toPosition) {
            listener.onItemMove(fromPosition, toPosition)
        }

        // 이동 작업이 끝났으므로 초기화
        fromPosition = -1
        toPosition = -1
    }
}