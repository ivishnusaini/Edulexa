package com.edulexa.activity.staff.custom_lesson_plan.activity

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
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Scroller
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.edulexa.R
import com.edulexa.activity.staff.custom_lesson_plan.adapter.ClassSpinnerAdapter
import com.edulexa.activity.staff.custom_lesson_plan.adapter.MultiSectionSelectAdapter
import com.edulexa.activity.staff.custom_lesson_plan.adapter.SubjectSpinnerAdapter
import com.edulexa.activity.staff.custom_lesson_plan.model.add_lesson.DatumAddLesson
import com.edulexa.activity.staff.custom_lesson_plan.model.add_lesson.SubjectAddLessonResponse
import com.edulexa.activity.staff.custom_lesson_plan.model.lesson_plan_list.FormFields
import com.edulexa.activity.staff.student_profile.model.class_list.ClassData
import com.edulexa.activity.staff.student_profile.model.class_list.ClassResponse
import com.edulexa.activity.staff.student_profile.model.section.Section
import com.edulexa.activity.staff.student_profile.model.section.SectionResponse
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityAddCustomLessonStaffBinding
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

class AddCustomLessonActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityAddCustomLessonStaffBinding? = null

    var selectedClassId = ""
    var selectedSectionId = ""
    var selectedSectionName = ""
    var selectedSubjectId = ""
    var idStr = ""
    var fromFieldStr = ""

    var myCalendar: Calendar? = null
    var fromDateSetListener: DatePickerDialog.OnDateSetListener? = null

    var preference: Preference? = null

    var classListSpinn: List<ClassData?>? = ArrayList()
    var classSpinnerAdapter: ClassSpinnerAdapter? = null
    var classId = ""
    var sectionListSpinn: List<Section?>? = ArrayList()
    var subjectListSpinn: List<DatumAddLesson?>? = ArrayList()
    var subjectSpinnerAdapter: SubjectSpinnerAdapter? = null
    var subjectId = ""

    var cameraOnActivityLaunch: ActivityResultLauncher<Intent>? = null
    var galleryOnActivityLaunch: ActivityResultLauncher<Intent>? = null
    var documentActivityLaunch: ActivityResultLauncher<Intent>? = null

    var uploadImageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCustomLessonStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        getBundleData()
        getFormFieldData()
        setUpFromDate()
        onActivityCamera()
        onActivityGallery()
        onActivityDocument()
        getClassList()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.sectionLay.setOnClickListener(this)
        binding!!.uploadDateLay.setOnClickListener(this)
        binding!!.uploadDocumentLay.setOnClickListener(this)
        binding!!.tvAdd.setOnClickListener(this)
        binding!!.classSpinn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (classListSpinn!![position]!!.getId() != null) {
                    classId = classListSpinn!![position]!!.getId()!!
                    selectedClassId = ""
                    getSection()
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
                    if (subjectListSpinn!![position]!!.getId() != null) {
                        subjectId = subjectListSpinn!![position]!!.getId()!!
                    } else subjectId = ""
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        binding!!.etDescription.setScroller(Scroller(mActivity))
        binding!!.etDescription.setVerticalScrollBarEnabled(true)
        binding!!.etDescription.setMovementMethod(ScrollingMovementMethod())
    }

    private fun getBundleData() {
        try {
            val bundle = intent.extras
            selectedClassId = bundle!!.getString(Constants.StaffCustomLessonPlan.CLASS_ID)!!
            selectedSectionId = bundle.getString(Constants.StaffCustomLessonPlan.SECTION_ID)!!
            selectedSectionName = bundle.getString(Constants.StaffCustomLessonPlan.SECTION_NAME)!!
            selectedSubjectId = bundle.getString(Constants.StaffCustomLessonPlan.SUBJECT_ID)!!
            idStr = bundle.getString(Constants.StaffCustomLessonPlan.ID)!!
            fromFieldStr = bundle.getString(Constants.StaffCustomLessonPlan.FORM_FIELD)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getFormFieldData() {
        try {
            if (fromFieldStr != "") {
                val formFieldModel =
                    Utils.getObject(fromFieldStr, FormFields::class.java) as FormFields
                if (formFieldModel.getIsLesson()
                        .equals("1")
                ) binding!!.lessonLay.visibility =
                    View.VISIBLE else binding!!.lessonLay.visibility =
                    View.GONE
                if (formFieldModel.getIsPreviousKnowledge()
                        .equals("1")
                ) binding!!.previousKnowledgeLay.visibility =
                    View.VISIBLE else binding!!.previousKnowledgeLay.visibility =
                    View.GONE
                if (formFieldModel.getIsComprehensiveQuestions()
                        .equals("1")
                ) binding!!.comprehensiveQuestionsLay.visibility =
                    View.VISIBLE else binding!!.comprehensiveQuestionsLay.visibility =
                    View.GONE
                if (formFieldModel.getIsLectureYoutubeUrl()
                        .equals("1")
                ) binding!!.lectureYoutubeLay.visibility =
                    View.VISIBLE else binding!!.lectureYoutubeLay.visibility =
                    View.GONE
                if (formFieldModel.getIsTopic()
                        .equals("1")
                ) binding!!.topicLay.visibility = View.VISIBLE else binding!!.topicLay.visibility =
                    View.GONE
                if (formFieldModel.getIsTeachingMethod()
                        .equals("1")
                ) binding!!.teachingMethodLay.visibility =
                    View.VISIBLE else binding!!.teachingMethodLay.visibility =
                    View.GONE
                if (formFieldModel.getIsGeneralObjectives()
                        .equals("1")
                ) binding!!.generalObjectiveLay.visibility =
                    View.VISIBLE else binding!!.generalObjectiveLay.visibility =
                    View.GONE
                if (formFieldModel.getPeriod().equals("1")) binding!!.periodLay.visibility =
                    View.VISIBLE else binding!!.periodLay.visibility =
                    View.GONE
                if (formFieldModel.getTeachingAids()
                        .equals("1")
                ) binding!!.teachingAidsLay.visibility =
                    View.VISIBLE else binding!!.teachingAidsLay.visibility = View.GONE
                if (formFieldModel.getPortionActuallyTaught()
                        .equals("1")
                ) binding!!.portionActuallyTaughtLay.visibility =
                    View.VISIBLE else binding!!.portionActuallyTaughtLay.visibility =
                    View.GONE
                if (formFieldModel.getHwAssigned()
                        .equals("1")
                ) binding!!.homeworkAssignedLay.visibility =
                    View.VISIBLE else binding!!.homeworkAssignedLay.visibility = View.GONE
                if (formFieldModel.getHwNotAssignedReason()
                        .equals("1")
                ) binding!!.homeworkNotAssignedReasonLay.visibility =
                    View.VISIBLE else binding!!.homeworkNotAssignedReasonLay.visibility =
                    View.GONE
            } else {
                binding!!.lessonLay.visibility = View.GONE
                binding!!.comprehensiveQuestionsLay.visibility = View.GONE
                binding!!.generalObjectiveLay.visibility = View.GONE
                binding!!.lectureYoutubeLay.visibility = View.GONE
                binding!!.teachingMethodLay.visibility = View.GONE
                binding!!.previousKnowledgeLay.visibility = View.GONE
                binding!!.topicLay.visibility = View.GONE
                binding!!.periodLay.visibility = View.GONE
                binding!!.teachingAidsLay.visibility = View.GONE
                binding!!.portionActuallyTaughtLay.visibility = View.GONE
                binding!!.homeworkAssignedLay.visibility = View.GONE
                binding!!.homeworkNotAssignedReasonLay.visibility = View.GONE
            }
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
        binding!!.tvUploadDate.text = sdf.format(myCalendar!!.time)
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

    private fun onActivityDocument() {
        documentActivityLaunch = registerForActivityResult(
            StartActivityForResult()
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
                                    setSelectedSpinnerData()
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

    private fun setSelectedSpinnerData() {
        var classIndex = -1
        if (classListSpinn!!.isNotEmpty()) {
            for (i in classListSpinn!!.indices) {
                if (selectedClassId == classListSpinn!![i]!!.getId()) {
                    classIndex = i
                    break
                }
            }
        }
        if (classIndex != -1) binding!!.classSpinn.setSelection(classIndex)
        binding!!.tvSection.text = selectedSectionName
    }

    private fun getSection() {
        (sectionListSpinn as ArrayList<Section?>).clear()
        (subjectListSpinn as ArrayList<DatumAddLesson?>).clear()
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
                                    if (sectionListSpinn!!.isNotEmpty())
                                        getSubjectList(sectionListSpinn!![0]!!.getId()!!)
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
                selectedSectionName = ""
                selectedSectionId = ""
                binding!!.tvSection.text = getString(R.string.add_custom_lesson_plan_staff_section)
                dialog.dismiss()
                dialogBinding.multiSectionRecycler.adapter = multiSectionAdapter
            }

            dialogBinding.btnSelect.setOnClickListener {
                selectedSectionName = ""
                selectedSectionId = ""
                for (i in sectionListSpinn!!.indices) {
                    if (sectionListSpinn!![i]!!.isSectionSelect()) {
                        val name = sectionListSpinn!![i]!!.getSection()
                        if (selectedSectionName == "") {
                            selectedSectionName = name!!
                            selectedSectionId = sectionListSpinn!![i]!!.getId()!!
                        } else {
                            selectedSectionName = selectedSectionName + "," + name
                            selectedSectionId =
                                selectedSectionId + "," + sectionListSpinn!![i]!!.getId()
                        }
                    }
                }

                binding!!.tvSection.text = selectedSectionName
                dialog.dismiss()
            }

            if (dialog.isShowing) dialog.dismiss()
            dialog.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun getSubjectList(sectionId: String) {
        (subjectListSpinn as ArrayList<DatumAddLesson?>).clear()
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
            builder.addFormDataPart(Constants.ParamsStaff.CLASS_SECTION_ID, sectionId)
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getLessonSubjectList")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getLessonSubjectList(requestBody)
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
                            val dataJsonArr = responseJsonObject.optJSONArray("data")
                            if (dataJsonArr != null) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    SubjectAddLessonResponse::class.java
                                ) as SubjectAddLessonResponse
                                if (modelResponse.getData() != null && modelResponse.getData()!!
                                        .isNotEmpty()
                                ) {
                                    val datumModel = DatumAddLesson()
                                    datumModel.setName("Select subject")
                                    subjectListSpinn = modelResponse.getData()
                                    (subjectListSpinn as ArrayList<DatumAddLesson?>).add(
                                        0,
                                        datumModel
                                    )
                                    subjectSpinnerAdapter =
                                        SubjectSpinnerAdapter(mActivity!!, subjectListSpinn)
                                    binding!!.subjectSpinn.adapter = subjectSpinnerAdapter
                                    var selectedIndex = -1
                                    if (subjectListSpinn!!.isNotEmpty()) {
                                        for (i in subjectListSpinn!!.indices) {
                                            if (selectedSubjectId == subjectListSpinn!![i]!!.getId()) {
                                                selectedIndex = i
                                                break
                                            }
                                        }
                                    }
                                    if (selectedIndex != -1)
                                        binding!!.subjectSpinn.setSelection(selectedIndex)
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

    private fun addCustomLesson() {
        var showOnDate = "0"
        showOnDate = if (binding!!.checkBox.isChecked) "1" else "0"

        val titleStr = binding!!.etTitle.text.toString().trim()
        val lessonStr = binding!!.etLesson.text.toString().trim()
        val topicStr = binding!!.etTopic.text.toString().trim()
        val generalObjectiveStr = binding!!.etGeneralObjective.text.toString().trim()
        val teachingMethodStr = binding!!.etTeachingMethod.text.toString().trim()
        val previousKnowledgeStr = binding!!.etPreviousKnowledge.text.toString().trim()
        val comprensiveQuestionStr = binding!!.etComprensiveQuestion.text.toString().trim()
        val youtubeStr = binding!!.etYoutube.text.toString().trim()
        val periodStr = binding!!.etPeriod.text.toString().trim()
        val teachingAidsStr = binding!!.etTeachingAids.text.toString().trim()
        val portionActuallyTaughtStr = binding!!.etPortionActuallyTaught.text.toString().trim()
        val homeworkAssignedStr = binding!!.etHomeworkAssigned.text.toString().trim()
        val homeworkNotAssignedStr = binding!!.etHomeworkNotAssignedReason.text.toString().trim()
        val descriptionStr = binding!!.etDescription.text.toString().trim()

        if (classId == "")
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.add_custom_lesson_plan_staff_select_class_validation)
            )
        else if (selectedSectionId == "")
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.add_custom_lesson_plan_staff_select_section_validation)
            )
        else if (subjectId == "")
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.add_custom_lesson_plan_staff_select_subject_validation)
            )
        else if (titleStr == "")
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.add_custom_lesson_plan_staff_enter_title_validation)
            )
        else if (descriptionStr == "")
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.add_custom_lesson_plan_staff_enter_description_validation)
            )
        else if (youtubeStr == "" && uploadImageFile == null)
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.add_custom_lesson_plan_staff_file_validation)
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
                        Utils.getStaffId(mActivity!!),
                        dbId!!
                    ).create(ApiInterfaceStaff::class.java)

                val builder = MultipartBody.Builder()
                builder.setType(MultipartBody.FORM)
                builder.addFormDataPart(Constants.ParamsStaff.SECTION_ID, selectedSectionId)
                builder.addFormDataPart(Constants.ParamsStaff.SHOW_ON_DATE, showOnDate)
                builder.addFormDataPart(Constants.ParamsStaff.CONTENT_TITLE, titleStr)
                builder.addFormDataPart(Constants.ParamsStaff.SUBJECT_ID, subjectId)
                builder.addFormDataPart(Constants.ParamsStaff.LESSON, lessonStr)
                builder.addFormDataPart(Constants.ParamsStaff.TOPIC, topicStr)
                builder.addFormDataPart(
                    Constants.ParamsStaff.GENERAL_OBJECTIVES,
                    generalObjectiveStr
                )
                builder.addFormDataPart(Constants.ParamsStaff.TEACHING_METHOD, teachingMethodStr)
                builder.addFormDataPart(
                    Constants.ParamsStaff.PREVIOUS_KNOWLEDGE,
                    previousKnowledgeStr
                )
                builder.addFormDataPart(
                    Constants.ParamsStaff.COMPREHENSIVE_QUESTIONS,
                    comprensiveQuestionStr
                )
                builder.addFormDataPart(Constants.ParamsStaff.LECTURE_YOUTUBE_URL, youtubeStr)
                builder.addFormDataPart(Constants.ParamsStaff.PERIOD, periodStr)
                builder.addFormDataPart(Constants.ParamsStaff.TEACHING_AIDS, teachingAidsStr)
                builder.addFormDataPart(
                    Constants.ParamsStaff.PORTION_ACTUALLY_TAUGHT,
                    portionActuallyTaughtStr
                )
                builder.addFormDataPart(Constants.ParamsStaff.HW_ASSIGNED, homeworkAssignedStr)
                builder.addFormDataPart(
                    Constants.ParamsStaff.HW_NOT_ASSIGNED_REASON,
                    homeworkNotAssignedStr
                )
                builder.addFormDataPart(Constants.ParamsStaff.NOTE, descriptionStr)
                builder.addFormDataPart(
                    Constants.ParamsStaff.UPLOAD_DATE,
                    binding!!.tvUploadDate.text.toString()
                )
                builder.addFormDataPart(
                    Constants.ParamsStaff.STAFF_ID,
                    Utils.getStaffId(mActivity!!)
                )
                builder.addFormDataPart(Constants.ParamsStaff.CLASS_ID, classId)
                if (uploadImageFile != null)
                    builder.addFormDataPart(
                        Constants.ParamsStaff.FILE, uploadImageFile!!.name, RequestBody.create(
                            MediaType.parse("multipart/form-data"), uploadImageFile!!
                        )
                    )
                val requestBody = builder.build()

                Utils.printLog("Url", Constants.BASE_URL_STAFF + "addcustomlessonplan")

                val call: Call<ResponseBody> =
                    apiInterfaceWithHeader.addCustomLessonPlan(requestBody)
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
                                val message = responseJsonObject.optString("message")
                                if (status == 200) {
                                    Utils.showToast(mActivity!!, message)
                                    onBackPressed()
                                } else {
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
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.section_lay) {
            selectedSectionName = ""
            selectedSectionId = ""
            setSectionSpinnerAdapter()
        } else if (id == R.id.upload_date_lay) {
            binding!!.tvUploadDate.text = getString(R.string.custom_lesson_plan_staff_date)
            Utils.hideKeyboard(mActivity!!)
            val fromDateDialog = DatePickerDialog(
                mActivity!!,
                fromDateSetListener,
                myCalendar!!.get(Calendar.YEAR),
                myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            )
            fromDateDialog.show()
        } else if (id == R.id.upload_document_lay)
            selectImageDialog()
        else if (id == R.id.tv_add)
            addCustomLesson()
    }
}