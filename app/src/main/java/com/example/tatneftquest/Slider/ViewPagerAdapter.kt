package com.example.tatneftquest.Slider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tatneftquest.R

class ViewPagerAdapter(private var title: List<String>, private var ditails: List<String>, private var images: List<Int>) : RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>(){

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val itemDetails: TextView = itemView.findViewById(R.id.tvAbout)
        val itemImage: ImageView = itemView.findViewById(R.id.ivImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false))
    }

    override fun getItemCount(): Int {
       return title.size
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.Pager2ViewHolder, position: Int) {
       holder.itemTitle.text = title[position]
       holder.itemDetails.text = ditails[position]
       holder.itemImage.setImageResource(images[position])
    }
}