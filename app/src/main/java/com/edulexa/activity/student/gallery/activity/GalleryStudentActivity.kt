package com.edulexa.activity.student.gallery.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.edulexa.R
import com.edulexa.activity.student.gallery.adapter.ViewpagerGalleryAdapter
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityGalleryStudentBinding

class GalleryStudentActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityGalleryStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryStudentBinding.inflate(layoutInflater)
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
        binding!!.galleryStudentAllLay.setOnClickListener(this)
        binding!!.galleryStudentVideoLay.setOnClickListener(this)
        binding!!.galleryStudentImageLay.setOnClickListener(this)
        binding!!.galleryStudentDocumentLay.setOnClickListener(this)
        binding!!.galleryStudentLinkLay.setOnClickListener(this)
    }

    private fun resetTab(position: Int) {
        binding!!.tvGalleryStudentAll.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewGalleryStudentAll.visibility = View.GONE
        binding!!.tvGalleryStudentVideo.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewGalleryStudentVideo.visibility = View.GONE
        binding!!.tvGalleryStudentImage.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewGalleryStudentImage.visibility = View.GONE
        binding!!.tvGalleryStudentDocument.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewGalleryStudentDocument.visibility = View.GONE
        binding!!.tvGalleryStudentLink.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewGalleryStudentLink.visibility = View.GONE

        when (position) {
            0 -> {
                Constants.AppSaveData.gallerystudenttype = "all"
                binding!!.tvGalleryStudentAll.setTextColor(ContextCompat.getColor(mActivity!!, R.color.red))
                binding!!.viewGalleryStudentAll.visibility = View.VISIBLE
                binding!!.viewGalleryStudentAll.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.red
                    )
                )
                val x = binding!!.tvGalleryStudentAll.left
                val y = binding!!.tvGalleryStudentAll.top
                binding!!.attendanceHorizontalScrollView.smoothScrollTo(x, y)
            }
            1 -> {
                Constants.AppSaveData.gallerystudenttype = "video"
                binding!!.tvGalleryStudentVideo.setTextColor(ContextCompat.getColor(mActivity!!, R.color.red))
                binding!!.viewGalleryStudentVideo.visibility = View.VISIBLE
                binding!!.viewGalleryStudentVideo.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.red
                    )
                )
                val x = binding!!.tvGalleryStudentVideo.left
                val y = binding!!.tvGalleryStudentVideo.top
                binding!!.attendanceHorizontalScrollView.smoothScrollTo(x, y)
            }
            2 -> {
                Constants.AppSaveData.gallerystudenttype = "image"
                binding!!.tvGalleryStudentImage.setTextColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.red
                    )
                )
                binding!!.viewGalleryStudentImage.visibility = View.VISIBLE
                binding!!.viewGalleryStudentImage.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.red
                    )
                )
                val x = binding!!.tvGalleryStudentImage.left
                val y = binding!!.tvGalleryStudentImage.top
                binding!!.attendanceHorizontalScrollView.smoothScrollTo(x, y)
            }
            3 -> {
                Constants.AppSaveData.gallerystudenttype = "document"
                binding!!.tvGalleryStudentDocument.setTextColor(ContextCompat.getColor(mActivity!!, R.color.red))
                binding!!.viewGalleryStudentDocument.visibility = View.VISIBLE
                binding!!.viewGalleryStudentDocument.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.red
                    )
                )
                val x = binding!!.tvGalleryStudentDocument.right
                val y = binding!!.tvGalleryStudentDocument.top
                binding!!.attendanceHorizontalScrollView.smoothScrollTo(x, y)
            }
            4 -> {
                Constants.AppSaveData.gallerystudenttype = "link"
                binding!!.tvGalleryStudentLink.setTextColor(ContextCompat.getColor(mActivity!!, R.color.red))
                binding!!.viewGalleryStudentLink.visibility = View.VISIBLE
                binding!!.viewGalleryStudentLink.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.red
                    )
                )
                val x = binding!!.tvGalleryStudentLink.right
                val y = binding!!.tvGalleryStudentLink.top
                binding!!.attendanceHorizontalScrollView.smoothScrollTo(x, y)
            }
        }
    }


    private fun setUpViewPager() {
        binding!!.galleryStudentViewPager.adapter =
            ViewpagerGalleryAdapter(supportFragmentManager, 5)
        binding!!.galleryStudentViewPager.addOnPageChangeListener(object :
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
        binding!!.galleryStudentViewPager.setCurrentItem(position)
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.gallery_student_all_lay)
            setViewPagerFromTabClick(0)
        else if (id == R.id.gallery_student_video_lay)
            setViewPagerFromTabClick(1)
        else if (id == R.id.gallery_student_image_lay)
            setViewPagerFromTabClick(2)
        else if (id == R.id.gallery_student_document_lay)
            setViewPagerFromTabClick(3)
        else if (id == R.id.gallery_student_link_lay)
            setViewPagerFromTabClick(4)
    }
}