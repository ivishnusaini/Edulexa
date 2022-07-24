package com.edulexa.activity.student.live_classes.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.edulexa.R
import com.edulexa.activity.student.live_classes.adapter.LiveClassesViewpagerAdapter
import com.edulexa.databinding.ActivityLiveClassesStudentBinding

class LiveClassesActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityLiveClassesStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveClassesStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        setUpViewPager()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.liveClassUpcomingLay.setOnClickListener(this)
        binding!!.liveClassOngoingLay.setOnClickListener(this)
        binding!!.liveClassCompletedLay.setOnClickListener(this)
    }

    private fun setUpViewPager() {
        binding!!.viewPagerLiveClass.adapter =
            LiveClassesViewpagerAdapter(supportFragmentManager, 3)
        binding!!.viewPagerLiveClass.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                resetTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    private fun resetTab(position: Int) {
        binding!!.tvLiveClassUpcoming.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewLiveClassUpcoming.visibility = View.GONE
        binding!!.tvLiveClassOngoing.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewLiveClassOngoing.visibility = View.GONE
        binding!!.tvLiveClassCompleted.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewLiveClassCompleted.visibility = View.GONE

        when (position) {
            0 -> {
                binding!!.tvLiveClassUpcoming.setTextColor(ContextCompat.getColor(mActivity!!, R.color.colorPrimary))
                binding!!.viewLiveClassUpcoming.visibility = View.VISIBLE
            }
            1 -> {
                binding!!.tvLiveClassOngoing.setTextColor(ContextCompat.getColor(mActivity!!, R.color.colorPrimary))
                binding!!.viewLiveClassOngoing.visibility = View.VISIBLE
            }
            2 -> {
                binding!!.tvLiveClassCompleted.setTextColor(ContextCompat.getColor(mActivity!!, R.color.colorPrimary))
                binding!!.viewLiveClassCompleted.visibility = View.VISIBLE
            }
        }
    }



    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.live_class_upcoming_lay)
            resetTab(0)
        else if (id == R.id.live_class_ongoing_lay)
            resetTab(1)
        else if (id == R.id.live_class_completed_lay)
            resetTab(2)
    }

}