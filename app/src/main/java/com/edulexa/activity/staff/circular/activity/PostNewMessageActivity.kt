package com.edulexa.activity.staff.circular.activity

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
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.edulexa.R
import com.edulexa.activity.staff.circular.adapter.MultiGroupSelectAdapter
import com.edulexa.activity.staff.circular.adapter.RoleTypeSpinnerAdapter
import com.edulexa.activity.staff.circular.model.role_type.ClassListRole
import com.edulexa.activity.staff.circular.model.role_type.Role
import com.edulexa.activity.staff.circular.model.role_type.RoleTypeResponse
import com.edulexa.activity.staff.custom_lesson_plan.adapter.MultiSectionSelectAdapter
import com.edulexa.activity.staff.student_profile.model.section.Section
import com.edulexa.activity.staff.student_profile.model.section.SectionResponse
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityPostNewMessageStaffBinding
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

class PostNewMessageActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityPostNewMessageStaffBinding? = null
    var preference: Preference? = null
    var listRole: List<Role?>? = ArrayList()
    var sectionListSpinn: List<Section?>? = ArrayList()
    var classListSpinn: List<ClassListRole?>? = ArrayList()
    var roleTypeSpinnerAdapter: RoleTypeSpinnerAdapter? = null
    var classId = ""
    var messageType = "group"

    var myCalendar: Calendar? = null
    var circularDateSetListener: DatePickerDialog.OnDateSetListener? = null
    var publishDateSetListener: DatePickerDialog.OnDateSetListener? = null

    var uploadImageFile: File? = null

    var cameraOnActivityLaunch: ActivityResultLauncher<Intent>? = null
    var galleryOnActivityLaunch: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostNewMessageStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        setUpCircularDate()
        setUpPublishDate()
        onActivityCamera()
        onActivityGallery()
        getRoleType()

    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.circularDateLay.setOnClickListener(this)
        binding!!.publishDateLay.setOnClickListener(this)
        binding!!.uploadDocumentLay.setOnClickListener(this)
        binding!!.tvGroup.setOnClickListener(this)
        binding!!.tvIndividual.setOnClickListener(this)
        binding!!.tvClass.setOnClickListener(this)
        binding!!.tvSend.setOnClickListener(this)

        binding!!.classSpinn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (classListSpinn!![position]!!.getId() != null) {
                    classId = classListSpinn!![position]!!.getId()!!
                    getSection()
                } else {
                    classId = ""
                    binding!!.sectionRecycler.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setUpCircularDate() {
        myCalendar = Calendar.getInstance()
        circularDateSetListener =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                myCalendar!!.set(Calendar.YEAR, year)
                myCalendar!!.set(Calendar.MONTH, monthOfYear)
                myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabelFromData(binding!!.tvCircularDate)
            }
    }

    private fun setUpPublishDate() {
        myCalendar = Calendar.getInstance()
        publishDateSetListener =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                myCalendar!!.set(Calendar.YEAR, year)
                myCalendar!!.set(Calendar.MONTH, monthOfYear)
                myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabelFromData(binding!!.tvPublishDate)
            }
    }

    private fun updateLabelFromData(textView: TextView) {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textView.text = sdf.format(myCalendar!!.time)
    }

    private fun onActivityCamera() {
        cameraOnActivityLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), @SuppressLint("NotifyDataSetChanged")
            fun(result: ActivityResult) {
                if (result.resultCode == RESULT_OK) {
                    try {
                        val data = result.data
                        val bitmap = data!!.extras!!["data"] as Bitmap?
                        val resultUri: Uri = Utils.getImageUri(mActivity!!, bitmap!!)
                        val filePath: String? = FileUtils.getPath(mActivity!!, resultUri)
                        if (filePath != null) {
                            uploadImageFile = File(filePath)
                            binding!!.tvUploadDocument.text = uploadImageFile!!.name
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
            if (result.resultCode == RESULT_OK) {
                try {
                    val data = result.data
                    val resultUri =
                        Objects.requireNonNull(data)!!.data
                    val filePath = FileUtils.getPath(mActivity!!, resultUri!!)
                    if (filePath != null) {
                        uploadImageFile = File(filePath)
                        binding!!.tvUploadDocument.text = uploadImageFile!!.name
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getRoleType() {
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


            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getRolesClassListForNotifications")

            val call: Call<ResponseBody> =
                apiInterfaceWithHeader.getRolesClassListForNotifications(requestBody)
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
                            val classlistJsonArr = responseJsonObject.optJSONArray("classlist")
                            if (classlistJsonArr != null && classlistJsonArr.length() > 0) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    RoleTypeResponse::class.java
                                ) as RoleTypeResponse
                                if (modelResponse.getRoles()!!.isNotEmpty()) {
                                    listRole = modelResponse.getRoles()
                                    if (modelResponse.getClasslist()!!.isNotEmpty()) {
                                        val classListRole = ClassListRole()
                                        classListRole.setClass_("Select class")
                                        classListSpinn = modelResponse.getClasslist()
                                        (classListSpinn as ArrayList<ClassListRole?>).add(
                                            0,
                                            classListRole
                                        )
                                        roleTypeSpinnerAdapter =
                                            RoleTypeSpinnerAdapter(mActivity!!, classListSpinn)
                                        binding!!.classSpinn.adapter = roleTypeSpinnerAdapter
                                    }
                                    binding!!.groupRecycler.visibility = View.VISIBLE
                                    binding!!.groupRecycler.layoutManager = LinearLayoutManager(
                                        mActivity!!,
                                        LinearLayoutManager.VERTICAL,
                                        false
                                    )
                                    binding!!.groupRecycler.adapter =
                                        MultiGroupSelectAdapter(mActivity!!, listRole)
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
        binding!!.sectionRecycler.visibility = View.GONE
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
                                    (sectionListSpinn as ArrayList<Section?>).clear()
                                    sectionListSpinn = modelResponse.getSectionList()
                                    if (sectionListSpinn!!.isNotEmpty()) {
                                        binding!!.sectionRecycler.visibility = View.VISIBLE
                                        binding!!.sectionRecycler.layoutManager =
                                            LinearLayoutManager(
                                                mActivity!!,
                                                LinearLayoutManager.VERTICAL,
                                                false
                                            )
                                        binding!!.sectionRecycler.adapter =
                                            MultiSectionSelectAdapter(mActivity!!, sectionListSpinn)
                                    }
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


    private fun resetAll(type: String) {
        messageType = type
        binding!!.tvGroup.setBackgroundResource(R.drawable.bg_border_5)
        binding!!.tvGroup.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.tvIndividual.setBackgroundResource(R.drawable.bg_border_5)
        binding!!.tvIndividual.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.tvClass.setBackgroundResource(R.drawable.bg_border_5)
        binding!!.tvClass.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.groupRecycler.visibility = View.GONE
        binding!!.classSectionLay.visibility = View.GONE
        when (type) {
            "group" -> {
                binding!!.groupRecycler.visibility = View.VISIBLE
                binding!!.classSectionLay.visibility = View.GONE
                binding!!.tvGroup.setBackgroundResource(R.drawable.bg_button_5)
                binding!!.tvGroup.setTextColor(ContextCompat.getColor(mActivity!!, R.color.white))
            }
            "individual" -> {
                binding!!.tvIndividual.setBackgroundResource(R.drawable.bg_button_5)
                binding!!.tvIndividual.setTextColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.primaray_text_color
                    )
                )
            }
            "class" -> {
                binding!!.groupRecycler.visibility = View.GONE
                binding!!.classSectionLay.visibility = View.VISIBLE
                binding!!.tvClass.setBackgroundResource(R.drawable.bg_button_5)
                binding!!.tvClass.setTextColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.primaray_text_color
                    )
                )
            }
        }
    }

    private fun selectImageDialog() {
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

                        dialogBinding.tvDialogFileManager.visibility = View.GONE

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

    private fun getRoleTypeSelectStatus(): Boolean {
        var flag = false
        for (model in listRole!!) {
            if (model!!.isSelectRole()) {
                flag = true
                break
            }
        }
        return flag
    }

    private fun getSectionSelectStatus(): Boolean {
        var flag = false
        for (model in sectionListSpinn!!) {
            if (model!!.isSectionSelect()) {
                flag = true
                break
            }
        }
        return flag
    }

    private fun sendMessage() {
        val titleStr = binding!!.etTitle.text.toString().trim()
        val messageStr = binding!!.etMessage.text.toString().trim()
        if (titleStr.isEmpty())
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.circular_staff_enter_title_validation)
            )
        else if (binding!!.tvCircularDate.text.toString() == getString(R.string.circular_staff_select_circular_date))
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.circular_staff_select_circular_date_validation)
            )
        else if (binding!!.tvPublishDate.text.toString() == getString(R.string.circular_staff_select_publish_date))
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.circular_staff_select_publish_date_validation)
            )
        else if (messageStr.isEmpty())
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.circular_staff_enter_message_validation)
            )
        else {
            when (messageType) {
                "group" -> {
                    if (!getRoleTypeSelectStatus())
                        Utils.showToastPopup(
                            mActivity!!,
                            getString(R.string.circular_staff_select_role_type_validation)
                        )
                    else sendMessageGroup()
                }
                "individual" -> {
                    sendMessageIndividual()
                }
                "class" -> {
                    if (!getSectionSelectStatus())
                        Utils.showToastPopup(
                            mActivity!!,
                            getString(R.string.circular_staff_select_section_validation)
                        )
                    else sendMessageClass()
                }
            }
        }
    }

    private fun sendMessageGroup() {
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


            var userIdStr = ""
            for (model in listRole!!) {
                if (model!!.isSelectRole()) {
                    if (userIdStr == "")
                        userIdStr = model.getId()!!
                    else userIdStr = userIdStr + "," + model.getId()
                }
            }

            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(
                Constants.ParamsStaff.GROUP_TITLE,
                binding!!.etTitle.text.toString().trim()
            )
            builder.addFormDataPart(
                Constants.ParamsStaff.GROUP_MESSAGE,
                binding!!.etMessage.text.toString().trim()
            )
            builder.addFormDataPart(
                Constants.ParamsStaff.DATE,
                binding!!.tvCircularDate.text.toString()
            )
            builder.addFormDataPart(
                Constants.ParamsStaff.PUBLISH_DATE,
                binding!!.tvPublishDate.text.toString()
            )
            builder.addFormDataPart(Constants.ParamsStaff.USER, userIdStr)
            if (uploadImageFile != null)
                builder.addFormDataPart(
                    Constants.ParamsStaff.FILE, uploadImageFile!!.name, RequestBody.create(
                        MediaType.parse("multipart/form-data"), uploadImageFile!!
                    )
                )
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
            val requestBody = builder.build()


            Utils.printLog("Url", Constants.BASE_URL_STAFF + "sendGroup")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.sendGroup(requestBody)
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
                            val message = responseJsonObject.optString("msg")
                            Utils.showToast(mActivity!!, message)
                            onBackPressed()
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

    private fun sendMessageIndividual() {

    }

    private fun sendMessageClass() {
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


            var sectionIdStr = ""
            for (model in sectionListSpinn!!) {
                if (model!!.isSectionSelect()) {
                    if (sectionIdStr == "")
                        sectionIdStr = model.getSectionId()!!
                    else sectionIdStr = sectionIdStr + "," + model.getSectionId()
                }
            }

            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(
                Constants.ParamsStaff.TITLE,
                binding!!.etTitle.text.toString().trim()
            )
            builder.addFormDataPart(
                Constants.ParamsStaff.MESSAGE,
                binding!!.etMessage.text.toString().trim()
            )
            builder.addFormDataPart(
                Constants.ParamsStaff.DATE,
                binding!!.tvCircularDate.text.toString()
            )
            builder.addFormDataPart(
                Constants.ParamsStaff.PUBLISH_DATE,
                binding!!.tvPublishDate.text.toString()
            )
            builder.addFormDataPart(Constants.ParamsStaff.SECTION, sectionIdStr)
            builder.addFormDataPart(Constants.ParamsStaff.CLASS, classId)
            if (uploadImageFile != null)
                builder.addFormDataPart(
                    Constants.ParamsStaff.FILE, uploadImageFile!!.name, RequestBody.create(
                        MediaType.parse("multipart/form-data"), uploadImageFile!!
                    )
                )
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
            val requestBody = builder.build()


            Utils.printLog("Url", Constants.BASE_URL_STAFF + "sendClass")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.sendClass(requestBody)
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
                            val message = responseJsonObject.optString("msg")
                            Utils.showToast(mActivity!!, message)
                            onBackPressed()
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
        else if (id == R.id.tv_group)
            resetAll("group")
        else if (id == R.id.tv_individual)
            resetAll("individual")
        else if (id == R.id.tv_class)
            resetAll("class")
        else if (id == R.id.circular_date_lay) {
            binding!!.tvCircularDate.text = getString(R.string.circular_staff_select_circular_date)
            Utils.hideKeyboard(mActivity!!)
            val fromDateDialog = DatePickerDialog(
                mActivity!!,
                circularDateSetListener,
                myCalendar!!.get(Calendar.YEAR),
                myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            )
            fromDateDialog.show()
        } else if (id == R.id.publish_date_lay) {
            binding!!.tvPublishDate.text = getString(R.string.circular_staff_select_publish_date)
            Utils.hideKeyboard(mActivity!!)
            val fromDateDialog = DatePickerDialog(
                mActivity!!,
                publishDateSetListener,
                myCalendar!!.get(Calendar.YEAR),
                myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            )
            fromDateDialog.show()
        } else if (id == R.id.upload_document_lay)
            selectImageDialog()
        else if (id == R.id.tv_send)
            sendMessage()
    }
}