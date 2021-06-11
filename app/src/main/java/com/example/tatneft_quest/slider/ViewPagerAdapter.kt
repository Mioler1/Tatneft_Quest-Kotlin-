package com.example.tatneft_quest.slider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.tatneft_quest.R
import com.example.tatneft_quest.databinding.FragmentHelpBinding

class ViewPagerAdapter(

    private var context: Context,
    private var onBoardingDataList: List<OnBoardingData>,
) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return onBoardingDataList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_page, null)
        val imageView: ImageView = view.findViewById(R.id.ivImage)
        val title: TextView = view.findViewById(R.id.tvTitle)

        imageView.setImageResource(onBoardingDataList[position].imageUrl)
        title.text = onBoardingDataList[position].title

        container.addView(view)
        return view

    }

    class OnBoardingData(var title: String, var imageUrl: Int)
}