package com.edulexa.activity.student.download_center.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.edulexa.R
import com.edulexa.activity.student.download_center.adapter.ViewpagerDownloadCenterAdapter
import com.edulexa.activity.student.fee.adapter.ViewpagerFeeAdapter
import com.edulexa.databinding.ActivityDownloadCenterStudentBinding
import com.edulexa.databinding.ActivityFeeStudentBinding

class DownloadCenterActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityDownloadCenterStudentBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloadCenterStudentBinding.inflate(layoutInflater)
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
        binding!!.imagesDownloadCenterLay.setOnClickListener(this)
        binding!!.audioDownloadCenterLay.setOnClickListener(this)
        binding!!.documentDownloadCenterLay.setOnClickListener(this)
        binding!!.videoDownloadCenterLay.setOnClickListener(this)
        binding!!.otherDownloadCenterLay.setOnClickListener(this)
    }

    private fun setUpViewPager() {
        binding!!.viewPagerDownloadCenter.adapter =
            ViewpagerDownloadCenterAdapter(supportFragmentManager, 5)
        binding!!.viewPagerDownloadCenter.offscreenPageLimit = 1
        binding!!.viewPagerDownloadCenter.addOnPageChangeListener(object :
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
        binding!!.tvImagesDownloadCenter.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewImagesDownloadCenter.visibility = View.GONE
        binding!!.tvAudioDownloadCenter.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewAudioDownloadCenter.visibility = View.GONE
        binding!!.tvDocumentDownloadCenter.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewDocumentDownloadCenter.visibility = View.GONE
        binding!!.tvVideoDownloadCenter.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewVideoDownloadCenter.visibility = View.GONE

        binding!!.tvOtherDownloadCenter.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewOtherDownloadCenter.visibility = View.GONE

        when (position) {
            0 -> {
                binding!!.tvImagesDownloadCenter.setTextColor(ContextCompat.getColor(mActivity!!, R.color.red))
                binding!!.viewImagesDownloadCenter.visibility = View.VISIBLE
                binding!!.viewImagesDownloadCenter.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.red
                    )
                )
                val x = binding!!.tvImagesDownloadCenter.left
                val y = binding!!.tvImagesDownloadCenter.top
                binding!!.downloadCenterHorizontalLay.smoothScrollTo(x, y)
            }
            1 -> {
                binding!!.tvAudioDownloadCenter.setTextColor(ContextCompat.getColor(mActivity!!, R.color.red))
                binding!!.viewAudioDownloadCenter.visibility = View.VISIBLE
                binding!!.viewAudioDownloadCenter.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.red
                    )
                )
                val x = binding!!.tvAudioDownloadCenter.left
                val y = binding!!.tvAudioDownloadCenter.top
                binding!!.downloadCenterHorizontalLay.smoothScrollTo(x, y)
            }
            2 -> {
                binding!!.tvDocumentDownloadCenter.setTextColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.red
                    )
                )
                binding!!.viewDocumentDownloadCenter.visibility = View.VISIBLE
                binding!!.viewDocumentDownloadCenter.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.red
                    )
                )
                val x = binding!!.tvDocumentDownloadCenter.left
                val y = binding!!.tvDocumentDownloadCenter.top
                binding!!.downloadCenterHorizontalLay.smoothScrollTo(x, y)
            }
            3 -> {
                binding!!.tvVideoDownloadCenter.setTextColor(ContextCompat.getColor(mActivity!!, R.color.red))
                binding!!.viewVideoDownloadCenter.visibility = View.VISIBLE
                binding!!.viewVideoDownloadCenter.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.red
                    )
                )
                val x = binding!!.tvVideoDownloadCenter.right
                val y = binding!!.tvVideoDownloadCenter.top
                binding!!.downloadCenterHorizontalLay.smoothScrollTo(x, y)
            }
            4 -> {
                binding!!.tvOtherDownloadCenter.setTextColor(ContextCompat.getColor(mActivity!!, R.color.red))
                binding!!.viewOtherDownloadCenter.visibility = View.VISIBLE
                binding!!.viewOtherDownloadCenter.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.red
                    )
                )
                val x = binding!!.tvOtherDownloadCenter.right
                val y = binding!!.tvOtherDownloadCenter.top
                binding!!.downloadCenterHorizontalLay.smoothScrollTo(x, y)
            }
        }
    }

    fun setViewPagerFromTabClick(position: Int) {
        binding!!.viewPagerDownloadCenter.setCurrentItem(position)
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.images_download_center_lay)
            setViewPagerFromTabClick(0)
        else if (id == R.id.audio_download_center_lay)
            setViewPagerFromTabClick(1)
        else if (id == R.id.document_download_center_lay)
            setViewPagerFromTabClick(2)
        else if (id == R.id.video_download_center_lay)
            setViewPagerFromTabClick(3)
        else if (id == R.id.other_download_center_lay)
            setViewPagerFromTabClick(4)
    }
}