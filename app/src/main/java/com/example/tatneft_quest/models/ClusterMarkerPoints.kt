package com.example.tatneft_quest.models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class ClusterMarkerPoints(
    private var id: Int,
    private var position: LatLng,
    private var title: String,
    private var iconPicture: Int,
    private var active: Boolean,
) : ClusterItem {

    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String {
        return title
    }

    override fun getSnippet(): String? {
        return null
    }

    fun getId(): Int {
        return id
    }

    fun getActive(): Boolean {
        return active
    }

    fun getIconPicture(): Int {
        return iconPicture
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun setActive(active: Boolean) {
        this.active = active
    }

    fun setPosition(position: LatLng) {
        this.position = position
    }

    fun setTitle(title: String) {
        this.title = title
    }
}