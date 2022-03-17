package com.edulexa.activity.student.homework.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.edulexa.R
import com.edulexa.activity.student.homework.model.ViewpagerModel
import com.edulexa.activity.student.homework.adapter.HomeworkTabAdapter
import com.edulexa.activity.student.homework.adapter.ViewpagerAdapter
import com.edulexa.databinding.ActivityHomeworkStudentBinding

class HomeworkStudentActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityHomeworkStudentBinding? = null
    private var tabNameList: List<ViewpagerModel>? = ArrayList()
    var homeworkTabAdapter: HomeworkTabAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeworkStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        setUpTabLayoutData()
        setUpViewPager()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun setUpTabLayoutData() {
        binding!!.recyclerViewTabHomework.layoutManager =
            LinearLayoutManager(mActivity!!, RecyclerView.HORIZONTAL, false)
        (tabNameList as ArrayList<ViewpagerModel>).clear()
        (tabNameList as ArrayList<ViewpagerModel>).add(ViewpagerModel("English", true))
        (tabNameList as ArrayList<ViewpagerModel>).add(ViewpagerModel("Hindi", false))
        (tabNameList as ArrayList<ViewpagerModel>).add(ViewpagerModel("Maths", false))
        (tabNameList as ArrayList<ViewpagerModel>).add(ViewpagerModel("Science", false))
        (tabNameList as ArrayList<ViewpagerModel>).add(ViewpagerModel("Social Science", false))
        (tabNameList as ArrayList<ViewpagerModel>).add(ViewpagerModel("Sanskrit", false))
        homeworkTabAdapter = HomeworkTabAdapter(mActivity!!, tabNameList)
        binding!!.recyclerViewTabHomework.adapter = homeworkTabAdapter
    }

    private fun setUpViewPager() {
        binding!!.viewPagerHomework.adapter =
            ViewpagerAdapter(supportFragmentManager, tabNameList!!.size)
        binding!!.viewPagerHomework.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                for (model in tabNameList!!) {
                    model.setSelectStatus(false)
                }
                tabNameList!!.get(position).setSelectStatus(true)
                if (homeworkTabAdapter != null) {
                    homeworkTabAdapter!!.updateList(tabNameList)
                    binding!!.recyclerViewTabHomework.layoutManager!!.scrollToPosition(position)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    fun setViewPagerFromTabClick(position: Int) {
        binding!!.viewPagerHomework.setCurrentItem(position)
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}