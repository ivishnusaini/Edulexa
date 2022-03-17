package com.edulexa.activity.student.fee.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.edulexa.R
import com.edulexa.activity.student.fee.adapter.ViewpagerFeeAdapter
import com.edulexa.activity.student.homework.model.ViewpagerModel
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
        binding!!.tvGeneralFee.setOnClickListener(this)
        binding!!.tvTransportFee.setOnClickListener(this)
        binding!!.tvQrCode.setOnClickListener(this)
        binding!!.tvUpload.setOnClickListener(this)
        binding!!.tvReceipts.setOnClickListener(this)
    }

    private fun resetTab(position: Int) {
        binding!!.tvGeneralFee.setBackgroundResource(R.drawable.edit_text_bg_25)
        binding!!.tvGeneralFee.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.tvTransportFee.setBackgroundResource(R.drawable.edit_text_bg_25)
        binding!!.tvTransportFee.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        when (position) {
            0 -> {
                binding!!.tvGeneralFee.setBackgroundResource(R.drawable.bg_button_25)
                binding!!.tvGeneralFee.setTextColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.white
                    )
                )
            }
            1 -> {
                binding!!.tvTransportFee.setBackgroundResource(R.drawable.bg_button_25)
                binding!!.tvTransportFee.setTextColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.white
                    )
                )
            }
        }
    }

    private fun setUpViewPager() {
        binding!!.viewPagerFee.adapter =
            ViewpagerFeeAdapter(supportFragmentManager, 2)
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

    private fun visibleMenuOptions(){
        binding!!.cvMenu.visibility = View.VISIBLE
    }

    private fun hideMenuOptions(){
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
        else if (id == R.id.tv_general_fee)
            setViewPagerFromTabClick(0)
        else if (id == R.id.tv_transport_fee)
            setViewPagerFromTabClick(1)
        else if (id == R.id.tv_qr_code)
            hideMenuOptions()
        else if (id == R.id.tv_upload)
            hideMenuOptions()
        else if (id == R.id.tv_receipts)
            hideMenuOptions()
    }
}