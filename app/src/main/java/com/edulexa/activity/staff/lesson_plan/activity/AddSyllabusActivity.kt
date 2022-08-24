package com.edulexa.activity.staff.lesson_plan.activity

import android.app.Activity
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.edulexa.R
import com.edulexa.databinding.ActivityAddSyllabusStaffBinding
import com.edulexa.databinding.ActivityLessonListStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import java.util.*

class AddSyllabusActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityAddSyllabusStaffBinding? = null
    var preference: Preference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSyllabusStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }
    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}