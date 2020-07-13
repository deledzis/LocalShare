package com.deledzis.localshare.presentation.screens.trackpasswords

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.deledzis.localshare.domain.model.LocationPassword
import com.deledzis.localshare.infrastructure.extensions.autoNotify
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.databinding.ItemTrackPasswordBinding
import javax.inject.Inject
import kotlin.properties.Delegates

class TrackPasswordsAdapter @Inject constructor() :
    RecyclerView.Adapter<TrackPasswordsAdapter.ViewHolder>() {

    var listener: ITrackPasswordActionsHandler? = null

    var locationPasswords: List<LocationPassword> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n ->
            o.password == n.password &&
                    o.description == n.description &&
                    o.active == n.active &&
                    o.ownerId == n.ownerId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemTrackPasswordBinding>(
            inflater,
            R.layout.item_track_password,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = locationPasswords.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(locationPasswords[position], position)
    }

    inner class ViewHolder(private val binding: ItemTrackPasswordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LocationPassword, pos: Int) = with(binding) {
            password = item
            position = pos
            viewModel = listener
        }
    }
}