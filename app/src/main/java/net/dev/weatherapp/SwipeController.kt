package net.dev.weatherapp

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


internal class SwipeController(val onSwipedSuccess : (Int)->Unit)
    : ItemTouchHelper.Callback() {
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
        return makeMovementFlags(
            0,
            LEFT or RIGHT
        )

    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
        return false;

    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        onSwipedSuccess(viewHolder.adapterPosition)
    }

}