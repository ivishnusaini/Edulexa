package com.edulexa.activity.staff.homework.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.edulexa.R
import com.edulexa.activity.staff.custom_lesson_plan.adapter.ClassSpinnerAdapter
import com.edulexa.activity.staff.custom_lesson_plan.adapter.MultiSectionSelectAdapter
import com.edulexa.activity.staff.homework.adapter.EvaluationListAdapter
import com.edulexa.activity.staff.homework.adapter.SubjectGroupSpinnerAdapter
import com.edulexa.activity.staff.homework.adapter.SubjectSpinnerAdapter
import com.edulexa.activity.staff.homework.model.evaluation.EvaluationResponse
import com.edulexa.activity.staff.homework.model.subject.Subject
import com.edulexa.activity.staff.homework.model.subject.SubjectResponse
import com.edulexa.activity.staff.homework.model.subject_group.SubjectGroup
import com.edulexa.activity.staff.homework.model.subject_group.SubjectGroupResponse
import com.edulexa.activity.staff.online_exam.adapter.SectionSpinnerAdapter
import com.edulexa.activity.staff.student_profile.model.class_list.ClassData
import com.edulexa.activity.staff.student_profile.model.class_list.ClassResponse
import com.edulexa.activity.staff.student_profile.model.section.Section
import com.edulexa.activity.staff.student_profile.model.section.SectionResponse
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityAddHomeworkStaffBinding
import com.edulexa.databinding.DialogSelectImageBinding
import com.edulexa.databinding.DialogStaffSelectMultipleSectionBinding
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

class AddHomeworkActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityAddHomeworkStaffBinding? = null
    var preference: Preference? = null

    var classListSpinn: List<ClassData?>? = ArrayList()
    var classSpinnerAdapter: ClassSpinnerAdapter? = null
    var classId = ""
    var sectionListSpinn: List<Section?>? = ArrayList()
    var sectionSpinnerAdapter: SectionSpinnerAdapter? = null
    var sectionId = ""
    var sectionName = ""
    var subjectGroupListSpinn: List<SubjectGroup?>? = ArrayList()
    var subjectGroupSpinnerAdapter: SubjectGroupSpinnerAdapter? = null
    var subjectGroupId = ""
    var subjectListSpinn: List<Subject?>? = ArrayList()
    var subjectSpinnerAdapter: SubjectSpinnerAdapter? = null
    var subjectId = ""


    var myCalendar: Calendar? = null
    var homeworkDateSetListener: DatePickerDialog.OnDateSetListener? = null
    var submissionDateSetListener: DatePickerDialog.OnDateSetListener? = null

    var cameraOnActivityLaunch: ActivityResultLauncher<Intent>? = null
    var galleryOnActivityLaunch: ActivityResultLauncher<Intent>? = null
    var documentActivityLaunch: ActivityResultLauncher<Intent>? = null

    var uploadImageFile: File? = null
    var fileSize: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHomeworkStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        setUpHomeworkDate()
        setUpSubmissionDate()
        onActivityCamera()
        onActivityGallery()
        onActivityDocument()
        getClassList()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.sectionLay.setOnClickListener(this)
        binding!!.uploadHomeworkDateLay.setOnClickListener(this)
        binding!!.submissionDateLay.setOnClickListener(this)
        binding!!.uploadDocumentLay.setOnClickListener(this)
        binding!!.tvAdd.setOnClickListener(this)
        binding!!.classSpinn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (classListSpinn!![position]!!.getId() != "") {
                    classId = classListSpinn!![position]!!.getId()!!
                    getSection()
                } else {
                    classId = ""
                    (sectionListSpinn as ArrayList<Section?>).clear()
                    if (sectionSpinnerAdapter != null)
                        sectionSpinnerAdapter!!.notifyDataSetChanged()
                    sectionId = ""

                    (subjectGroupListSpinn as ArrayList<SubjectGroup?>).clear()
                    if (subjectGroupSpinnerAdapter != null)
                        subjectGroupSpinnerAdapter!!.notifyDataSetChanged()
                    subjectGroupId = ""

                    (subjectListSpinn as ArrayList<Subject?>).clear()
                    if (subjectSpinnerAdapter != null)
                        subjectSpinnerAdapter!!.notifyDataSetChanged()
                    subjectId = ""
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding!!.subjectGroupSpinn.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (subjectGroupListSpinn!![position]!!.getSubjectGroupId() != "") {
                        subjectGroupId = subjectGroupListSpinn!![position]!!.getSubjectGroupId()!!
                        getSubject()
                    } else {
                        subjectGroupId = ""
                        (subjectListSpinn as ArrayList<Subject?>).clear()
                        if (subjectSpinnerAdapter != null)
                            subjectSpinnerAdapter!!.notifyDataSetChanged()
                        subjectId = ""
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        binding!!.subjectSpinn.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (subjectListSpinn!![position]!!.getId() != "") {
                        subjectId = subjectListSpinn!![position]!!.getId()!!
                    } else
                        subjectId = ""
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun setUpHomeworkDate() {
        myCalendar = Calendar.getInstance()
        homeworkDateSetListener =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                myCalendar!!.set(Calendar.YEAR, year)
                myCalendar!!.set(Calendar.MONTH, monthOfYear)
                myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabelFromData(binding!!.tvHomeworkDate)
            }
    }

    private fun setUpSubmissionDate() {
        myCalendar = Calendar.getInstance()
        submissionDateSetListener =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                myCalendar!!.set(Calendar.YEAR, year)
                myCalendar!!.set(Calendar.MONTH, monthOfYear)
                myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabelFromData(binding!!.tvSubmissionDate)
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
                            val fileSizeKb: Int =
                                (uploadImageFile!!.length() / 1024).toString().toInt()
                            fileSize = fileSizeKb / 1024
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
                        val fileSizeKb: Int = (uploadImageFile!!.length() / 1024).toString().toInt()
                        fileSize = fileSizeKb / 1024
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun onActivityDocument() {
        documentActivityLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                try {
                    val data = result.data
                    val resultUri =
                        Objects.requireNonNull(data)!!.data
                    val filePath = FileUtils.getPath(mActivity!!, resultUri!!)
                    if (filePath != null) {
                        uploadImageFile = File(filePath)
                        binding!!.tvUploadDocument.text = uploadImageFile!!.name
                        val fileSizeKb: Int = (uploadImageFile!!.length() / 1024).toString().toInt()
                        fileSize = fileSizeKb / 1024
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getClassList() {
        (classListSpinn as ArrayList<ClassData?>).clear()
        if (classSpinnerAdapter != null)
            classSpinnerAdapter!!.notifyDataSetChanged()
        classId = ""
        (sectionListSpinn as ArrayList<Section?>).clear()
        if (sectionSpinnerAdapter != null)
            sectionSpinnerAdapter!!.notifyDataSetChanged()
        sectionId = ""
        (subjectGroupListSpinn as ArrayList<SubjectGroup?>).clear()
        if (subjectGroupSpinnerAdapter != null)
            subjectGroupSpinnerAdapter!!.notifyDataSetChanged()
        subjectGroupId = ""
        (subjectListSpinn as ArrayList<Subject?>).clear()
        if (subjectSpinnerAdapter != null)
            subjectSpinnerAdapter!!.notifyDataSetChanged()
        subjectId = ""
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
                                    binding!!.classSpinn.adapter = classSpinnerAdapter
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
        sectionId = ""
        sectionName = ""
        binding!!.tvSection.text = sectionName
        (subjectGroupListSpinn as ArrayList<SubjectGroup?>).clear()
        if (subjectGroupSpinnerAdapter != null)
            subjectGroupSpinnerAdapter!!.notifyDataSetChanged()
        subjectGroupId = ""
        (subjectListSpinn as ArrayList<Subject?>).clear()
        if (subjectSpinnerAdapter != null)
            subjectSpinnerAdapter!!.notifyDataSetChanged()
        subjectId = ""
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
                                    sectionListSpinn = modelResponse.getSectionList()
                                    if (sectionListSpinn!!.isNotEmpty()) {
                                        getSubjectGroup(sectionListSpinn!![0]!!.getSectionId()!!)
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

    private fun getSubjectGroup(sectionId : String) {
        (subjectGroupListSpinn as ArrayList<SubjectGroup?>).clear()
        if (subjectGroupSpinnerAdapter != null)
            subjectGroupSpinnerAdapter!!.notifyDataSetChanged()
        subjectGroupId = ""
        (subjectListSpinn as ArrayList<Subject?>).clear()
        if (subjectSpinnerAdapter != null)
            subjectSpinnerAdapter!!.notifyDataSetChanged()
        subjectId = ""
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
            builder.addFormDataPart(Constants.ParamsStaff.CLASS_ID, classId)
            builder.addFormDataPart(Constants.ParamsStaff.SECTION_ID, sectionId)
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getSubjectGroupByClassAndSection")

            val call: Call<ResponseBody> =
                apiInterfaceWithHeader.getSubjectGroupByClassAndSection(requestBody)
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
                            val subjectGroupListJsonArr =
                                responseJsonObject.optJSONArray("subject_group_list")
                            if (subjectGroupListJsonArr != null) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    SubjectGroupResponse::class.java
                                ) as SubjectGroupResponse
                                if (modelResponse.getSubjectGroupList()!!.isNotEmpty()) {
                                    val subjectGroup = SubjectGroup()
                                    subjectGroup.setName("Select subject group")
                                    subjectGroupListSpinn = modelResponse.getSubjectGroupList()
                                    (subjectGroupListSpinn as ArrayList<SubjectGroup?>).add(
                                        0,
                                        subjectGroup
                                    )
                                    subjectGroupSpinnerAdapter =
                                        SubjectGroupSpinnerAdapter(
                                            mActivity!!,
                                            subjectGroupListSpinn
                                        )
                                    binding!!.subjectGroupSpinn.adapter = subjectGroupSpinnerAdapter
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

    private fun getSubject() {
        (subjectListSpinn as ArrayList<Subject?>).clear()
        if (subjectSpinnerAdapter != null)
            subjectSpinnerAdapter!!.notifyDataSetChanged()
        subjectId = ""
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
            builder.addFormDataPart(Constants.ParamsStaff.SUBJECT_GROUP_ID, subjectGroupId)
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getGroupSubjects")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getGroupSubjects(requestBody)
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
                            val subjectListJsonArr = responseJsonObject.optJSONArray("subject_list")
                            if (subjectListJsonArr != null) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    SubjectResponse::class.java
                                ) as SubjectResponse
                                if (modelResponse.getSubjectList()!!.isNotEmpty()) {
                                    val subject = Subject()
                                    subject.setName("Select subject")
                                    subjectListSpinn = modelResponse.getSubjectList()
                                    (subjectListSpinn as ArrayList<Subject?>).add(0, subject)
                                    subjectSpinnerAdapter =
                                        SubjectSpinnerAdapter(mActivity!!, subjectListSpinn)
                                    binding!!.subjectSpinn.adapter = subjectSpinnerAdapter
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

                        dialogBinding.tvDialogFileManager.visibility = View.VISIBLE

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
                        dialogBinding.tvDialogFileManager.setOnClickListener {
                            try {
                                val intent = Intent(Intent.ACTION_GET_CONTENT)
                                intent.type = "application/*"
                                intent.addCategory(Intent.CATEGORY_OPENABLE)
                                documentActivityLaunch!!.launch(intent)
                            } catch (ex: ActivityNotFoundException) {
                                Toast.makeText(
                                    mActivity, "Please install a File Manager.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
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

    private fun setSectionSpinnerAdapter() {
        try {
            val dialog = Dialog(mActivity!!)
            var dialogBinding: DialogStaffSelectMultipleSectionBinding? = null
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBinding = DialogStaffSelectMultipleSectionBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)

            dialogBinding.multiSectionRecycler.layoutManager =
                LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
            val multiSectionAdapter = MultiSectionSelectAdapter(mActivity!!, sectionListSpinn)
            dialogBinding.multiSectionRecycler.adapter = multiSectionAdapter

            dialogBinding.btnCancel.setOnClickListener {
                sectionName = ""
                sectionId = ""
                binding!!.tvSection.text = getString(R.string.add_custom_lesson_plan_staff_section)
                dialog.dismiss()
                dialogBinding.multiSectionRecycler.adapter = multiSectionAdapter
            }

            dialogBinding.btnSelect.setOnClickListener {
                sectionName = ""
                sectionId = ""
                for (i in sectionListSpinn!!.indices) {
                    if (sectionListSpinn!![i]!!.isSectionSelect()) {
                        val name = sectionListSpinn!![i]!!.getSection()
                        if (sectionName == "") {
                            sectionName = name!!
                            sectionId = sectionListSpinn!![i]!!.getId()!!
                        } else {
                            sectionName = sectionName + "," + name
                            sectionId =
                                sectionId + "," + sectionListSpinn!![i]!!.getId()
                        }
                    }
                }

                binding!!.tvSection.text = sectionName
                dialog.dismiss()
            }

            if (dialog.isShowing) dialog.dismiss()
            dialog.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


    private fun addHomework() {
        val descriptionStr = binding!!.etDescription.text.toString().trim()
        if (classId == "")
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.add_custom_lesson_plan_staff_select_class_validation)
            )
        else if (sectionId == "")
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.add_custom_lesson_plan_staff_select_section_validation)
            )
        else if (subjectGroupId == "")
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.homework_staff_select_subject_group_validation)
            )
        else if (subjectId == "")
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.add_custom_lesson_plan_staff_select_subject_validation)
            )
        else if (binding!!.tvHomeworkDate.text.toString() == getString(R.string.homework_staff_add_homework_select_homework_date))
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.homework_staff_select_homework_date_validation)
            )
        else if (binding!!.tvSubmissionDate.text.toString() == getString(R.string.homework_staff_add_homework_select_submission_date))
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.homework_staff_select_submission_date_validation)
            )
        else if (uploadImageFile == null)
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.homework_staff_select_upload_image_validation)
            )
        else if (fileSize > 10)
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.homework_staff_select_upload_image_size_validation)
            )
        else if (descriptionStr.isEmpty())
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.homework_staff_select_description_validation)
            )
        else {
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
                builder.addFormDataPart(
                    Constants.ParamsStaff.STAFF_ID,
                    Utils.getStaffId(mActivity!!)
                )
                builder.addFormDataPart(Constants.ParamsStaff.CLASS_ID, classId)
                builder.addFormDataPart(Constants.ParamsStaff.SECTION_ID, sectionId)
                builder.addFormDataPart(Constants.ParamsStaff.SUBJECT_GROUP_ID, subjectGroupId)
                builder.addFormDataPart(Constants.ParamsStaff.SUBJECT_ID, subjectId)
                builder.addFormDataPart(
                    Constants.ParamsStaff.HOMEWORK_DATE,
                    binding!!.tvHomeworkDate.text.toString()
                )
                builder.addFormDataPart(
                    Constants.ParamsStaff.SUBMIT_DATE,
                    binding!!.tvSubmissionDate.text.toString()
                )
                builder.addFormDataPart(Constants.ParamsStaff.DESCRIPTION, descriptionStr)
                if (uploadImageFile != null)
                    builder.addFormDataPart(
                        Constants.ParamsStaff.USERFILE, uploadImageFile!!.name, RequestBody.create(
                            MediaType.parse("multipart/form-data"), uploadImageFile!!
                        )
                    )
                val requestBody = builder.build()

                Utils.printLog("Url", Constants.BASE_URL_STAFF + "addHomework")

                val call: Call<ResponseBody> = apiInterfaceWithHeader.addHomework(requestBody)
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
                                val homeworkId = responseJsonObject.optString("homework_id")
                                Utils.showToast(mActivity!!,message)
                                if (homeworkId != "")
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

    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.section_lay) {
            if (classId != "") {
                sectionId = ""
                sectionName = ""
                setSectionSpinnerAdapter()
            }
        } else if (id == R.id.upload_homework_date_lay) {
            binding!!.tvHomeworkDate.text =
                getString(R.string.homework_staff_add_homework_select_homework_date)
            Utils.hideKeyboard(mActivity!!)
            val fromDateDialog = DatePickerDialog(
                mActivity!!,
                homeworkDateSetListener,
                myCalendar!!.get(Calendar.YEAR),
                myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            )
            fromDateDialog.show()
        } else if (id == R.id.submission_date_lay) {
            binding!!.tvSubmissionDate.text =
                getString(R.string.homework_staff_add_homework_select_submission_date)
            Utils.hideKeyboard(mActivity!!)
            val fromDateDialog = DatePickerDialog(
                mActivity!!,
                submissionDateSetListener,
                myCalendar!!.get(Calendar.YEAR),
                myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            )
            fromDateDialog.show()
        } else if (id == R.id.upload_document_lay)
            selectImageDialog()
        else if (id == R.id.tv_add)
            addHomework()
    }

}