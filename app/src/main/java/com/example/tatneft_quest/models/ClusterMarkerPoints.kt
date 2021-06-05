package com.example.tatneft_quest.models

import android.media.Image
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class ClusterMarkerPoints(private var position: LatLng, private var iconPicture: Int) :
    ClusterItem {

    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String? {
        return null
    }

    override fun getSnippet(): String? {
        return null
    }

    fun getIconPicture(): Int {
        return iconPicture
    }

    fun setPosition(position: LatLng) {
        this.position = position
    }
}