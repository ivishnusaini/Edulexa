package com.edulexa.activity.student.class_timetable.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.edulexa.activity.student.class_timetable.fragment.ClassTimetableFragment
import com.edulexa.activity.student.class_timetable.model.ClassTimetableModel
import com.edulexa.activity.student.gallery.fragment.*

class ViewpagerClassTimetableAdapter(fragmentManager: FragmentManager, classTimeTableList : List<ClassTimetableModel>, size: Int) :
    FragmentPagerAdapter(fragmentManager) {

    var size: Int? = null
    var classTimeTableList : List<ClassTimetableModel>? = null

    init {
        this.size = size
        this.classTimeTableList = classTimeTableList
    }

    override fun getCount(): Int {
        return size!!;
    }

    override fun getItem(position: Int): Fragment {
        return ClassTimetableFragment.newInstance(classTimeTableList!!.get(position).getDayWiseList()!!)
    }
}