package com.culinarix.ui.main.detail

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.culinarix.R
import com.culinarix.data.model.SnipetModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindow (private val context: Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(p0: Marker): View? {
        return null
    }

    override fun getInfoWindow(marker: Marker): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_window,null)

        val nameCulinar = view.findViewById<TextView>(R.id.tv_titlesnipet)
        val addressCul = view.findViewById<TextView>(R.id.tv_addressnipet)
        val ivCulianar = view.findViewById<ImageView>(R.id.iv_snipet)
        val infoWindow = marker.tag as SnipetModel
        val imgUrl = infoWindow.imgUrl

        Glide.with(context)
            .load(imgUrl)
            .override(55,55)
            .centerCrop()
            .placeholder(R.drawable.logo)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("Glide", "Error Loading Image", e)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("Glide", "Image Loaded")
                    if (marker.isInfoWindowShown) {
                        marker.hideInfoWindow()
                        marker.showInfoWindow()
                    }
                    return false
                }

            }).into(ivCulianar)

        nameCulinar.text = infoWindow.name
        addressCul.text = infoWindow.address

        return view
    }

}