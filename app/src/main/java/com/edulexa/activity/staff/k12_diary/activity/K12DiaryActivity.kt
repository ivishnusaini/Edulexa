package com.edulexa.activity.staff.k12_diary.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.k12_diary.adapter.K12TimelineAdapter
import com.edulexa.activity.staff.k12_diary.model.TimelineResponse
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityK12DiaryStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class K12DiaryActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityK12DiaryStaffBinding? = null
    var studentId = ""
    var preference : Preference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityK12DiaryStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        getBundleData()
        getTimelineData()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.ivAdd.setOnClickListener(this)
    }

    private fun getBundleData() {
        try {
            val bundle = intent.extras
            studentId = bundle!!.getString(Constants.StaffK12Timeline.STUDENT_ID)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getTimelineData() {
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

            val apiInterfaceWithHeader: ApiInterfaceStaff =
                APIClientStaff.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    Utils.getStaffToken(mActivity!!),
                    Utils.getStaffId(mActivity!!),
                    dbId!!
                ).create(ApiInterfaceStaff::class.java)

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
            jsonObject.put(Constants.ParamsStaff.STUDENT_ID, studentId)
            jsonObject.put(Constants.ParamsStaff.ROLE_ID, Utils.getStaffRoleId(mActivity!!))

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "view_student_timeline")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getK12Timeline(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            val responseJsonObject = JSONObject(responseStr)
                            val status = responseJsonObject.optBoolean("status")
                            if (status) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    TimelineResponse::class.java
                                ) as TimelineResponse
                                if (modelResponse.getTimelineList()!!.isNotEmpty()) {
                                    binding!!.recyclerView.visibility = View.VISIBLE
                                    binding!!.tvNoData.visibility = View.GONE
                                    binding!!.recyclerView.layoutManager =
                                        LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false)
                                    binding!!.recyclerView.adapter = K12TimelineAdapter(
                                        mActivity!!,
                                        modelResponse.getTimelineList()
                                    )
                                } else {
                                    binding!!.recyclerView.visibility = View.GONE
                                    binding!!.tvNoData.visibility = View.VISIBLE
                                }
                            } else {
                                val message = responseJsonObject.optString("message")
                                if (!message.isEmpty())
                                    Utils.showToastPopup(mActivity!!, message)
                                else Utils.showToastPopup(
                                    mActivity!!,
                                    getString(R.string.did_not_fetch_data)
                                )
                                binding!!.recyclerView.visibility = View.GONE
                                binding!!.tvNoData.visibility = View.VISIBLE
                            }
                        } else {
                            Utils.showToastPopup(
                                mActivity!!,
                                getString(R.string.response_null_or_empty_validation)
                            )
                            binding!!.recyclerView.visibility = View.GONE
                            binding!!.tvNoData.visibility = View.VISIBLE
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        binding!!.recyclerView.visibility = View.GONE
                        binding!!.tvNoData.visibility = View.VISIBLE
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.recyclerView.visibility = View.GONE
                    binding!!.tvNoData.visibility = View.VISIBLE
                }

            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    fun deleteTimelinePopup(timelineId: String) {
        AlertDialog.Builder(mActivity)
            .setTitle("Delete Timeline")
            .setMessage("Are you sure you want to delete this?")
            .setPositiveButton(
                android.R.string.yes
            ) { dialog: DialogInterface?, which: Int ->
                deleteTimline(
                    timelineId
                )
            }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun deleteTimline(timelineId: String) {
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

            val apiInterfaceWithHeader: ApiInterfaceStaff =
                APIClientStaff.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    Utils.getStaffToken(mActivity!!),
                    Utils.getStaffId(mActivity!!),
                    dbId!!
                ).create(ApiInterfaceStaff::class.java)

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStaff.TIMELINE_ID, timelineId)

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "delete_student_timeline")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.deleteStudentTimeline(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            val responseJsonObject = JSONObject(responseStr)
                            val status = responseJsonObject.optBoolean("status")
                            if (status)
                                getTimelineData()
                            else {
                                val message = responseJsonObject.optString("message")
                                if (!message.isEmpty())
                                    Utils.showToastPopup(mActivity!!, message)
                                else Utils.showToastPopup(
                                    mActivity!!,
                                    getString(R.string.did_not_fetch_data)
                                )
                            }
                        } else Utils.showToastPopup(
                            mActivity!!,
                            getString(R.string.response_null_or_empty_validation)
                        )
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
        else if (id == R.id.iv_add) {
            val bundle = Bundle()
            bundle.putString(Constants.StaffK12Timeline.STUDENT_ID, studentId)
            startActivity(Intent(mActivity, AddTimelineActivity::class.java).putExtras(bundle))
        }
    }
}