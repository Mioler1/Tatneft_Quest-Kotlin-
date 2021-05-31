package com.example.tatneftquest.Utils

import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup
import android.widget.ImageView
import com.example.tatneftquest.Models.ClusterMarker
import com.example.tatneftquest.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator

class MyClusterManagerRenderer(
    context: Context,
    googleMap: GoogleMap?,
    clusterManager: ClusterManager<ClusterMarker>?,
) :
    DefaultClusterRenderer<ClusterMarker>(context, googleMap, clusterManager) {

    private var iconGenerator: IconGenerator? = IconGenerator(context.applicationContext)
    private var imageView: ImageView = ImageView(context.applicationContext)
    private val markerWidth: Int =
        context.resources.getDimension(R.dimen.custom_marker_image).toInt()
    private val markerHeight: Int =
        context.resources.getDimension(R.dimen.custom_marker_image).toInt()

    init {
        imageView.layoutParams = ViewGroup.LayoutParams(markerWidth, markerHeight)
        val padding: Int = context.resources.getDimension(R.dimen.custom_marker_padding).toInt()
        imageView.setPadding(padding, padding, padding, padding)
        iconGenerator!!.setContentView(imageView)
    }

    override fun onBeforeClusterItemRendered(item: ClusterMarker, markerOptions: MarkerOptions) {
        imageView.setImageResource(item.getIconPicture())
        val icon: Bitmap? = iconGenerator?.makeIcon()
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
    }

    override fun shouldRenderAsCluster(cluster: Cluster<ClusterMarker?>): Boolean {
        return false
    }

    fun setUpdateMarker(clusterMarker: ClusterMarker) {
        val marker:Marker = getMarker(clusterMarker)
        if(marker != null) {
            marker.position = clusterMarker.position
        }
    }
}