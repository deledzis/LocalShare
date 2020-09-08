package com.deledzis.localshare.presentation.screens.trackingpasswords

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.deledzis.localshare.domain.model.TrackingPassword
import com.deledzis.localshare.infrastructure.extensions.autoNotify
import com.deledzis.localshare.infrastructure.extensions.drawableCompatFrom
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.databinding.ItemTrackingPasswordBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject
import kotlin.properties.Delegates

class TrackingPasswordsAdapter @Inject constructor(
    private val applicationContext: Context
) : RecyclerView.Adapter<TrackingPasswordsAdapter.ViewHolder>() {

    var listener: ITrackingPasswordActionsHandler? = null

    var trackingPasswords: List<TrackingPassword> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n ->
            o.password == n.password &&
                    o.description == n.description &&
                    o.active == n.active &&
                    o.lastCoordinates.lat == n.lastCoordinates.lat &&
                    o.lastCoordinates.lng == n.lastCoordinates.lng
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemTrackingPasswordBinding>(
            inflater,
            R.layout.item_tracking_password,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = trackingPasswords.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(trackingPasswords[position], position)
    }

    inner class ViewHolder(private val binding: ItemTrackingPasswordBinding) :
        RecyclerView.ViewHolder(binding.root), OnMapReadyCallback {

        private val mapView: MapView = binding.mapPreview
        private var pos: Int = -1
        private lateinit var password: TrackingPassword
        private lateinit var map: GoogleMap
        private lateinit var marker: Marker
        private lateinit var latLng: LatLng

        init {
            with(mapView) {
                // Initialise the MapView
                onCreate(null)
                // Set the map ready callback to receive the GoogleMap object
                getMapAsync(this@ViewHolder)
            }
        }

        override fun onMapReady(googleMap: GoogleMap?) {
            MapsInitializer.initialize(applicationContext)
            // If map is not initialised properly
            map = googleMap ?: return
            setMapLocation()
        }

        private fun setMapLocation() {
            if (!::map.isInitialized) return
            with(map) {
                val mapMarkerDrawable =
                    applicationContext.drawableCompatFrom(
                        if (password.active) {
                            R.drawable.ic_map_marker_online
                        } else {
                            R.drawable.ic_map_marker_offline
                        }
                    )
                val icon = BitmapDescriptorFactory.fromBitmap(
                    mapMarkerDrawable.toBitmap(
                        mapMarkerDrawable.intrinsicWidth * 2,
                        mapMarkerDrawable.intrinsicHeight * 2,
                        null
                    )
                )
                uiSettings.isMapToolbarEnabled = false
                uiSettings.isZoomControlsEnabled = true
                moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f))
                marker = addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .icon(icon)
                )
                mapType = GoogleMap.MAP_TYPE_NORMAL
                setOnMapClickListener {
                    listener?.handleOnMapClick(password = password, position = pos)
                }
            }
        }

        fun bind(item: TrackingPassword, pos: Int) {
            with(binding) {
                password = item
                position = pos
                controller = listener
            }

            this.password = item
            this.pos = pos
            this.latLng = LatLng(
                item.lastCoordinates.lat,
                item.lastCoordinates.lng
            )
            this.mapView.tag = this

            // We need to call setMapLocation from here because RecyclerView might use the
            // previously loaded maps
            setMapLocation()
        }
    }
}