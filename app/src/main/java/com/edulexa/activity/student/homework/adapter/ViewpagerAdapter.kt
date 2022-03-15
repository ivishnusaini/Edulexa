package com.edulexa.activity.student.homework.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.edulexa.activity.student.homework.fragment.ViewpagerFragment

class ViewpagerAdapter(fragmentManager: FragmentManager,size:Int) : FragmentPagerAdapter(fragmentManager) {

    var size : Int? = null
    init {
        this.size= size
    }
    override fun getCount(): Int {
        return size!!;
    }

    override fun getItem(position: Int): Fragment {
        return ViewpagerFragment()
    }
}