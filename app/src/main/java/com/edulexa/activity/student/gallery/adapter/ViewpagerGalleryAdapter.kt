package com.edulexa.activity.student.gallery.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.edulexa.activity.student.gallery.fragment.*

class ViewpagerGalleryAdapter(fragmentManager: FragmentManager, size: Int) :
    FragmentPagerAdapter(fragmentManager) {

    var size: Int? = null

    init {
        this.size = size
    }

    override fun getCount(): Int {
        return size!!;
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return GalleryViewpagerAllFragment.getInstance()!!
            }
            1 -> {
                return GalleryViewpagerVideoFragment.getInstance()!!
            }
            2 -> {
                return GalleryViewpagerImageFragment.getInstance()!!
            }
            3 -> {
                return GalleryViewpagerDocumentFragment.getInstance()!!
            }
            else -> {
                return GalleryViewpagerLinkFragment.getInstance()!!
            }
        }

    }
}