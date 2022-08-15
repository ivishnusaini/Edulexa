package com.edulexa.activity.staff.online_exam.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.edulexa.R
import com.edulexa.activity.staff.online_exam.adapter.ViewpagerAddExamAdapter
import com.edulexa.activity.student.fee.adapter.ViewpagerFeeAdapter
import com.edulexa.databinding.ActivityAddExamStaffBinding
import com.edulexa.databinding.ActivityOnlineExamStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import java.util.*

class AddExamActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityAddExamStaffBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExamStaffBinding.inflate(layoutInflater)
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
        binding!!.practiceExamLay.setOnClickListener(this)
        binding!!.onlineExamLay.setOnClickListener(this)
    }

    private fun setUpViewPager(){
        binding!!.viewPagerOnlineExam.adapter =
            ViewpagerAddExamAdapter(supportFragmentManager, 2)
        binding!!.viewPagerOnlineExam.addOnPageChangeListener(object :
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
        binding!!.tvPracticeExam.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.secondary_text_color
            )
        )
        binding!!.viewPracticeExam.visibility = View.INVISIBLE
        binding!!.tvOnlineExam.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.secondary_text_color
            )
        )
        binding!!.viewOnlineExam.visibility = View.INVISIBLE


        when (position) {
            0 -> {
                binding!!.tvPracticeExam.setTextColor(ContextCompat.getColor(mActivity!!, R.color.colorPrimary))
                binding!!.viewPracticeExam.visibility = View.VISIBLE
                binding!!.viewPracticeExam.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.colorPrimary
                    )
                )
            }
            1 -> {
                binding!!.tvOnlineExam.setTextColor(ContextCompat.getColor(mActivity!!, R.color.colorPrimary))
                binding!!.viewOnlineExam.visibility = View.VISIBLE
                binding!!.viewOnlineExam.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.colorPrimary
                    )
                )
            }
        }
    }

    private fun setViewPagerFromTabClick(position: Int){
        binding!!.viewPagerOnlineExam.currentItem = position
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.practice_exam_lay)
            setViewPagerFromTabClick(0)
        else if (id == R.id.online_exam_lay)
            setViewPagerFromTabClick(1)
    }
}