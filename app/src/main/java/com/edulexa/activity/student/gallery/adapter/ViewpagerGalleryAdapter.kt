package com.edulexa.activity.student.gallery.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.edulexa.activity.student.fee.fragment.FeeViewpagerFragment
import com.edulexa.activity.student.gallery.fragment.GalleryViewpagerFragment

class ViewpagerGalleryAdapter(fragmentManager: FragmentManager, size:Int) : FragmentPagerAdapter(fragmentManager) {

    var size : Int? = null
    init {
        this.size= size
    }
    override fun getCount(): Int {
        return size!!;
    }

    override fun getItem(position: Int): Fragment {
        return GalleryViewpagerFragment()
    }
}