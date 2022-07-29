package com.edulexa.activity.student.report_card.activity

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.report_card.adapter.ReportCardDetailMarksAdapter
import com.edulexa.activity.student.report_card.model.detail.ReportCardDetailsResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityReportCardDetailBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportCardDetailActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityReportCardDetailBinding? = null
    var titleStr: String? = null
    var examGroupClassBatchExamId: String? = null
    var resultId: String? = null
    var downloadMarkSheetUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportCardDetailBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        getBundleData()
        setUpData()
        getResultDetail()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.downloadReportCardLay.setOnClickListener(this)
    }

    private fun getBundleData() {
        try {
            val bundle = intent.extras
            titleStr = bundle!!.getString(Constants.ReportCardDetail.TITLE)
            examGroupClassBatchExamId = bundle.getString(Constants.ReportCardDetail.EXAM_GROUP_CLASS_BATCH_EXAM_ID)
            resultId = bundle.getString(Constants.ReportCardDetail.RESULT_ID)
            downloadMarkSheetUrl = bundle.getString(Constants.ReportCardDetail.DOWNLOAD_MARK_SHEET)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setUpData() {
        binding!!.tvTitle.text = titleStr
    }

    private fun getResultDetail(){
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId =
                Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
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
            jsonObject.put(Constants.ParamsStudent.EXAM_GROUP_CLASS_BATCH_EXAM_ID, examGroupClassBatchExamId)
            jsonObject.put(Constants.ParamsStudent.RESULT_ID, resultId)

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/Webservice/getExamResultDetails")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getExamResultDetails(requestBody)
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
                                    ReportCardDetailsResponse::class.java
                                ) as ReportCardDetailsResponse
                                binding!!.tvReportCardDetailGrandTotal.text = getString(R.string.concat_string_with_text_format,modelResponse.getGetMarks().toString(),"/",modelResponse.getTotalMarks().toString())
                                binding!!.tvReportCardDetailPercentage.text = modelResponse.getPercentage()
                                binding!!.tvReportCardDetailGrade.text = modelResponse.getGrade()
                                if (modelResponse.getExamResult() != null && modelResponse.getExamResult()!!.size > 0){
                                    binding!!.recyclerViewReportCardDetailMarks.visibility = View.VISIBLE
                                    binding!!.tvReportDetailMarksNoData.visibility = View.GONE
                                    binding!!.recyclerViewReportCardDetailMarks.layoutManager = LinearLayoutManager(mActivity,RecyclerView.VERTICAL,false)
                                    binding!!.recyclerViewReportCardDetailMarks.adapter = ReportCardDetailMarksAdapter(mActivity!!,modelResponse.getExamResult())
                                }else{
                                    binding!!.recyclerViewReportCardDetailMarks.visibility = View.GONE
                                    binding!!.tvReportDetailMarksNoData.visibility = View.VISIBLE
                                }
                            }
                        } else {
                            Utils.showToastPopup(
                                mActivity!!,
                                getString(R.string.response_null_or_empty_validation)
                            )

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
        else if (id == R.id.download_report_card_lay){
            if (downloadMarkSheetUrl != null){
                val permissions = arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                val rationale = "Please provide permission so that you can ..."
                val options = Permissions.Options()
                    .setRationaleDialogTitle("Info")
                    .setSettingsDialogTitle("Warning")
                Permissions.check(
                    mActivity,
                    permissions,
                    rationale,
                    options,
                    object : PermissionHandler() {
                        override fun onGranted() {
                            Utils.startDownload(mActivity!!,downloadMarkSheetUrl!!,downloadMarkSheetUrl!!)
                        }
                        override fun onDenied(
                            context: Context,
                            deniedPermissions: java.util.ArrayList<String>
                        ) {
                            Toast.makeText(mActivity, getString(R.string.permission_denied), Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
            }else Utils.showToast(mActivity!!,getString(R.string.dashboard_student_present_format))
        }
    }
}