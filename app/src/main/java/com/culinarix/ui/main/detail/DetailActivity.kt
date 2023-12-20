package com.culinarix.ui.main.detail

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import com.culinarix.R
import com.culinarix.data.model.SnipetModel
import com.culinarix.databinding.ActivityDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class DetailActivity : AppCompatActivity(),OnMapReadyCallback {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var gmaps: GoogleMap
    private var placeName:String? = null
    private var descPlace:String? = null
    private var rtgPlace:Double? = null
    private var imgUrl:String? = null
    private var addressPlace:String? = null
    private var addLocation:String? = null
    private var latPlace:Double? = null
    private var longPlace:Double? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)

        placeName = intent.getStringExtra(EXTRA_PLACE_NAME)
        descPlace = intent.getStringExtra(EXTRA_DESC)
        rtgPlace = intent.getDoubleExtra(EXTRA_RATING,5.0)
        imgUrl = intent.getStringExtra(EXTRA_IMGURL)
        addressPlace = intent.getStringExtra(EXTRA_ADDRESS)
        addLocation = intent.getStringExtra(EXTRA_LOCADDRESS)
        latPlace = intent.getDoubleExtra(EXTRA_LAT,0.0)
        longPlace = intent.getDoubleExtra(EXTRA_LONG,0.0)


        binding.tvTempatmakan.text = placeName
        binding.tvDeskripsi.text = descPlace
        binding.ratingbar.rating = rtgPlace!!.toFloat()
        binding.tvRtgbar.text = rtgPlace.toString()
        binding.btnGmaps.setOnClickListener {
            val uri = Uri.parse(addLocation)
            val i = Intent(Intent.ACTION_VIEW,uri)
//            i.setPackage("com.google.android.apps.maps")
            startActivity(i)

        }



    }

    override fun onMapReady(googleMaps: GoogleMap) {
        gmaps = googleMaps
        setMapStyle()

        gmaps.uiSettings.isCompassEnabled = true
        gmaps.uiSettings.isZoomControlsEnabled = true
        gmaps.uiSettings.isMyLocationButtonEnabled = true
        gmaps.setPadding(2,2,2,440)

        val info = SnipetModel(
            name = placeName.toString(),
            address = addressPlace.toString(),
            imgUrl = imgUrl.toString()
        )


        val customInfoWindow = CustomInfoWindow(this)
        gmaps.setInfoWindowAdapter(customInfoWindow)

        val loc = LatLng(latPlace!!, longPlace!!)
        val markerOptions = MarkerOptions()
        markerOptions.position(loc).icon(vectorToBitmap(R.drawable.marker))

        val marker = gmaps.addMarker(markerOptions)
        marker?.tag = info
        marker?.showInfoWindow()

        gmaps.setOnInfoWindowClickListener {
            val uri = Uri.parse(addLocation)
            val i = Intent(Intent.ACTION_VIEW,uri)
//            i.setPackage("com.google.android.apps.maps")
            startActivity(i)

        }

        gmaps.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 12f))
    }

    private fun vectorToBitmap (@DrawableRes id : Int): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(resources,id,null)
        if (vectorDrawable == null){
            Log.e("BitmapHelper", "Resource not found")
            return BitmapDescriptorFactory.defaultMarker()
        }

        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0,0,canvas.width,canvas.height)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun setMapStyle(){
        try {
            val success = gmaps.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.map_style))

            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }

        }catch (e:Resources.NotFoundException){
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }

    companion object{
        var EXTRA_PLACE_NAME = "extra_place_name"
        var EXTRA_DESC = "extra_desc"
        var EXTRA_RATING = "extra_rating"
        var EXTRA_ADDRESS = "extra_address"
        var EXTRA_IMGURL = "extra_imgurl"
        var EXTRA_LOCADDRESS = "extra_locaddress"
        var EXTRA_LAT = "extra_lat"
        var EXTRA_LONG = "extra_long"
        private const val TAG = "MapsActivity"
    }
}