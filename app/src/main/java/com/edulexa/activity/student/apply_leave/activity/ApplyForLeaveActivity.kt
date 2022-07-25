package com.edulexa.activity.student.apply_leave.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import com.edulexa.R
import com.edulexa.databinding.ActivityApplyForLeaveStudentBinding
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.MediaStore
import android.view.Window
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.activity.student.apply_leave.adapter.LeaveListAdapter
import com.edulexa.activity.student.apply_leave.model.LeaveListResponse
import com.edulexa.activity.student.online_exam.model.question_ans.UploadFileModel
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.DialogSelectImageBinding
import com.edulexa.support.FileUtils
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ApplyForLeaveActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityApplyForLeaveStudentBinding? = null
    var fromDateSetListener: OnDateSetListener? = null
    var toDateSetListener: OnDateSetListener? = null
    var myCalendar: Calendar? = null

    var cameraOnActivityLaunch: ActivityResultLauncher<Intent>? = null
    var galleryOnActivityLaunch: ActivityResultLauncher<Intent>? = null
    var uploadImageFile: File? = null

    var leaveIdStr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyForLeaveStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        setUpFromDate()
        setUpToDate()
        onActivityCamera()
        onActivityGallery()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.tvFormDate.setOnClickListener(this)
        binding!!.tvToDate.setOnClickListener(this)
        binding!!.ivLeaveAttachment.setOnClickListener(this)
        binding!!.tvApplyLeaveSubmit.setOnClickListener(this)
    }

    private fun setUpFromDate() {
        myCalendar = Calendar.getInstance()
        fromDateSetListener =
            OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                myCalendar!!.set(Calendar.YEAR, year)
                myCalendar!!.set(Calendar.MONTH, monthOfYear)
                myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabelFromData()
            }
    }

    private fun updateLabelFromData() {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding!!.tvFormDate.text = sdf.format(myCalendar!!.time)
    }

    private fun setUpToDate() {
        myCalendar = Calendar.getInstance()
        toDateSetListener =
            OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                myCalendar!!.set(Calendar.YEAR, year)
                myCalendar!!.set(Calendar.MONTH, monthOfYear)
                myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabelToData()
            }
    }

    private fun onActivityCamera() {
        cameraOnActivityLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), @SuppressLint("NotifyDataSetChanged")
            fun(result: ActivityResult) {
                if (result.resultCode === RESULT_OK) {
                    try {
                        val data = result.data
                        val bitmap = data!!.extras!!["data"] as Bitmap?
                        val resultUri: Uri = Utils.getImageUri(mActivity!!, bitmap!!)
                        val filePath: String? = FileUtils.getPath(mActivity!!, resultUri)
                        if (filePath != null) {
                            uploadImageFile = File(filePath)
                            binding!!.ivLeaveAttachment.setImageURI(resultUri)
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }
        )

    }

    private fun onActivityGallery() {
        galleryOnActivityLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode === RESULT_OK) {
                try {
                    val data = result.data
                    val resultUri =
                        Objects.requireNonNull(data)!!.data
                    val filePath = FileUtils.getPath(mActivity!!, resultUri!!)
                    if (filePath != null) {
                        uploadImageFile = File(filePath)
                        binding!!.ivLeaveAttachment.setImageURI(resultUri)
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    private fun updateLabelToData() {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding!!.tvToDate.text = sdf.format(myCalendar!!.time)
    }

    private fun selectImageDialog(){
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
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
                    try {
                        var dialogBinding: DialogSelectImageBinding? = null
                        val dialog = Dialog(mActivity!!)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialogBinding = DialogSelectImageBinding.inflate(layoutInflater)
                        dialog.setContentView(dialogBinding.root)
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.setCanceledOnTouchOutside(false)

                        dialogBinding.tvDialogCamera.setOnClickListener(object :
                            View.OnClickListener {
                            override fun onClick(p0: View?) {
                                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                cameraOnActivityLaunch!!.launch(intent)
                                dialog.dismiss()
                            }
                        })

                        dialogBinding.tvDialogGallery.setOnClickListener(object :
                            View.OnClickListener {
                            override fun onClick(p0: View?) {
                                val pickPhoto = Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                )
                                galleryOnActivityLaunch!!.launch(pickPhoto)
                                dialog.dismiss()
                            }
                        })
                        dialog.show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onDenied(
                    context: Context,
                    deniedPermissions: java.util.ArrayList<String>
                ) {
                    Toast.makeText(
                        mActivity,
                        getString(R.string.permission_denied),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
    }

    private fun submitApplyLeave(){
        if (binding!!.tvFormDate.text.toString().equals(getString(R.string.add_leave_student_select_from_date)))
            Utils.showToastPopup(mActivity!!,getString(R.string.add_leave_student_from_date_validation))
        else if (binding!!.tvToDate.text.toString().equals(getString(R.string.add_leave_student_select_to_date)))
            Utils.showToastPopup(mActivity!!,getString(R.string.add_leave_student_to_date_validation))
        else if (binding!!.etReasonForLeave.text.toString().isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.add_leave_student_reason_validation))
        else if (uploadImageFile == null)
            Utils.showToastPopup(mActivity!!,getString(R.string.add_leave_student_document_select_validation))
        else{
            if (Utils.isNetworkAvailable(mActivity!!)){
                Utils.showProgressBar(mActivity!!)
                Utils.hideKeyboard(mActivity!!)

                val branchId = Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
                val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
                val userId = Utils.getStudentUserId(mActivity!!)

                val apiInterfaceWithHeader: ApiInterfaceStudent = APIClientStudent.getRetroFitClientWithNewKeyHeader(mActivity!!, accessToken,branchId,userId).create(
                    ApiInterfaceStudent::class.java)

                val builder = MultipartBody.Builder()
                builder.setType(MultipartBody.FORM)
                builder.addFormDataPart(Constants.ParamsStudent.STUDENT_SESSION_ID, Utils.getStudentSessionId(mActivity!!));
                builder.addFormDataPart(Constants.ParamsStudent.APPLY_DATE, Utils.getCurrentDate()!!);
                builder.addFormDataPart(Constants.ParamsStudent.FROM_DATE, binding!!.tvFormDate.text.toString());
                builder.addFormDataPart(Constants.ParamsStudent.TO_DATE, binding!!.tvToDate.text.toString());
                builder.addFormDataPart(Constants.ParamsStudent.MESSAGE, binding!!.etReasonForLeave.text.toString());
                builder.addFormDataPart(Constants.ParamsStudent.LEAVE_ID, leaveIdStr);
                builder.addFormDataPart(Constants.ParamsStudent.USERFILE, uploadImageFile!!.name, RequestBody.create(MediaType.parse("multipart/form-data"), uploadImageFile))
                val requestBody = builder.build()


                Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/addLeave")

                val call: Call<ResponseBody> = apiInterfaceWithHeader.addLeave(requestBody)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Utils.hideProgressBar()
                        try{
                            val responseStr = response.body()!!.string()
                            if (!responseStr.isNullOrEmpty()){
                                val jsonObjectResponse = JSONObject(responseStr)
                                val status = jsonObjectResponse.optInt("status")
                                val message = jsonObjectResponse.optString("message")
                                if (status == 1){
                                    Utils.showToast(mActivity!!,message)
                                    onBackPressed()
                                }else Utils.showToast(mActivity!!,message)
                            }else Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                        }catch (e : Exception){
                            e.printStackTrace()
                        }
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Utils.hideProgressBar()
                        Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    }
                })
            }else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

        }
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.tv_form_date){
            Utils.hideKeyboard(mActivity!!)
            val fromDateDialog = DatePickerDialog(
                mActivity!!, fromDateSetListener, myCalendar!!.get(Calendar.YEAR), myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            )
            fromDateDialog.show()
        }
        else if (id == R.id.tv_to_date){
            Utils.hideKeyboard(mActivity!!)
            val fromDateDialog = DatePickerDialog(
                mActivity!!, toDateSetListener, myCalendar!!.get(Calendar.YEAR), myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            )
            fromDateDialog.show()
        }
        else if (id == R.id.iv_leave_attachment)
            selectImageDialog()
        else if (id == R.id.tv_apply_leave_submit)
            submitApplyLeave()
    }

}