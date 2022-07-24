package com.edulexa.activity.student.live_classes.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.edulexa.activity.student.fee.fragment.FeeViewpagerFragment
import com.edulexa.activity.student.live_classes.fragment.CompletedLiveClassFragment
import com.edulexa.activity.student.live_classes.fragment.OnGoingLiveClassFragment
import com.edulexa.activity.student.live_classes.fragment.UpcomingLiveClassFragment

class LiveClassesViewpagerAdapter(fragmentManager: FragmentManager, size:Int) : FragmentPagerAdapter(fragmentManager) {

    var size : Int? = null
    init {
        this.size= size
    }
    override fun getCount(): Int {
        return size!!;
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> {
                return UpcomingLiveClassFragment()
            }
            1 -> {
                return OnGoingLiveClassFragment()
            }
            else -> {
                return CompletedLiveClassFragment()
            }
        }
    }
}