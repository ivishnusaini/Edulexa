package com.edulexa.activity.student.gallery.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.report_card.adapter.ReportCardAdapter
import com.edulexa.activity.student.report_card.model.ReportCardListResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityGalleryOldStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryOldActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityGalleryOldStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryOldStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        getAlbumList()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getAlbumList(){
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

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/Webservice/getAlbumList")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getAlbumList(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
                       /* if (!responseStr.isNullOrEmpty()) {
                            val jsonObjectResponse = JSONObject(responseStr)
                            val statusCode = jsonObjectResponse.optInt("status")
                            if (statusCode == 200) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    ReportCardListResponse::class.java
                                ) as ReportCardListResponse
                                if (modelResponse.getData() != null) {
                                    if (modelResponse.getData()!!.size > 0) {
                                        binding!!.reportCardRecycler.visibility = View.VISIBLE
                                        binding!!.tvReportCardNoData.visibility = View.GONE
                                        binding!!.reportCardRecycler.layoutManager = LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false)
                                        binding!!.reportCardRecycler.adapter = ReportCardAdapter(mActivity!!,modelResponse.getData())
                                    } else {
                                        binding!!.reportCardRecycler.visibility = View.GONE
                                        binding!!.tvReportCardNoData.visibility = View.VISIBLE
                                    }
                                } else {
                                    binding!!.reportCardRecycler.visibility = View.GONE
                                    binding!!.tvReportCardNoData.visibility = View.VISIBLE
                                }
                            } else {
                                binding!!.reportCardRecycler.visibility = View.GONE
                                binding!!.tvReportCardNoData.visibility = View.VISIBLE
                            }
                        } else {
                            Utils.showToastPopup(
                                mActivity!!,
                                getString(R.string.response_null_or_empty_validation)
                            )
                            binding!!.reportCardRecycler.visibility = View.GONE
                            binding!!.tvReportCardNoData.visibility = View.VISIBLE
                        }*/
                    } catch (e: Exception) {
                        e.printStackTrace()
//                        binding!!.reportCardRecycler.visibility = View.GONE
//                        binding!!.tvReportCardNoData.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
//                    binding!!.reportCardRecycler.visibility = View.GONE
//                    binding!!.tvReportCardNoData.visibility = View.VISIBLE
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