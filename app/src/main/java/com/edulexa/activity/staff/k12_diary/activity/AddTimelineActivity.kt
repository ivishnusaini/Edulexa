package com.edulexa.activity.staff.k12_diary.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.edulexa.R
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityAddTimelineStaffBinding
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

class AddTimelineActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityAddTimelineStaffBinding? = null
    var studentId = ""
    var myCalendar: Calendar? = null
    var fromDateSetListener: DatePickerDialog.OnDateSetListener? = null
    var cameraOnActivityLaunch: ActivityResultLauncher<Intent>? = null
    var galleryOnActivityLaunch: ActivityResultLauncher<Intent>? = null
    var uploadImageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTimelineStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        getBundleData()
        setUpFromDate()
        onActivityCamera()
        onActivityGallery()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.dateLay.setOnClickListener(this)
        binding!!.attachDocumentLay.setOnClickListener(this)
        binding!!.tvSave.setOnClickListener(this)
    }

    private fun getBundleData() {
        try {
            val bundle = intent.extras
            studentId = bundle!!.getString(Constants.StaffK12Timeline.STUDENT_ID)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
        binding!!.tvDate.text = sdf.format(myCalendar!!.time)
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
                            binding!!.tvDocument.text =uploadImageFile!!.name
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
                        binding!!.tvDocument.text =uploadImageFile!!.name
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
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

                        dialogBinding.tvDialogCamera.setOnClickListener {
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            cameraOnActivityLaunch!!.launch(intent)
                            dialog.dismiss()
                        }

                        dialogBinding.tvDialogGallery.setOnClickListener {
                            val pickPhoto = Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            )
                            galleryOnActivityLaunch!!.launch(pickPhoto)
                            dialog.dismiss()
                        }
                        dialog.show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onDenied(
                    context: Context,
                    deniedPermissions: ArrayList<String>
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

    private fun saveTimeline(){
        val titleStr = binding!!.etTitle.text.toString().trim()
        val descriptionStr = binding!!.etDescription.text.toString().trim()
        if (titleStr.isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.add_timeline_staff_enter_title_validation))
        else if (binding!!.tvDate.text.toString().equals(getString(R.string.add_timeline_staff_select_date)))
            Utils.showToastPopup(mActivity!!,getString(R.string.add_timeline_staff_select_date_validation))
        else if (descriptionStr.isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.add_timeline_staff_enter_description_validation))
        else if (uploadImageFile == null)
            Utils.showToastPopup(mActivity!!,getString(R.string.add_timeline_staff_upload_document_validation))
        else{
            if (Utils.isNetworkAvailable(mActivity!!)) {
                Utils.showProgressBar(mActivity!!)
                Utils.hideKeyboard(mActivity!!)

                val preference = Preference().getInstance(mActivity!!)
                val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

                val apiInterfaceWithHeader: ApiInterfaceStaff =
                    APIClientStaff.getRetroFitClientWithNewKeyHeader(
                        mActivity!!,
                        Utils.getStaffToken(mActivity!!),
                        Utils.getStaffId(mActivity!!),
                        dbId!!
                    ).create(ApiInterfaceStaff::class.java)

                var visibleCheck = "0"
                if (binding!!.checkBox.isChecked)
                    visibleCheck = "1"

                val builder = MultipartBody.Builder()
                builder.setType(MultipartBody.FORM)
                builder.addFormDataPart(Constants.ParamsStaff.VISIBLE_CHECK, visibleCheck)
                builder.addFormDataPart(Constants.ParamsStaff.TIMELINE_DATE, binding!!.tvDate.text.toString())
                builder.addFormDataPart(Constants.ParamsStaff.TIMELINE_TITLE,titleStr)
                builder.addFormDataPart(Constants.ParamsStaff.TIMELINE_DESC,descriptionStr)
                builder.addFormDataPart(Constants.ParamsStaff.STUDENT_ID,studentId)
                builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID,Utils.getStaffId(mActivity!!))
                builder.addFormDataPart(Constants.ParamsStaff.ROLE_ID,Utils.getStaffRoleId(mActivity!!))
                builder.addFormDataPart(Constants.ParamsStaff.TIMELINE_DOC, uploadImageFile!!.name, RequestBody.create(MediaType.parse("multipart/form-data"), uploadImageFile!!))
                val requestBody = builder.build()


                Utils.printLog("Url", Constants.BASE_URL_STAFF + "add_student_timeline")

                val call: Call<ResponseBody> = apiInterfaceWithHeader.addStudentTimeline(requestBody)
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
                                    Utils.showToast(mActivity!!,getString(R.string.add_timeline_staff_successfully_add_timeline))
                                    onBackPressed()
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
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.date_lay){
            binding!!.tvDate.text = getString(R.string.add_timeline_staff_select_date)
            Utils.hideKeyboard(mActivity!!)
            val fromDateDialog = DatePickerDialog(
                mActivity!!, fromDateSetListener, myCalendar!!.get(Calendar.YEAR), myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            )
            fromDateDialog.show()
        }else if (id == R.id.attach_document_lay)
            selectImageDialog()
        else if (id == R.id.tv_save)
            saveTimeline()
    }

}