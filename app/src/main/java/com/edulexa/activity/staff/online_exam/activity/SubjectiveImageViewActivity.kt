package com.edulexa.activity.staff.online_exam.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.edulexa.R
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivitySubjectiveImageViewStaffBinding
import com.edulexa.databinding.ActivitySubjectiveImagesStaffBinding
import com.edulexa.support.Utils

class SubjectiveImageViewActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivitySubjectiveImageViewStaffBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubjectiveImageViewStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        getBundleData()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getBundleData() {
        try {
            val bundle = intent.extras
            val imageUrl = bundle!!.getString(Constants.StaffOnlineExam.IMAGEURL)!!
            Utils.setImageUsingGlide(mActivity!!,imageUrl,binding!!.ivImage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}