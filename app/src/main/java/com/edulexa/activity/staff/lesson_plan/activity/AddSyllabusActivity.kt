package com.edulexa.activity.staff.lesson_plan.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.*
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.edulexa.R
import com.edulexa.activity.staff.lesson_plan.adapter.LessonSpinnerAdapter
import com.edulexa.activity.staff.lesson_plan.adapter.TopicSpinnerAdapter
import com.edulexa.activity.staff.lesson_plan.model.lesson_list.DatumLesson
import com.edulexa.activity.staff.lesson_plan.model.lesson_list.LessonListResponse
import com.edulexa.activity.staff.lesson_plan.model.syllabus_detail.SyllabusDetailResponse
import com.edulexa.activity.staff.lesson_plan.model.topic_list.DatumTopic
import com.edulexa.activity.staff.lesson_plan.model.topic_list.TopicListResponse
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityAddSyllabusStaffBinding
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
import java.util.*

class AddSyllabusActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityAddSyllabusStaffBinding? = null
    var preference: Preference? = null

    var staffID = ""
    var classID = ""
    var sectionId = ""
    var subjectGroupId = ""
    var subjectGroupSubjectId = ""
    var timeFrom = ""
    var timeTo = ""
    var date = ""
    var roleId = ""
    var syllabusId = ""
    var typeStr = ""
    var subjectGroupClassSectionsId = ""
    var lessonId = ""
    var topicId = ""


    var cameraOnActivityLaunch: ActivityResultLauncher<Intent>? = null
    var galleryOnActivityLaunch: ActivityResultLauncher<Intent>? = null
    var uploadImageFile: File? = null
    var fileSize: Int = 0

    var downloadID: Long? = null

    var lessonListSpinn: List<DatumLesson?>? = ArrayList()
    var lessonSpinnerAdapter: LessonSpinnerAdapter? = null

    var topicListSpinn: List<DatumTopic?>? = ArrayList()
    var topicSpinnerAdapter: TopicSpinnerAdapter? = null

    var modelSyllabusDetail : SyllabusDetailResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSyllabusStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        getBundleData()
        onActivityCamera()
        onActivityGallery()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.uploadDocumentLay.setOnClickListener(this)
        binding!!.downloadAttachmentLay.setOnClickListener(this)
        binding!!.tvAdd.setOnClickListener(this)
    }

    private fun getBundleData(){
        try {
            val bundle = intent.extras
            staffID = bundle!!.getString(Constants.StaffLessonList.STAFF_ID)!!
            subjectGroupSubjectId = bundle.getString(Constants.StaffLessonList.SUBJECT_GROUP_SUBJECT_ID)!!
            timeFrom = bundle.getString(Constants.StaffLessonList.TIME_FROM)!!
            timeTo = bundle.getString(Constants.StaffLessonList.TIME_TO)!!
            date = bundle.getString(Constants.StaffLessonList.DATE)!!
            roleId = bundle.getString(Constants.StaffLessonList.ROLE_ID)!!
            typeStr = bundle.getString(Constants.StaffLessonList.TYPE)!!
            if (typeStr == "add"){
                classID = bundle.getString(Constants.StaffLessonList.CLASS_ID)!!
                sectionId = bundle.getString(Constants.StaffLessonList.SECTION_ID)!!
                subjectGroupId = bundle.getString(Constants.StaffLessonList.SUBJECT_GROUP_ID)!!
            }else syllabusId = bundle.getString(Constants.StaffLessonList.SYLLABUS_ID)!!

            if (roleId == "1" || roleId == "7"){
                binding!!.tvAdd.visibility = View.GONE
                binding!!.lessonSpinn.isEnabled = false
                binding!!.topicSpinn.isEnabled = false
                binding!!.etPresentation.isEnabled = false
                binding!!.etSubTopicName.isEnabled = false
                binding!!.etTeachingMethod.isEnabled = false
                binding!!.etGeneralObjective.isEnabled = false
                binding!!.etPreviousKnowledge.isEnabled = false
                binding!!.etComprensiveQuestion.isEnabled = false
                binding!!.etYoutube.isEnabled = false
                binding!!.uploadDocumentLay.isEnabled = false
            }

            if (typeStr == "add"){
                getSubjectGroupClassSectionId()
                binding!!.tvAdd.text = "Add"
            }else{
                getSyllabusDetail()
                binding!!.tvAdd.text = "Save"
            }

            registerReceiver(
                onDownloadComplete,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )


            binding!!.lessonSpinn.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (syllabusId == "add") {
                        if (lessonListSpinn!![position]!!.getId() != null) {
                            lessonId = lessonListSpinn!![position]!!.getId()!!
                            getTopicList()
                        } else {
                            lessonId = ""
                            (topicListSpinn as ArrayList<DatumTopic?>).clear()
                            if (topicSpinnerAdapter != null) topicSpinnerAdapter!!.notifyDataSetChanged()
                            topicId = ""
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            })

            binding!!.topicSpinn.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (syllabusId == "add") {
                        topicId = if (topicListSpinn!![position]!!.getId() != null) {
                            topicListSpinn!![position]!!.getId()!!
                        } else ""
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
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

    private fun getSubjectGroupClassSectionId(){
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
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID,staffID)
            builder.addFormDataPart(Constants.ParamsStaff.CLASS_ID,classID)
            builder.addFormDataPart(Constants.ParamsStaff.SECTION_ID, sectionId)
            builder.addFormDataPart(Constants.ParamsStaff.SUBJECT_GROUP_ID, subjectGroupId)

            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getSubjectGroupClassSectionsId")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getSubjectGroupClassSectionsId(requestBody)
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
                            val dataJsonObj =
                                responseJsonObject.optJSONObject("data")
                            if (dataJsonObj != null) {
                                subjectGroupClassSectionsId = dataJsonObj.optString("id")
                                getLessonList()
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

    private fun getSyllabusDetail(){
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
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID,staffID)
            builder.addFormDataPart(Constants.ParamsStaff.SYLLABUS_ID,syllabusId)

            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "editSubjectSyllabus")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.editSubjectSyllabus(requestBody)
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
                            val dataJsonObj =
                                responseJsonObject.optJSONObject("data")
                            if (dataJsonObj != null) {
                                modelSyllabusDetail = Utils.getObject(
                                    responseStr,
                                    SyllabusDetailResponse::class.java
                                ) as SyllabusDetailResponse
                                if (modelSyllabusDetail!!.getData() != null) {
                                    subjectGroupClassSectionsId = modelSyllabusDetail!!.getData()!!.getSubjectGroupClassSectionsId()!!
                                    lessonId = modelSyllabusDetail!!.getData()!!.getLessonId()!!
                                    getLessonList()
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

    private fun getLessonList(){
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
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID,staffID)
            builder.addFormDataPart(Constants.ParamsStaff.SUBJECT_GROUP_SUBJECT_ID,subjectGroupSubjectId)
            builder.addFormDataPart(Constants.ParamsStaff.SUBJECT_GROUP_CLASS_SECTIONS_ID, subjectGroupClassSectionsId)

            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getlessonBysubjectid")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getlessonBysubjectId(requestBody)
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
                            val dataJsonObj =
                                responseJsonObject.optJSONObject("data")
                            if (dataJsonObj != null) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    LessonListResponse::class.java
                                ) as LessonListResponse
                                if (modelResponse.getData()!!.isNotEmpty()) {
                                    val datumLesson = DatumLesson()
                                    datumLesson.setName("Select lesson")
                                    lessonListSpinn = modelResponse.getData()
                                    (lessonListSpinn as ArrayList<DatumLesson?>).add(0, datumLesson)
                                    lessonSpinnerAdapter =
                                        LessonSpinnerAdapter(mActivity!!, lessonListSpinn)
                                    binding!!.lessonSpinn.adapter = lessonSpinnerAdapter
                                    if (typeStr != "add")
                                        getTopicList()
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

    private fun getTopicList(){
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
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID,staffID)
            builder.addFormDataPart(Constants.ParamsStaff.LESSON_ID,lessonId)

            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getTopicBylessonid")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getTopicBylessonId(requestBody)
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
                            val dataJsonObj =
                                responseJsonObject.optJSONObject("data")
                            if (dataJsonObj != null) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    TopicListResponse::class.java
                                ) as TopicListResponse
                                if (modelResponse.getData()!!.isNotEmpty()) {
                                    val datumTopic = DatumTopic()
                                    datumTopic.setName("Select topic")
                                    topicListSpinn = modelResponse.getData()
                                    (topicListSpinn as ArrayList<DatumTopic?>).add(0, datumTopic)
                                    topicSpinnerAdapter =
                                        TopicSpinnerAdapter(mActivity!!, topicListSpinn)
                                    binding!!.topicSpinn.adapter = topicSpinnerAdapter
                                    if (typeStr != "add")
                                        setUpEditSyllabusData()
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


    private fun setUpEditSyllabusData(){
        try {
            setLesson()
            setTopic()
            binding!!.etPresentation.setText(modelSyllabusDetail!!.getData()!!.getPresentation())
            binding!!.etPresentation.setSelection(modelSyllabusDetail!!.getData()!!.getPresentation()!!.length)
            binding!!.etSubTopicName.setText(modelSyllabusDetail!!.getData()!!.getSubTopic())
            binding!!.etTeachingMethod.setText(modelSyllabusDetail!!.getData()!!.getTeachingMethod())
            binding!!.etGeneralObjective.setText(modelSyllabusDetail!!.getData()!!.getGeneralObjectives())
            binding!!.etPreviousKnowledge.setText(modelSyllabusDetail!!.getData()!!.getPreviousKnowledge())
            binding!!.etComprensiveQuestion.setText(modelSyllabusDetail!!.getData()!!.getComprehensiveQuestions())
            binding!!.etYoutube.setText(modelSyllabusDetail!!.getData()!!.getLactureYoutubeUrl())
            if (roleId == "1" || roleId == "7")
                staffID = modelSyllabusDetail!!.getData()!!.getCreatedFor()!!
            else binding!!.downloadAttachmentLay.visibility = View.GONE
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun setLesson(){
        lessonId = modelSyllabusDetail!!.getData()!!.getLessonId()!!
        var position = -1
        for (i in lessonListSpinn!!.indices) {
            if (lessonId == lessonListSpinn!![i]!!.getId()) {
                position = i
                break
            }
        }
        if (position != -1) binding!!.lessonSpinn.setSelection(position)
    }

    private fun setTopic(){
        topicId = modelSyllabusDetail!!.getData()!!.getTopicId()!!
        var position = -1
        for (i in topicListSpinn!!.indices) {
            if (topicId == topicListSpinn!![i]!!.getId()) {
                position = i
                break
            }
        }
        if (position != -1) binding!!.topicSpinn.setSelection(position)
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

    private fun downloadAttachment(){
        if (modelSyllabusDetail != null && modelSyllabusDetail!!.getData() != null && !modelSyllabusDetail!!.getData()!!
                .getAttachment().equals("null")
        ) {
            val downloadUrl: String =
                (Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF + "uploads/syllabus_attachment/"
                        + modelSyllabusDetail!!.getData()!!.getAttachment())
            downloadID = Utils.startDownload(mActivity!!, downloadUrl, downloadUrl)
        } else Toast.makeText(mActivity, "no attachment", Toast.LENGTH_SHORT).show()
    }

    private fun addSyllabus(){
        val presentationStr = binding!!.etPresentation.text.toString().trim()
        val subtopicNameStr = binding!!.etSubTopicName.text.toString().trim()
        val teachingMethodStr = binding!!.etTeachingMethod.text.toString().trim()
        val generalObjectiveStr = binding!!.etGeneralObjective.text.toString().trim()
        val previousKnowledgeStr = binding!!.etPreviousKnowledge.text.toString().trim()
        val comprensiveQuestionStr = binding!!.etComprensiveQuestion.text.toString().trim()
        val youtubeUrlStr = binding!!.etYoutube.text.toString().trim()
        if (lessonId == "")
            Utils.showToastPopup(mActivity!!,getString(R.string.add_syllabus_staff_select_lesson_validation))
        else if (topicId == "")
            Utils.showToastPopup(mActivity!!,getString(R.string.add_syllabus_staff_select_topic_validation))
        else if (fileSize > 10)
            Utils.showToastPopup(mActivity!!,getString(R.string.add_syllabus_staff_upload_less_than_10_mb_validation))
        else{
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
                builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID,staffID)
                builder.addFormDataPart(Constants.ParamsStaff.CREATED_FOR,staffID)
                builder.addFormDataPart(Constants.ParamsStaff.TOPIC_ID,topicId)
                builder.addFormDataPart(Constants.ParamsStaff.DATE,date)
                builder.addFormDataPart(Constants.ParamsStaff.TIME_FROM,timeFrom)
                builder.addFormDataPart(Constants.ParamsStaff.TIME_TO,timeTo)
                builder.addFormDataPart(Constants.ParamsStaff.PRESENTATION,presentationStr)
                builder.addFormDataPart(Constants.ParamsStaff.SUB_TOPIC,subtopicNameStr)
                builder.addFormDataPart(Constants.ParamsStaff.TEACHING_METHOD,teachingMethodStr)
                builder.addFormDataPart(Constants.ParamsStaff.GENERAL_OBJECTIVES,generalObjectiveStr)
                builder.addFormDataPart(Constants.ParamsStaff.PREVIOUS_KNOWLEDGE,previousKnowledgeStr)
                builder.addFormDataPart(Constants.ParamsStaff.COMPREHENSIVE_QUESTIONS,comprensiveQuestionStr)
                builder.addFormDataPart(Constants.ParamsStaff.LECTURE_YOUTUBE_URL,youtubeUrlStr)
                if (uploadImageFile != null)
                    builder.addFormDataPart(
                        Constants.ParamsStaff.FILE, uploadImageFile!!.name, RequestBody.create(
                            MediaType.parse("multipart/form-data"), uploadImageFile!!
                        )
                    )
                if (typeStr != "add")
                    builder.addFormDataPart(Constants.ParamsStaff.SYLLABUS_ID,syllabusId)

                val requestBody = builder.build()

                Utils.printLog("Url", Constants.BASE_URL_STAFF + "addSyllabus")

                val call: Call<ResponseBody> = apiInterfaceWithHeader.addSyllabus(requestBody)
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
                                val status =
                                    responseJsonObject.optBoolean("status")
                                if (status) {
                                    Utils.showToast(mActivity!!,"Successfully add")
                                    onBackPressed()
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
    }


    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.upload_document_lay)
            selectImageDialog()
        else if (id == R.id.download_attachment_lay)
            downloadAttachment()
        else if (id == R.id.tv_add)
            addSyllabus()
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