package com.edulexa.activity.student.examination.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.examination.model.ExamListResponse
import com.edulexa.activity.student.examination.model.exam_detail.ExamDetailResponse
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityExamDetailBinding
import com.edulexa.support.Utils
import org.json.JSONObject

class ExamDetailActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityExamDetailBinding? = null
    var titleStr: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExamDetailBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        getBundleData()
        setUpData()
        getExamDetail()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }


    private fun getBundleData() {
        try {
            val bundle = intent.extras
            titleStr = bundle!!.getString(Constants.StudentExamDetail.TITLE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setUpData() {
        binding!!.tvStudentExamDetail.text = titleStr
    }

    @SuppressLint("InflateParams")
    private fun getExamDetail(){
        try {
            val responseStr = "{\"data\":[{\"id\":\"10\",\"exam_group_class_batch_exams_id\":\"10\",\"subject_id\":\"1\",\"date_from\":\"2020-09-21\",\"time_from\":\"11:25:24\",\"duration\":\"2\",\"room_no\":\"1\",\"max_marks\":\"50.00\",\"min_marks\":\"30.00\",\"fields_mark\":\"{\\\"1\\\":{\\\"max_marks\\\":\\\"50.00\\\",\\\"min_marks\\\":\\\"30.00\\\"}}\",\"fields_id\":\"[\\\"1\\\"]\",\"credit_hours\":\"1.00\",\"date_to\":null,\"is_active\":\"0\",\"created_at\":\"2022-03-05 15:17:48\",\"updated_at\":\"2022-03-05 15:17:48\",\"class_batch_subject_id\":null,\"subject_name\":\"English\",\"subject_code\":\"ENG\",\"subject_type\":\"theory\"}],\"success\":1}"
            if (!responseStr.isNullOrEmpty()) {
                val jsonObjectResponse = JSONObject(responseStr)
                val statusCode = jsonObjectResponse.optInt("success")
                if (statusCode == 1) {
                    val modelResponse = Utils.getObject(
                        responseStr,
                        ExamDetailResponse::class.java
                    ) as ExamDetailResponse
                    if (modelResponse.getData() != null && modelResponse.getData()!!.size > 0) {
                        binding!!.examDetailLay.visibility = View.VISIBLE
                        binding!!.tvExamDetailNoData.visibility = View.GONE
                        binding!!.examDetailLay.removeAllViews()
                        var position = 0
                        while (position < modelResponse.getData()!!.size) {
                            val inflater =
                                mActivity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                            val itemView: View =
                                inflater!!.inflate(R.layout.item_student_exam_detail, null, true)

                            val tvStudentSubjectName = itemView.findViewById<TextView>(R.id.tv_student_exam_detail_subject_name)
                            val tvStudentExamDuration = itemView.findViewById<TextView>(R.id.tv_student_exam_detail_duration)
                            val tvStudentExamDate = itemView.findViewById<TextView>(R.id.tv_student_exam_detail_date)
                            val tvStudentExamRoomNo = itemView.findViewById<TextView>(R.id.tv_student_exam_detail_room_no)

                            tvStudentSubjectName.text = modelResponse.getData()!!.get(position)!!.getSubjectName()
                            tvStudentExamDuration.text = getString(R.string.concat_string_with_text_format,"Duration: ",modelResponse.getData()!!.get(position)!!.getDuration()," Min")
                            tvStudentExamDate.text = getString(R.string.concat_string_with_text_format,modelResponse.getData()!!.get(position)!!.getDateFrom(),"-",modelResponse.getData()!!.get(position)!!.getTimeFrom())
                            tvStudentExamRoomNo.text = getString(R.string.concat_string_with_text_format,"Room ","No ",modelResponse.getData()!!.get(position)!!.getRoomNo())

                            binding!!.examDetailLay.addView(itemView)
                            position += 1
                        }
                    } else {
                        binding!!.examDetailLay.visibility = View.GONE
                        binding!!.tvExamDetailNoData.visibility = View.VISIBLE
                    }
                } else {
                    binding!!.examDetailLay.visibility = View.GONE
                    binding!!.tvExamDetailNoData.visibility = View.VISIBLE
                }
            } else {
                Utils.showToastPopup(
                    mActivity!!,
                    getString(R.string.response_null_or_empty_validation)
                )
                binding!!.examDetailLay.visibility = View.GONE
                binding!!.tvExamDetailNoData.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
            binding!!.examDetailLay.visibility = View.GONE
            binding!!.tvExamDetailNoData.visibility = View.VISIBLE
        }

    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}