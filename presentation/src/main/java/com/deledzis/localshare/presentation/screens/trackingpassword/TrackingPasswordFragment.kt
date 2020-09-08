package com.deledzis.localshare.presentation.screens.trackingpassword

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.deledzis.localshare.infrastructure.extensions.drawableCompatFrom
import com.deledzis.localshare.infrastructure.extensions.injectViewModel
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseFragment
import com.deledzis.localshare.presentation.databinding.FragmentTrackingPasswordBinding
import com.deledzis.localshare.presentation.screens.main.MainActivityViewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import timber.log.Timber
import javax.inject.Inject

class TrackingPasswordFragment @Inject constructor() :
    BaseFragment<TrackingPasswordViewModel>(),
    OnMapReadyCallback {

    private lateinit var dataBinding: FragmentTrackingPasswordBinding

    private val args: TrackingPasswordFragmentArgs by navArgs()

    private lateinit var googleMap: GoogleMap
    private lateinit var marker: Marker

    @Inject
    lateinit var mainActivityViewModel: MainActivityViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var trackingPasswordViewModel: TrackingPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trackingPasswordViewModel = injectViewModel(viewModelFactory)
        trackingPasswordViewModel.init(args.password)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tracking_password,
            container,
            false
        )
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.title = args.password.description

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        bindObservers()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap ?: return
        setMapLocation(googleMap)

        updateLocationUI()
    }

    override fun onResume() {
        super.onResume()
        mainActivityViewModel.setInSecondaryLevelFragment(dataBinding.toolbar)
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    googleMap.isMyLocationEnabled = true
                    googleMap.uiSettings?.isMyLocationButtonEnabled = true
                }
            }
        }
        updateLocationUI()
    }

    override fun bindObservers() {
        trackingPasswordViewModel.latLng.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer

            animateMarker(position = it)
        })
    }

    private fun setMapLocation(googleMap: GoogleMap) {
        with(googleMap) {
            val latLng = LatLng(
                args.password.lastCoordinates.lat,
                args.password.lastCoordinates.lng
            )

            val mapMarkerDrawable =
                applicationContext.drawableCompatFrom(
                    if (args.password.active) {
                        R.drawable.ic_map_marker_online
                    } else {
                        R.drawable.ic_map_marker_offline
                    }
                )
            val icon = BitmapDescriptorFactory.fromBitmap(
                mapMarkerDrawable.toBitmap(
                    mapMarkerDrawable.intrinsicWidth * 3,
                    mapMarkerDrawable.intrinsicHeight * 3,
                    null
                )
            )
            uiSettings.isMapToolbarEnabled = true
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isZoomGesturesEnabled = true
            uiSettings.isCompassEnabled = true
            moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f))
            marker = addMarker(
                MarkerOptions()
                    .position(latLng)
                    .icon(icon)
            )
            mapType = GoogleMap.MAP_TYPE_NORMAL
            setOnMapClickListener {

            }
        }
    }

    private fun getLocationPermission() {
        if (
            ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    private fun updateLocationUI() {
        try {
            if (
                ContextCompat.checkSelfPermission(
                    this.applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                googleMap.isMyLocationEnabled = true
                googleMap.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                googleMap.isMyLocationEnabled = false
                googleMap.uiSettings?.isMyLocationButtonEnabled = false
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Timber.e("Exception: ${e.message}")
        }
    }

    private fun animateMarker(position: LatLng) {
        val handler = Handler()
        val start: Long = SystemClock.uptimeMillis()
        val proj: Projection = googleMap.projection
        val startPoint: Point = proj.toScreenLocation(marker.position)
        val startLatLng: LatLng = proj.fromScreenLocation(startPoint)
        val duration: Long = 500
        val interpolator: Interpolator = LinearInterpolator()

        handler.post(object : Runnable {
            override fun run() {
                val elapsed: Long = SystemClock.uptimeMillis() - start
                val t: Float = interpolator.getInterpolation(elapsed.toFloat() / duration)
                val lng = t * position.longitude + (1 - t) * startLatLng.longitude
                val lat = t * position.latitude + (1 - t) * startLatLng.latitude
                marker.position = LatLng(lat, lng)
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16)
                }/* else {
                    marker.isVisible = !hideMarker
                }*/
            }
        })
    }

    companion object {
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1001
    }
}