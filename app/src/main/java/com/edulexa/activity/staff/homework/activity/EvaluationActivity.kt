package com.edulexa.activity.staff.homework.activity

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.edulexa.R
import com.edulexa.activity.staff.homework.adapter.EvaluationListAdapter
import com.edulexa.activity.staff.homework.model.evaluation.EvaluationResponse
import com.edulexa.activity.staff.homework.model.evaluation.StudentEvaluation
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityEvaluationStaffBinding
import com.edulexa.databinding.DialogStaffHomeworkShowDescriptionBinding
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

class EvaluationActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityEvaluationStaffBinding? = null
    var preference : Preference? = null
    var homeworkId = ""

    var myCalendar: Calendar? = null
    var fromDateSetListener: DatePickerDialog.OnDateSetListener? = null

    var modelResponse : EvaluationResponse? = null
    var listStudent : List<StudentEvaluation?>? = null
    var downloadID: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEvaluationStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        getBundleData()
        setUpEvaluationDate()
        getEvaluationList()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.tvEvaluationDate.setOnClickListener(this)
        binding!!.ivDownload.setOnClickListener(this)
        binding!!.tvDescription.setOnClickListener(this)
        binding!!.tvSave.setOnClickListener(this)
    }
    private fun getBundleData() {
        try {
            val bundle = intent.extras
            homeworkId = bundle!!.getString(Constants.StaffHomework.HOMEWORK_ID)!!

            registerReceiver(
                onDownloadComplete,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setUpEvaluationDate() {
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
        binding!!.tvEvaluationDate.text = sdf.format(myCalendar!!.time)
    }

    private fun getEvaluationList(){
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
            builder.addFormDataPart(Constants.ParamsStaff.HOMEWORK_ID, homeworkId)
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "evaluation")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.evaluation(requestBody)
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
                            val studentListJsonArr = responseJsonObject.optJSONArray("studentlist")
                            if (studentListJsonArr != null) {
                                modelResponse = Utils.getObject(
                                    responseStr,
                                    EvaluationResponse::class.java
                                ) as EvaluationResponse
                                if (modelResponse!!.getStudentlist()!!.isNotEmpty()) {
                                    binding!!.recyclerView.visibility = View.VISIBLE
                                    binding!!.tvNoData.visibility = View.VISIBLE
                                    binding!!.recyclerView.layoutManager = LinearLayoutManager(mActivity,
                                        LinearLayoutManager.VERTICAL,false)
                                    listStudent = modelResponse!!.getStudentlist()
                                    for (model in listStudent!!){
                                        model!!.setNameSelect(model.getHomeworkEvaluationId() != "0")
                                    }
                                    binding!!.recyclerView.adapter = EvaluationListAdapter(mActivity!!,listStudent)
                                    if (modelResponse!!.getResult() != null){
                                        binding!!.tvEvaluationDate.text = modelResponse!!.getResult()!!.getEvaluationDate()
                                        setUpDescription()
                                    }
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

    private fun setUpDescription(){
        try {
            if (modelResponse!!.getResult()!!.getDocument() != "")
                binding!!.ivDownload.setOnClickListener(this)
            else binding!!.ivDownload.visibility = View.GONE
            binding!!.tvDescription.text = modelResponse!!.getResult()!!.getDescription()
            if (modelResponse!!.getResult()!!.getDescription() != "null")
                binding!!.tvDescription.setOnClickListener(this)
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun downloadAttachment(){
        val downloadUrl = Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF+"uploads/homework/"+modelResponse!!.getResult()!!.getDocument()
        downloadID = Utils.startDownload(mActivity!!,downloadUrl,downloadUrl)
    }

    private fun showDescriptionPopup(){
        try {
            val dialogBinding: DialogStaffHomeworkShowDescriptionBinding?
            val dialog = Dialog(mActivity!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBinding = DialogStaffHomeworkShowDescriptionBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)

            dialogBinding.tvDescription.text = modelResponse!!.getResult()!!.getDescription()

            dialogBinding.btnOk.setOnClickListener { v: View? ->
                dialog.dismiss()
            }
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveEvaluation(){
        if (allNameSelectedStatus()){
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

                val studentListJsonObj = JSONObject()
                val commentJsonObj = JSONObject()
                val reSubmitJsonObj = JSONObject()
                val studentIdJsonObj = JSONObject()
                for (model in listStudent!!){
                    if (model!!.isNameSelect()){
                        var homeEvaluationId = ""
                        if (model.getHomeworkEvaluationId() != "0")
                            homeEvaluationId = model.getHomeworkEvaluationId()!!
                        studentListJsonObj.put(model.getStudentSessionId()!!, homeEvaluationId)

                        commentJsonObj.put(model.getStudentSessionId()!!, model.getComment())

                        var resubmitValue = "0"
                        if (model.isResubmitSelect()) resubmitValue = "1"
                        reSubmitJsonObj.put(model.getStudentSessionId()!!, resubmitValue)

                        studentIdJsonObj.put(model.getStudentSessionId()!!, model.getStudentId())
                    }
                }

                val builder = MultipartBody.Builder()
                builder.setType(MultipartBody.FORM)
                builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
                builder.addFormDataPart(Constants.ParamsStaff.HOMEWORK_ID, homeworkId)
                builder.addFormDataPart(Constants.ParamsStaff.STUDENT_LIST, studentListJsonObj.toString())
                builder.addFormDataPart(Constants.ParamsStaff.COMMENT, commentJsonObj.toString())
                builder.addFormDataPart(Constants.ParamsStaff.RESUBMIT, reSubmitJsonObj.toString())
                builder.addFormDataPart(Constants.ParamsStaff.STUDENT_ID, studentIdJsonObj.toString())
                builder.addFormDataPart(Constants.ParamsStaff.STUDENT_SESSION_ID, Utils.getStudentSessionId(mActivity!!))
                val requestBody = builder.build()

                Utils.printLog("Url", Constants.BASE_URL_STAFF + "addEvaluvation")

                val call: Call<ResponseBody> = apiInterfaceWithHeader.addEvaluvation(requestBody)
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
                                val message = responseJsonObject.optString("message")
                                Utils.showToast(mActivity!!,message)
                                onBackPressed()
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

        }else Utils.showToastPopup(mActivity!!,getString(R.string.homework_staff_select_name_validation))
    }

    private fun allNameSelectedStatus() : Boolean{
        var flag = false
        for (model in listStudent!!){
            if (model!!.isNameSelect()){
                flag = true
                break
            }
        }
        return flag
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.upload_date_lay) {
            Utils.hideKeyboard(mActivity!!)
            val fromDateDialog = DatePickerDialog(
                mActivity!!,
                fromDateSetListener,
                myCalendar!!.get(Calendar.YEAR),
                myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            )
            fromDateDialog.show()
        }else if (id == R.id.iv_download)
            downloadAttachment()
        else if (id == R.id.tv_description)
            showDescriptionPopup()
        else if (id == R.id.tv_save)
            saveEvaluation()
    }
    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadID == id) {
                val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.app_icon)
                    .setContentTitle(context.applicationContext.getString(R.string.app_name))
                    .setContentText("All Download completed")
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(455, mBuilder.build())
            }
        }
    }
}