package com.deledzis.localshare.infrastructure.binding

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