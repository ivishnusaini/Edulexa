package com.edulexa.activity.student.online_exam.activity

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.homework.adapter.SubjectSpinnerAdapter
import com.edulexa.activity.student.homework.model.subject_list.SubjectListDatum
import com.edulexa.activity.student.homework.model.subject_list.SubjectListResponse
import com.edulexa.activity.student.online_exam.adapter.OnlineExamListAdapter
import com.edulexa.activity.student.online_exam.model.exam_list.OnlineExamListResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityHostelStudentBinding
import com.edulexa.databinding.ActivityOnlineExamStudentBinding
import com.edulexa.databinding.DialogSubmitOnlineExamBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OnlineExamListActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityOnlineExamStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineExamStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        getOnlineExamList()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getOnlineExamList(){
        if (Utils.isNetworkAvailable(mActivity!!)){
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId = Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent = APIClientStudent.getRetroFitClientWithNewKeyHeader(mActivity!!, accessToken,branchId,userId).create(
                ApiInterfaceStudent::class.java)

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStudent.STUDENT_ID, Utils.getStudentId(mActivity!!))
            jsonObject.put(Constants.ParamsStudent.STUDENT_SESSION_ID, Utils.getStudentSessionId(mActivity!!))

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/getOnlineExam")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getOnlineExamList(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try{
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()){
                            val modelResponse = Utils.getObject(responseStr, OnlineExamListResponse::class.java) as OnlineExamListResponse
                            if (modelResponse.getExamSchedule() != null){
                                if (modelResponse.getExamSchedule()!!.size > 0){
                                    binding!!.studentOnlineExamRecycler.visibility = View.VISIBLE
                                    binding!!.tvStudentOnlineExamNoData.visibility = View.GONE
                                    binding!!.studentOnlineExamRecycler.layoutManager = LinearLayoutManager(mActivity!!,RecyclerView.VERTICAL,false)
                                    binding!!.studentOnlineExamRecycler.adapter = OnlineExamListAdapter(mActivity!!,modelResponse.getExamSchedule()
                                        ,modelResponse.getOnlineExamNative()!!,modelResponse.getWebviewUrl()!!)
                                }else{
                                    binding!!.studentOnlineExamRecycler.visibility = View.GONE
                                    binding!!.tvStudentOnlineExamNoData.visibility = View.VISIBLE
                                }
                            }else{
                                binding!!.studentOnlineExamRecycler.visibility = View.GONE
                                binding!!.tvStudentOnlineExamNoData.visibility = View.VISIBLE
                            }
                        }else {
                            Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                            binding!!.studentOnlineExamRecycler.visibility = View.GONE
                            binding!!.tvStudentOnlineExamNoData.visibility = View.VISIBLE
                        }
                    }catch (e : Exception){
                        e.printStackTrace()
                        binding!!.studentOnlineExamRecycler.visibility = View.GONE
                        binding!!.tvStudentOnlineExamNoData.visibility = View.VISIBLE
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.studentOnlineExamRecycler.visibility = View.GONE
                    binding!!.tvStudentOnlineExamNoData.visibility = View.VISIBLE
                }
            })
        }else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    fun submitDialog() {
        try {
            var dialogBinding: DialogSubmitOnlineExamBinding? = null
            val dialog = Dialog(mActivity!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBinding = DialogSubmitOnlineExamBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)

            dialogBinding.title.text = getString(R.string.online_exam_student_question_ans_attention)
            dialogBinding.tvMsg.text = getString(R.string.online_exam_student_question_ans_alreay_submitted)
            dialogBinding.totalQuestionLay.visibility = View.GONE
            dialogBinding.attemptedQuestionLay.visibility = View.GONE
            dialogBinding.skippedQuestionLay.visibility = View.GONE

            dialogBinding.tvOk.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    dialog.dismiss()
                }
            })


            dialogBinding.tvCancel.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    dialog.dismiss()
                }
            })

            dialog.show()
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