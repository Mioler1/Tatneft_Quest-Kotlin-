package com.example.tatneft_quest.models

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class ClusterMarkerUser(private var position: LatLng, private var title: String, private var iconPicture: Bitmap) : ClusterItem {

    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String {
        return title
    }

    override fun getSnippet(): String? {
        return null
    }

    fun getIconPicture(): Bitmap {
        return iconPicture
    }

    fun setPosition(position: LatLng) {
        this.position = position
    }

    fun setTitle(title: String) {
        this.title = title
    }
}