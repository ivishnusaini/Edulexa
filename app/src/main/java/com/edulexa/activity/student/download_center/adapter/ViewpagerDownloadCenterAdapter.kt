package com.edulexa.activity.student.download_center.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.edulexa.activity.student.download_center.fragment.DownloadCenterViewpagerFragment
import com.edulexa.activity.student.fee.fragment.FeeViewpagerFragment

class ViewpagerDownloadCenterAdapter(fragmentManager: FragmentManager, size:Int) : FragmentPagerAdapter(fragmentManager) {

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
                return DownloadCenterViewpagerFragment.newInstance("Assignments")!!
            }
            1 -> {
                return DownloadCenterViewpagerFragment.newInstance("Study Material")!!
            }
            2 -> {
                return DownloadCenterViewpagerFragment.newInstance("Syllabus")!!
            }
            3 -> {
                return DownloadCenterViewpagerFragment.newInstance("Video URL")!!
            }
            else ->{
                return DownloadCenterViewpagerFragment.newInstance("Other Download")!!
            }
        }

    }
}