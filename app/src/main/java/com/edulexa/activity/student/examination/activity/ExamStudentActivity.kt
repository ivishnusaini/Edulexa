package com.edulexa.activity.student.examination.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.edulexa.R
import com.edulexa.activity.student.examination.model.ExamListResponse
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityExamStudentBinding
import com.edulexa.support.Utils
import org.json.JSONObject

class ExamStudentActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityExamStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExamStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        setUpExamList()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    @SuppressLint("InflateParams")
    private fun setUpExamList() {
        try {
            val responseStr =
                "{\"status\":\"200\",\"class_id\":\"1\",\"section_id\":\"2\",\"examSchedule\":[{\"id\":\"394\",\"exam_group_class_batch_exam_id\":\"10\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2020-09-22 11:25:13\",\"updated_at\":\"2020-09-22 11:25:13\",\"exam\":\"demo\"},{\"id\":\"421\",\"exam_group_class_batch_exam_id\":\"2\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"100003\",\"is_active\":\"1\",\"created_at\":\"2021-08-19 17:29:07\",\"updated_at\":\"2021-08-19 17:29:07\",\"exam\":\"Final Exam term\"},{\"id\":\"424\",\"exam_group_class_batch_exam_id\":\"24\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-08-19 17:50:20\",\"updated_at\":\"2021-08-19 17:50:20\",\"exam\":\"Testing for new\"},{\"id\":\"427\",\"exam_group_class_batch_exam_id\":\"1\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"100003\",\"is_active\":\"0\",\"created_at\":\"2021-08-20 18:20:01\",\"updated_at\":\"2021-08-20 18:20:01\",\"exam\":\"Unit Exam term\"},{\"id\":\"431\",\"exam_group_class_batch_exam_id\":\"11\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-08-19 18:08:21\",\"updated_at\":\"2021-08-19 18:08:21\",\"exam\":\"demoexam\"},{\"id\":\"434\",\"exam_group_class_batch_exam_id\":\"14\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"1\",\"created_at\":\"2021-08-19 18:04:59\",\"updated_at\":\"2021-08-19 18:04:59\",\"exam\":\"Hindi\"},{\"id\":\"457\",\"exam_group_class_batch_exam_id\":\"51\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-10-30 10:34:51\",\"updated_at\":\"2021-10-30 10:34:51\",\"exam\":\"Half-yearly Exam\"},{\"id\":\"462\",\"exam_group_class_batch_exam_id\":\"50\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-10-30 10:34:55\",\"updated_at\":\"2021-10-30 10:34:55\",\"exam\":\"Half yearly exam\"},{\"id\":\"473\",\"exam_group_class_batch_exam_id\":\"48\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-10-30 10:48:12\",\"updated_at\":\"2021-10-30 10:48:12\",\"exam\":\"Half yearly exam\"},{\"id\":\"478\",\"exam_group_class_batch_exam_id\":\"73\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-10-30 10:51:03\",\"updated_at\":\"2021-10-30 10:51:03\",\"exam\":\"Half yearly\"},{\"id\":\"487\",\"exam_group_class_batch_exam_id\":\"87\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-10-30 10:55:15\",\"updated_at\":\"2021-10-30 10:55:15\",\"exam\":\"Half-yearly\"},{\"id\":\"491\",\"exam_group_class_batch_exam_id\":\"75\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-10-30 10:55:17\",\"updated_at\":\"2021-10-30 10:55:17\",\"exam\":\"Half yearly Examination\"},{\"id\":\"496\",\"exam_group_class_batch_exam_id\":\"83\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-10-30 10:55:21\",\"updated_at\":\"2021-10-30 10:55:21\",\"exam\":\"Half yearly exam\"},{\"id\":\"501\",\"exam_group_class_batch_exam_id\":\"85\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-10-30 10:55:24\",\"updated_at\":\"2021-10-30 10:55:24\",\"exam\":\"Half yearly\"},{\"id\":\"506\",\"exam_group_class_batch_exam_id\":\"79\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-10-30 10:55:24\",\"updated_at\":\"2021-10-30 10:55:24\",\"exam\":\"Halfyearly\"},{\"id\":\"511\",\"exam_group_class_batch_exam_id\":\"84\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-10-30 10:55:25\",\"updated_at\":\"2021-10-30 10:55:25\",\"exam\":\"HALF-YEARLY EXAMINATION\"},{\"id\":\"516\",\"exam_group_class_batch_exam_id\":\"86\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-10-30 10:55:25\",\"updated_at\":\"2021-10-30 10:55:25\",\"exam\":\"Demo\"},{\"id\":\"524\",\"exam_group_class_batch_exam_id\":\"82\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-10-30 10:55:33\",\"updated_at\":\"2021-10-30 10:55:33\",\"exam\":\"Half yearly examination\"},{\"id\":\"529\",\"exam_group_class_batch_exam_id\":\"88\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-10-30 10:55:37\",\"updated_at\":\"2021-10-30 10:55:37\",\"exam\":\"Half YEARLY\"},{\"id\":\"534\",\"exam_group_class_batch_exam_id\":\"72\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-10-30 10:55:48\",\"updated_at\":\"2021-10-30 10:55:48\",\"exam\":\"Half yearly\"},{\"id\":\"539\",\"exam_group_class_batch_exam_id\":\"77\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-10-30 10:56:42\",\"updated_at\":\"2021-10-30 10:56:42\",\"exam\":\"Half yearly\"},{\"id\":\"544\",\"exam_group_class_batch_exam_id\":\"91\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-10-30 11:03:18\",\"updated_at\":\"2021-10-30 11:03:18\",\"exam\":\"Half yearly exam\"},{\"id\":\"549\",\"exam_group_class_batch_exam_id\":\"94\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2021-10-30 11:05:59\",\"updated_at\":\"2021-10-30 11:05:59\",\"exam\":\"Half yearly\"},{\"id\":\"607\",\"exam_group_class_batch_exam_id\":\"102\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2022-02-16 22:06:23\",\"updated_at\":\"2022-02-16 22:06:23\",\"exam\":\"Mid Term\"},{\"id\":\"615\",\"exam_group_class_batch_exam_id\":\"103\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2022-03-02 11:38:30\",\"updated_at\":\"2022-03-02 11:38:30\",\"exam\":\"Yearly Exam\"},{\"id\":\"631\",\"exam_group_class_batch_exam_id\":\"105\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"100002\",\"is_active\":\"0\",\"created_at\":\"2022-05-10 22:11:41\",\"updated_at\":\"2022-05-10 22:11:41\",\"exam\":\"Formative Exam\"},{\"id\":\"643\",\"exam_group_class_batch_exam_id\":\"106\",\"student_id\":\"249\",\"student_session_id\":\"371\",\"roll_no\":\"0\",\"is_active\":\"0\",\"created_at\":\"2022-06-13 11:08:00\",\"updated_at\":\"2022-06-13 11:08:00\",\"exam\":\"Unit Exam\"}]}"
            if (!responseStr.isNullOrEmpty()) {
                val jsonObjectResponse = JSONObject(responseStr)
                val statusCode = jsonObjectResponse.optInt("status")
                if (statusCode == 200) {
                    val modelResponse = Utils.getObject(
                        responseStr,
                        ExamListResponse::class.java
                    ) as ExamListResponse
                    if (modelResponse.getExamSchedule() != null && modelResponse.getExamSchedule()!!.size > 0) {
                        binding!!.examListLay.visibility = View.VISIBLE
                        binding!!.tvExaminationNoData.visibility = View.GONE
                        binding!!.examListLay.removeAllViews()
                        var position = 0
                        while (position < modelResponse.getExamSchedule()!!.size) {
                            val inflater =
                                mActivity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                            val itemView: View =
                                inflater!!.inflate(R.layout.item_student_exam, null, true)


                            val cvExam1 = itemView.findViewById<CardView>(R.id.cv_exam_1)
                            val tvExam1 =
                                itemView.findViewById<TextView>(R.id.tv_student_exam_name_1)
                            val cvExam2 = itemView.findViewById<CardView>(R.id.cv_exam_2)
                            val tvExam2 =
                                itemView.findViewById<TextView>(R.id.tv_student_exam_name_2)

                            if (position < modelResponse.getExamSchedule()!!.size) {
                                cvExam1.visibility = View.VISIBLE
                                tvExam1.text =
                                    modelResponse.getExamSchedule()!!.get(position)!!.getExam()
                                cvExam1.tag = 1000 + position
                            }
                            val secondItemPosition = position + 1
                            if (secondItemPosition < modelResponse.getExamSchedule()!!.size) {
                                cvExam2.visibility = View.VISIBLE
                                tvExam2.text =
                                    modelResponse.getExamSchedule()!!.get(secondItemPosition)!!
                                        .getExam()
                                cvExam2.tag = 10000 + secondItemPosition
                            }
                            cvExam1.setOnClickListener {
                                val tag = cvExam1.tag as Int - 1000
                                val bundle = Bundle()
                                bundle.putString(
                                    Constants.StudentExamDetail.TITLE,
                                    modelResponse.getExamSchedule()!!.get(tag)!!.getExam()
                                )
                                startActivity(
                                    Intent(
                                        mActivity,
                                        ExamDetailActivity::class.java
                                    ).putExtras(bundle)
                                )
                            }

                            cvExam2.setOnClickListener {
                                val tag = cvExam2.tag as Int - 10000
                                val bundle = Bundle()
                                bundle.putString(
                                    Constants.StudentExamDetail.TITLE,
                                    modelResponse.getExamSchedule()!!.get(tag)!!
                                        .getExam()
                                )
                                startActivity(
                                    Intent(
                                        mActivity,
                                        ExamDetailActivity::class.java
                                    ).putExtras(bundle)
                                )
                            }


                            binding!!.examListLay.addView(itemView)
                            position += 2
                        }
                    } else {
                        binding!!.examListLay.visibility = View.GONE
                        binding!!.tvExaminationNoData.visibility = View.VISIBLE
                    }
                } else {
                    binding!!.examListLay.visibility = View.GONE
                    binding!!.tvExaminationNoData.visibility = View.VISIBLE
                }
            } else {
                Utils.showToastPopup(
                    mActivity!!,
                    getString(R.string.response_null_or_empty_validation)
                )
                binding!!.examListLay.visibility = View.GONE
                binding!!.tvExaminationNoData.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
            binding!!.examListLay.visibility = View.GONE
            binding!!.tvExaminationNoData.visibility = View.VISIBLE
        }

    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}