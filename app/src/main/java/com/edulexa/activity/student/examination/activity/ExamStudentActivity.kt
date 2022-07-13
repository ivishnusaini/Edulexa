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
import com.edulexa.activity.student.homework.adapter.SubjectSpinnerAdapter
import com.edulexa.activity.student.homework.model.subject_list.SubjectListDatum
import com.edulexa.activity.student.homework.model.subject_list.SubjectListResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityExamStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId = Preference().getInstance(mActivity!!)!!
                .getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent =
                APIClientStudent.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    accessToken,
                    branchId,
                    userId
                ).create(
                    ApiInterfaceStudent::class.java
                )

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStudent.STUDENT_ID, Utils.getStudentId(mActivity!!))
            jsonObject.put(Constants.ParamsStudent.STUDENT_SESSION_ID, Utils.getStudentSessionId(mActivity!!))

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/Webservice/examSchedule")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getExamSchedule(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
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
                                            inflater!!.inflate(
                                                R.layout.item_student_exam,
                                                null,
                                                true
                                            )


                                        val cvExam1 =
                                            itemView.findViewById<CardView>(R.id.cv_exam_1)
                                        val tvExam1 =
                                            itemView.findViewById<TextView>(R.id.tv_student_exam_name_1)
                                        val cvExam2 =
                                            itemView.findViewById<CardView>(R.id.cv_exam_2)
                                        val tvExam2 =
                                            itemView.findViewById<TextView>(R.id.tv_student_exam_name_2)

                                        if (position < modelResponse.getExamSchedule()!!.size) {
                                            cvExam1.visibility = View.VISIBLE
                                            tvExam1.text =
                                                modelResponse.getExamSchedule()!!
                                                    .get(position)!!.getExam()
                                            cvExam1.tag = 1000 + position
                                        }
                                        val secondItemPosition = position + 1
                                        if (secondItemPosition < modelResponse.getExamSchedule()!!.size) {
                                            cvExam2.visibility = View.VISIBLE
                                            tvExam2.text =
                                                modelResponse.getExamSchedule()!!
                                                    .get(secondItemPosition)!!
                                                    .getExam()
                                            cvExam2.tag = 10000 + secondItemPosition
                                        }
                                        cvExam1.setOnClickListener {
                                            val tag = cvExam1.tag as Int - 1000
                                            val bundle = Bundle()
                                            bundle.putString(
                                                Constants.StudentExamDetail.TITLE,
                                                modelResponse.getExamSchedule()!!.get(tag)!!
                                                    .getExam()
                                            )
                                            bundle.putString(
                                                Constants.StudentExamDetail.EXAM_ID,
                                                modelResponse.getExamSchedule()!!.get(tag)!!
                                                    .getExamGroupClassBatchExamId()
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
                                            bundle.putString(
                                                Constants.StudentExamDetail.EXAM_ID,
                                                modelResponse.getExamSchedule()!!.get(tag)!!
                                                    .getExamGroupClassBatchExamId()
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
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                }
            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}