package com.deledzis.localshare.infrastructure.binding

import android.os.Build
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

@Suppress("UNCHECKED_CAST")
@BindingAdapter(value = ["layoutManager"])
fun setRecyclerViewProperties(recyclerView: RecyclerView, manager: String) {
    when (manager.toLowerCase(Locale.getDefault())) {
        "linear" -> recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        "grid" -> recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 3)
    }
}

@BindingAdapter("nested_scrolling")
fun setRecyclerViewNestedScrolling(recyclerView: RecyclerView, enabled: Boolean) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        if (!enabled) {
            recyclerView.onFlingListener = object : RecyclerView.OnFlingListener() {
                override fun onFling(velocityX: Int, velocityY: Int): Boolean {
                    recyclerView.dispatchNestedFling(velocityX.toFloat(), velocityY.toFloat(), false)
                    return false
                }
            }
        }
    }
    recyclerView.isNestedScrollingEnabled = enabled
}