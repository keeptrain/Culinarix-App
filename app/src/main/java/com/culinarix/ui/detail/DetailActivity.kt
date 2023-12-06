package com.culinarix.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.culinarix.R
import com.example.culinarix.databinding.ActivityDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailActivity : AppCompatActivity(),OnMapReadyCallback {
    private lateinit var binding : ActivityDetailBinding
    private lateinit var gmaps : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding.tempatMakan.text = "Test Lokasi"
        binding.alamat.text = "Jl.Test Location No.10"
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gmaps = googleMap

        gmaps.uiSettings.isCompassEnabled = true
        gmaps.uiSettings.isZoomControlsEnabled = true
        gmaps.uiSettings.isMyLocationButtonEnabled = true

        gmaps.mapType = GoogleMap.MAP_TYPE_NORMAL

        val testLocation = LatLng(-6.8957643, 107.6338462)
        gmaps.addMarker(
            MarkerOptions()
                .position(testLocation)
                .title("Test Loacation")
                .snippet("Jl.Test Location No.10")
        )

        gmaps.animateCamera(CameraUpdateFactory.newLatLngZoom(testLocation,20f))
    }
}
