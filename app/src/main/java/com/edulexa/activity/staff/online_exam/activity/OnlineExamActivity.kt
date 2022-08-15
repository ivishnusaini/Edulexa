package com.edulexa.activity.staff.online_exam.activity

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.edulexa.R
import com.edulexa.activity.staff.custom_lesson_plan.activity.AddCustomLessonActivity
import com.edulexa.activity.staff.online_exam.adapter.OnlineExamListAdapter
import com.edulexa.activity.staff.online_exam.model.list.ExamOnlineExamStaff
import com.edulexa.activity.staff.online_exam.model.list.OnlineExamListResponse
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityOnlineExamStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class OnlineExamActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityOnlineExamStaffBinding? = null
    var preference: Preference? = null

    var myCalendar: Calendar? = null
    var fromDateSetListener: DatePickerDialog.OnDateSetListener? = null
    val examList: List<ExamOnlineExamStaff?> = ArrayList()
    val examAccToStaffLoginList: List<ExamOnlineExamStaff?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineExamStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    override fun onResume() {
        super.onResume()
        getExamList()
    }

    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        setUpFromDate()
        setUpData()
        Utils.showProgressBar(mActivity!!)
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.tvDate.setOnClickListener(this)
        binding!!.ivAdd.setOnClickListener(this)
    }

    private fun setUpFromDate() {
        myCalendar = Calendar.getInstance()
        fromDateSetListener =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                myCalendar!!.set(Calendar.YEAR, year)
                myCalendar!!.set(Calendar.MONTH, monthOfYear)
                myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabelFromData()
                Utils.showProgressBar(mActivity!!)
                getExamList()
            }
    }

    private fun updateLabelFromData() {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding!!.tvDate.text = sdf.format(myCalendar!!.time)
    }

    private fun setUpData() {
        binding!!.tvDate.text = Utils.getCurrentDate()
    }

    private fun getExamList() {
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.hideKeyboard(mActivity!!)

            val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

            val apiInterfaceWithHeader: ApiInterfaceStaff =
                APIClientStaff.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    Utils.getStaffToken(mActivity!!),
                    Utils.getStaffId(mActivity!!), dbId!!
                ).create(ApiInterfaceStaff::class.java)

            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
            builder.addFormDataPart(Constants.ParamsStaff.MODULE_ID, Constants.MODULE_ID_VALUE)
            builder.addFormDataPart(
                Constants.ParamsStaff.ROLE_ID,
                Utils.getStaffRoleId(mActivity!!)
            )
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "onlineexam")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getOnlineExam(requestBody)
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
                            val examListArr = responseJsonObject.optJSONArray("examlist")
                            if (examListArr != null) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    OnlineExamListResponse::class.java
                                ) as OnlineExamListResponse
                                if (modelResponse.getExamlist()!!.isNotEmpty()) {
                                    (examList as ArrayList<ExamOnlineExamStaff?>).clear()
                                    (examAccToStaffLoginList as ArrayList<ExamOnlineExamStaff?>).clear()
                                    examList.addAll(modelResponse.getExamlist()!!)
                                    examAccToStaffLoginList.addAll(modelResponse.getExamlist()!!)
                                    filterListAccToStaffLogin()
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

    private fun filterListAccToStaffLogin() {
        try {
            val roleId = Utils.getStaffRoleId(mActivity!!)
            val staffId = Utils.getStaffId(mActivity!!)
            if (roleId == "1" || roleId == "7") {
                if (examAccToStaffLoginList.isNotEmpty()) {
                    binding!!.recyclerView.visibility = View.VISIBLE
                    binding!!.tvNoData.visibility = View.GONE
                    binding!!.recyclerView.adapter =
                        OnlineExamListAdapter(mActivity!!, examAccToStaffLoginList)
                } else {
                    binding!!.recyclerView.visibility = View.GONE
                    binding!!.tvNoData.visibility = View.VISIBLE
                }
            } else {
                if (examList.isNotEmpty()) {
                    (examAccToStaffLoginList as ArrayList<ExamOnlineExamStaff?>).clear()
                    for (model in examList) {
                        if (staffId == model!!.getCreatedBy())
                            examAccToStaffLoginList.add(model)
                    }
                    if (examAccToStaffLoginList.isNotEmpty()) {
                        binding!!.recyclerView.visibility = View.VISIBLE
                        binding!!.tvNoData.visibility = View.GONE
                        binding!!.recyclerView.adapter =
                            OnlineExamListAdapter(mActivity!!, examAccToStaffLoginList)
                    } else {
                        binding!!.recyclerView.visibility = View.GONE
                        binding!!.tvNoData.visibility = View.VISIBLE
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun sendNotification(examId : String){
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.hideKeyboard(mActivity!!)

            val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

            val apiInterfaceWithHeader: ApiInterfaceStaff =
                APIClientStaff.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    Utils.getStaffToken(mActivity!!),
                    Utils.getStaffId(mActivity!!), dbId!!
                ).create(ApiInterfaceStaff::class.java)

            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
            builder.addFormDataPart(Constants.ParamsStaff.EXAM_ID, examId)
            builder.addFormDataPart(
                Constants.ParamsStaff.ROLE_ID,
                Utils.getStaffRoleId(mActivity!!)
            )
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "sendOnlineExamNotification")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.sendOnlineExamNotification(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            Utils.showToast(mActivity!!,getString(R.string.online_exam_staff_notification_successfully_send))
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

    fun showDeleteExamDialog(examId : String){
        AlertDialog.Builder(mActivity)
            .setTitle("Delete Exam")
            .setMessage("Do you sure want to delete exam ?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(
                android.R.string.yes
            ) { dialog: DialogInterface?, whichButton: Int ->
                if (Utils.isNetworkAvailable(mActivity!!)) {
                    Utils.hideKeyboard(mActivity!!)

                    val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

                    val apiInterfaceWithHeader: ApiInterfaceStaff =
                        APIClientStaff.getRetroFitClientWithNewKeyHeader(
                            mActivity!!,
                            Utils.getStaffToken(mActivity!!),
                            Utils.getStaffId(mActivity!!), dbId!!
                        ).create(ApiInterfaceStaff::class.java)

                    val builder = MultipartBody.Builder()
                    builder.setType(MultipartBody.FORM)
                    builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
                    builder.addFormDataPart(Constants.ParamsStaff.ONLINEEXAM_ID, examId)
                    builder.addFormDataPart(
                        Constants.ParamsStaff.ROLE_ID,
                        Utils.getStaffRoleId(mActivity!!)
                    )
                    val requestBody = builder.build()

                    Utils.printLog("Url", Constants.BASE_URL_STAFF + "deleteOnlineExam")

                    val call: Call<ResponseBody> = apiInterfaceWithHeader.deleteOnlineExam(requestBody)
                    call.enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            Utils.hideProgressBar()
                            try {
                                val responseStr = response.body()!!.string()
                                if (!responseStr.isNullOrEmpty()) {
                                    val jsonObject = JSONObject(responseStr)
                                    val message = jsonObject.optString("message")
                                    Utils.showToast(mActivity!!,message)
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
            .setNegativeButton(android.R.string.no, null).show()
    }


    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.tv_date) {
            binding!!.tvDate.text = getString(R.string.custom_lesson_plan_staff_date)
            Utils.hideKeyboard(mActivity!!)
            val fromDateDialog = DatePickerDialog(
                mActivity!!,
                fromDateSetListener,
                myCalendar!!.get(Calendar.YEAR),
                myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            )
            fromDateDialog.show()
        } else if (id == R.id.iv_add) {
            startActivity(Intent(mActivity, AddExamActivity::class.java))
        }
    }

}