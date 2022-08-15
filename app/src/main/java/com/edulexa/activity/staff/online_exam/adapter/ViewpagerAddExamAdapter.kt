package com.edulexa.activity.staff.online_exam.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.edulexa.activity.staff.online_exam.fragment.OnlineExamFragment
import com.edulexa.activity.staff.online_exam.fragment.PracticeExamFragment

class ViewpagerAddExamAdapter(fragmentManager: FragmentManager, size: Int) :
    FragmentPagerAdapter(fragmentManager) {

    var size: Int? = null

    init {
        this.size = size
    }

    override fun getCount(): Int {
        return size!!;
    }

    override fun getItem(position: Int): Fragment {
        return if (position == 0)
            PracticeExamFragment()
        else OnlineExamFragment()
    }
}