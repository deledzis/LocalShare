package com.deledzis.localshare.presentation.screens.locationpasswords

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.deledzis.localshare.domain.model.LocationPassword
import com.deledzis.localshare.infrastructure.extensions.autoNotify
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.databinding.ItemLocationPasswordBinding
import javax.inject.Inject
import kotlin.properties.Delegates

class LocationPasswordsAdapter @Inject constructor(val listener: ILocationPasswordActionsHandler) :
    RecyclerView.Adapter<LocationPasswordsAdapter.ViewHolder>() {
    var locationPasswords: List<LocationPassword> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n ->
            o.id == n.id && o.password == n.password && o.description == n.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemLocationPasswordBinding>(
            inflater,
            R.layout.item_location_password,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = locationPasswords.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(locationPasswords[position])
    }

    inner class ViewHolder(private val binding: ItemLocationPasswordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LocationPassword) = with(binding) {
            password = item
            controller = listener
        }
    }
}