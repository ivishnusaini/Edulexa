package com.edulexa.activity.staff.homework.activity

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.edulexa.R
import com.edulexa.activity.staff.custom_lesson_plan.adapter.ClassSpinnerAdapter
import com.edulexa.activity.staff.homework.adapter.HomeworkListAdapter
import com.edulexa.activity.staff.homework.model.homeworklist.HomeworkResponse
import com.edulexa.activity.staff.online_exam.adapter.SectionSpinnerAdapter
import com.edulexa.activity.staff.student_profile.model.class_list.ClassData
import com.edulexa.activity.staff.student_profile.model.class_list.ClassResponse
import com.edulexa.activity.staff.student_profile.model.section.Section
import com.edulexa.activity.staff.student_profile.model.section.SectionResponse
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityHomeworkStaffBinding
import com.edulexa.databinding.DialogStaffHomeworkFilterBinding
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

class HomeworkActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityHomeworkStaffBinding? = null
    var preference: Preference? = null

    var classListSpinn: List<ClassData?>? = ArrayList()
    var classSpinnerAdapter: ClassSpinnerAdapter? = null
    var classId = ""
    var sectionListSpinn: List<Section?>? = ArrayList()
    var sectionId = ""
    var dateSTr = ""

    var dialogBinding: DialogStaffHomeworkFilterBinding? = null
    var tvMonthYear: TextView? = null

    var myCalendar: Calendar? = null
    var fromDateSetListener: DatePickerDialog.OnDateSetListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeworkStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        setUpFromDate()
        getHomeWorkList()

    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.ivAdd.setOnClickListener(this)
        binding!!.ivFilter.setOnClickListener(this)
    }

    private fun setUpFromDate() {
        myCalendar = Calendar.getInstance()
        fromDateSetListener =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                myCalendar!!.set(Calendar.YEAR, year)
                myCalendar!!.set(Calendar.MONTH, monthOfYear)
                myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabelFromData()
            }
    }

    private fun updateLabelFromData() {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dialogBinding!!.tvMonthYear.text = sdf.format(myCalendar!!.time)
        dateSTr = dialogBinding!!.tvMonthYear.text.toString()
    }

    private fun getHomeWorkList() {
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
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
            builder.addFormDataPart(Constants.ParamsStaff.MODULE_ID, Utils.getStaffId(mActivity!!))
            builder.addFormDataPart(
                Constants.ParamsStaff.ROLE_ID,
                Utils.getStaffRoleId(mActivity!!)
            )
            builder.addFormDataPart(Constants.ParamsStaff.CLASS_ID, classId)
            builder.addFormDataPart(Constants.ParamsStaff.SECTION_ID, sectionId)
            builder.addFormDataPart(Constants.ParamsStaff.DATE, dateSTr)
            builder.addFormDataPart(
                Constants.ParamsStaff.ROLE_ID,
                Utils.getStaffRoleId(mActivity!!)
            )
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "homeworkList")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.homeworkList(requestBody)
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
                            val homeworkListJsonArr =
                                responseJsonObject.optJSONArray("homeworklist")
                            if (homeworkListJsonArr != null) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    HomeworkResponse::class.java
                                ) as HomeworkResponse
                                if (modelResponse.getHomeworklist()!!.isNotEmpty()) {
                                    binding!!.recyclerView.visibility = View.VISIBLE
                                    binding!!.tvNoData.visibility = View.GONE
                                    Constants.AppSaveData.homeworkList =
                                        modelResponse.getHomeworklist()
                                    binding!!.recyclerView.layoutManager = LinearLayoutManager(
                                        mActivity,
                                        LinearLayoutManager.VERTICAL, false
                                    )
                                    binding!!.recyclerView.adapter = HomeworkListAdapter(
                                        mActivity!!,
                                        Constants.AppSaveData.homeworkList
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
                }

            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    private fun getClassList() {
        (classListSpinn as ArrayList<ClassData?>).clear()
        if (classSpinnerAdapter != null)
            classSpinnerAdapter!!.notifyDataSetChanged()
        classId = ""
        (sectionListSpinn as ArrayList<Section?>).clear()
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


            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
            val requestBody = builder.build()


            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getClasses")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getClasses(requestBody)
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
                            val status = responseJsonObject.optInt("status")
                            if (status == 200) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    ClassResponse::class.java
                                ) as ClassResponse
                                if (modelResponse.getClasses() != null && modelResponse.getClasses()!!
                                        .isNotEmpty()
                                ) {
                                    val classData = ClassData()
                                    classData.setClass_("Select class")
                                    classListSpinn = modelResponse.getClasses()
                                    (classListSpinn as ArrayList<ClassData?>).add(0, classData)
                                    classSpinnerAdapter =
                                        ClassSpinnerAdapter(mActivity!!, classListSpinn)
                                    dialogBinding!!.classSpinn.adapter = classSpinnerAdapter
                                }
                            } else {
                                val message = responseJsonObject.optString("message")
                                if (!message.isEmpty())
                                    Utils.showToastPopup(mActivity!!, message)
                                else Utils.showToastPopup(
                                    mActivity!!,
                                    getString(R.string.did_not_fetch_data)
                                )
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

    private fun getSection() {
        (sectionListSpinn as ArrayList<Section?>).clear()
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

            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
            builder.addFormDataPart(Constants.ParamsStaff.CLASS_ID, classId)
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getClassSections")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getClassSections(requestBody)
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
                            val sectionListJsonArr = responseJsonObject.optJSONArray("section_list")
                            if (sectionListJsonArr != null) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    SectionResponse::class.java
                                ) as SectionResponse
                                if (modelResponse.getSectionList() != null && modelResponse.getSectionList()!!
                                        .isNotEmpty()
                                ) {
                                    val section = Section()
                                    section.setSection("Select section")
                                    sectionListSpinn = modelResponse.getSectionList()
                                    (sectionListSpinn as ArrayList<Section?>).add(0, section)
                                    dialogBinding!!.sectionSpinn.adapter =
                                        SectionSpinnerAdapter(mActivity!!, sectionListSpinn)
                                }
                            } else {
                                val message = responseJsonObject.optString("message")
                                if (!message.isEmpty())
                                    Utils.showToastPopup(mActivity!!, message)
                                else Utils.showToastPopup(
                                    mActivity!!,
                                    getString(R.string.did_not_fetch_data)
                                )
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


    private fun showFilterPopup() {
        try {
            val dialog = Dialog(mActivity!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBinding = DialogStaffHomeworkFilterBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding!!.root)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)
            tvMonthYear = dialogBinding!!.tvMonthYear
            getClassList()

            dialogBinding!!.classSpinn.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        if (classListSpinn!![position]!!.getId() != "") {
                            classId = classListSpinn!![position]!!.getId()!!
                            getSection()
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            dialogBinding!!.sectionSpinn.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        if (sectionListSpinn!![position]!!.getId() != "") {
                            sectionId = sectionListSpinn!![position]!!.getId()!!
                        } else sectionId = ""
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }

            dialogBinding!!.tvMonthYear.setOnClickListener { v: View? ->
                Utils.hideKeyboard(mActivity!!)
                val fromDateDialog = DatePickerDialog(
                    mActivity!!,
                    fromDateSetListener,
                    myCalendar!!.get(Calendar.YEAR),
                    myCalendar!!.get(Calendar.MONTH),
                    myCalendar!!.get(Calendar.DAY_OF_MONTH)
                )
                fromDateDialog.show()
            }
            dialogBinding!!.tvApply.setOnClickListener { v: View? ->
                getHomeWorkList()
                dialog.dismiss()
            }
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.iv_filter) {
            classId = ""
            sectionId = ""
            dateSTr = ""
            showFilterPopup()
        } else if (id == R.id.iv_add)
            startActivity(Intent(mActivity!!, AddHomeworkActivity::class.java))
    }
}