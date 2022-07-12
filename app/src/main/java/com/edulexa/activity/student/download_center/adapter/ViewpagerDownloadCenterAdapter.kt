package com.edulexa.activity.student.download_center.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.edulexa.activity.student.class_timetable.fragment.ClassTimetableFragment
import com.edulexa.activity.student.class_timetable.model.ClassTimetableModel
import com.edulexa.activity.student.download_center.fragment.DownloadCenterViewpagerFragment
import com.edulexa.activity.student.download_center.model.DownloadCenterModel
import com.edulexa.activity.student.fee.fragment.FeeViewpagerFragment

class ViewpagerDownloadCenterAdapter(fragmentManager: FragmentManager, downloadCenterModelList : List<DownloadCenterModel>, size:Int) : FragmentPagerAdapter(fragmentManager) {

    var size : Int? = null
    var downloadCenterModelList : List<DownloadCenterModel>? = null
    init {
        this.size= size
        this.downloadCenterModelList= downloadCenterModelList
    }
    override fun getCount(): Int {
        return size!!;
    }

    override fun getItem(position: Int): Fragment {
        return DownloadCenterViewpagerFragment.newInstance(downloadCenterModelList!!.get(position).getTypeWiseList()!!)!!
    }
}