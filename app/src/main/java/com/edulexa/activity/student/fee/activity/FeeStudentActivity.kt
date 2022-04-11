package com.edulexa.activity.student.fee.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.edulexa.R
import com.edulexa.activity.student.fee.adapter.FeeTypeAdapter
import com.edulexa.activity.student.fee.adapter.ViewpagerFeeAdapter
import com.edulexa.databinding.ActivityFeeStudentBinding

class FeeStudentActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityFeeStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeeStudentBinding.inflate(layoutInflater)
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
        binding!!.ivMenu.setOnClickListener(this)
        binding!!.topLay.setOnClickListener(this)
        binding!!.schoolFeeAttendanceLay.setOnClickListener(this)
        binding!!.examFeeAttendanceLay.setOnClickListener(this)
        binding!!.activityFeeAttendanceLay.setOnClickListener(this)
        binding!!.otherFeeAttendanceLay.setOnClickListener(this)
        binding!!.tvQrCode.setOnClickListener(this)
        binding!!.tvUpload.setOnClickListener(this)
        binding!!.tvReceipts.setOnClickListener(this)
    }


    private fun resetTab(position: Int) {
        binding!!.tvSchoolFee.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewSchoolFee.visibility = View.GONE
        binding!!.tvExamFee.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewExamFee.visibility = View.GONE
        binding!!.tvActivityFee.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewActivityFee.visibility = View.GONE
        binding!!.tvOtherFee.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewOtherFee.visibility = View.GONE

        when (position) {
            0 -> {
                binding!!.tvSchoolFee.setTextColor(ContextCompat.getColor(mActivity!!, R.color.red))
                binding!!.viewSchoolFee.visibility = View.VISIBLE
                binding!!.viewSchoolFee.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.red
                    )
                )
                val x = binding!!.tvSchoolFee.left
                val y = binding!!.tvSchoolFee.top
                binding!!.attendanceHorizontalScrollView.smoothScrollTo(x, y)
            }
            1 -> {
                binding!!.tvExamFee.setTextColor(ContextCompat.getColor(mActivity!!, R.color.red))
                binding!!.viewExamFee.visibility = View.VISIBLE
                binding!!.viewExamFee.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.red
                    )
                )
                val x = binding!!.tvExamFee.left
                val y = binding!!.tvExamFee.top
                binding!!.attendanceHorizontalScrollView.smoothScrollTo(x, y)
            }
            2 -> {
                binding!!.tvActivityFee.setTextColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.red
                    )
                )
                binding!!.viewActivityFee.visibility = View.VISIBLE
                binding!!.viewActivityFee.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.red
                    )
                )
                val x = binding!!.tvActivityFee.left
                val y = binding!!.tvActivityFee.top
                binding!!.attendanceHorizontalScrollView.smoothScrollTo(x, y)
            }
            3 -> {
                binding!!.tvOtherFee.setTextColor(ContextCompat.getColor(mActivity!!, R.color.red))
                binding!!.viewOtherFee.visibility = View.VISIBLE
                binding!!.viewOtherFee.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.red
                    )
                )
                val x = binding!!.tvOtherFee.right
                val y = binding!!.tvOtherFee.top
                binding!!.attendanceHorizontalScrollView.smoothScrollTo(x, y)
            }
        }
    }

    private fun setUpViewPager() {
        binding!!.viewPagerFee.adapter =
            ViewpagerFeeAdapter(supportFragmentManager, 4)
        binding!!.viewPagerFee.addOnPageChangeListener(object :
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

    fun setViewPagerFromTabClick(position: Int) {
        binding!!.viewPagerFee.setCurrentItem(position)
    }

    private fun visibleMenuOptions() {
        binding!!.cvMenu.visibility = View.VISIBLE
    }

    private fun hideMenuOptions() {
        binding!!.cvMenu.visibility = View.GONE
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.iv_menu)
            visibleMenuOptions()
        else if (id == R.id.top_lay)
            hideMenuOptions()
        else if (id == R.id.school_fee_attendance_lay)
            setViewPagerFromTabClick(0)
        else if (id == R.id.exam_fee_attendance_lay)
            setViewPagerFromTabClick(1)
        else if (id == R.id.activity_fee_attendance_lay)
            setViewPagerFromTabClick(2)
        else if (id == R.id.other_fee_attendance_lay)
            setViewPagerFromTabClick(3)
        else if (id == R.id.tv_qr_code)
            hideMenuOptions()
        else if (id == R.id.tv_upload)
            hideMenuOptions()
        else if (id == R.id.tv_receipts)
            hideMenuOptions()
    }
}