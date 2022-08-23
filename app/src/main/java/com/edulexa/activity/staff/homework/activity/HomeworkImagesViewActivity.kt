package com.edulexa.activity.staff.homework.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.edulexa.R
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityHomeworkImagesViewStaffBinding
import com.edulexa.databinding.ActivitySubjectiveImageViewStaffBinding
import com.edulexa.support.Utils

class HomeworkImagesViewActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityHomeworkImagesViewStaffBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeworkImagesViewStaffBinding.inflate(layoutInflater)
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
            val imageUrl = bundle!!.getString(Constants.StaffHomework.IMAGEURL)!!
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