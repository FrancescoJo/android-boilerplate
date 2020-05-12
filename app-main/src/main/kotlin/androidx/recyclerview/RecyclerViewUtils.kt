package androidx.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.firstVisibleItemIndex(): Int {
    val layoutManager = layoutManager as? LinearLayoutManager ?: return -1
    return layoutManager.findFirstVisibleItemPosition()
}

fun RecyclerView.lastVisibleItemIndex(): Int {
    val layoutManager = layoutManager as? LinearLayoutManager ?: return -1
    return layoutManager.findLastVisibleItemPosition()
}

fun RecyclerView.getItemCount(): Int = adapter?.itemCount ?: 0
