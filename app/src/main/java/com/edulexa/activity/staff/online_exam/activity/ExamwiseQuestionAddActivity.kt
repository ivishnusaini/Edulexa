package com.edulexa.activity.staff.online_exam.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.edulexa.R
import com.edulexa.activity.staff.online_exam.adapter.add_question.QuestionTypeSpinnerAdapter
import com.edulexa.activity.staff.online_exam.adapter.add_question.SubjectSpinnerAdapter
import com.edulexa.activity.staff.online_exam.model.add_question.QuestionType
import com.edulexa.activity.staff.online_exam.model.add_question.QuestionTypeResponse
import com.edulexa.activity.staff.online_exam.model.add_question.Subject
import com.edulexa.activity.staff.online_exam.model.examwise_questions.QuestionExamWise
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityExamwiseQuestionAddStaffBinding
import com.edulexa.support.FileUtils
import com.edulexa.support.Preference
import com.edulexa.support.Utils
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

class ExamwiseQuestionAddActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityExamwiseQuestionAddStaffBinding? = null
    var preference: Preference? = null

    var examId = ""
    var examType = ""
    var questionId = ""
    var questionExamModel: QuestionExamWise? = null

    var cameraOnActivityLaunch: ActivityResultLauncher<Intent>? = null
    var galleryOnActivityLaunch: ActivityResultLauncher<Intent>? = null
    var uploadImageFile: File? = null

    var questionTypeListSpinn: List<QuestionType?>? = ArrayList()
    var questionTypeSpinnerAdapter: QuestionTypeSpinnerAdapter? = null
    var questionType = ""
    var questionTypeId = ""
    var subjectListSpinn: List<Subject?>? = ArrayList()
    var subjectSpinnerAdapter: SubjectSpinnerAdapter? = null
    var subjectId = ""

    var isOptAChecked = false
    var isOptBChecked = false
    var isOptCChecked = false
    var isOptDChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExamwiseQuestionAddStaffBinding.inflate(layoutInflater)
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
        getQuesTypeList()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.ivGallery.setOnClickListener(this)
        binding!!.ivCamera.setOnClickListener(this)
        binding!!.ivRemove.setOnClickListener(this)
        binding!!.btnSubmit.setOnClickListener(this)
        binding!!.btnAddMore.setOnClickListener(this)

        binding!!.questionTypeSpinn.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (questionTypeListSpinn!![position]!!.getId() != null) {
                        questionTypeId = questionTypeListSpinn!![position]!!.getId()!!
                        questionType = questionTypeListSpinn!![position]!!.getName()!!
                        showAnswerView()
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


        binding!!.mcqCheckboxOptionA.setOnCheckedChangeListener { p0, boolean ->
            isOptAChecked = boolean
        }
        binding!!.mcqCheckboxOptionB.setOnCheckedChangeListener { p0, boolean ->
            isOptBChecked = boolean
        }
        binding!!.mcqCheckboxOptionC.setOnCheckedChangeListener { p0, boolean ->
            isOptCChecked = boolean
        }
        binding!!.mcqCheckboxOptionD.setOnCheckedChangeListener { p0, boolean ->
            isOptDChecked = boolean
        }
    }

    private fun getBundleData() {
        try {
            val bundle = intent.extras
            val type = bundle!!.getString(Constants.StaffOnlineExam.TYPE)!!
            examId = bundle.getString(Constants.StaffOnlineExam.EXAM_ID)!!
            examType = bundle.getString(Constants.StaffOnlineExam.EXAM_TYPE)!!
            if (type == "edit") {
                binding!!.btnAddMore.visibility = View.GONE
                val questionExamModelStr = bundle.getString(Constants.StaffOnlineExam.EXAM_MODEL)!!
                questionExamModel = Utils.getObject(
                    questionExamModelStr,
                    QuestionExamWise::class.java
                ) as QuestionExamWise
                questionId = bundle.getString(Constants.StaffOnlineExam.QUESTION_ID)!!
            }
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
                            binding!!.ivImage.setBackgroundResource(0)
                            binding!!.ivImage.setImageURI(resultUri)
                            binding!!.ivRemove.visibility = View.VISIBLE
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
                        binding!!.ivImage.setBackgroundResource(0)
                        binding!!.ivImage.setImageURI(resultUri)
                        binding!!.ivRemove.visibility = View.VISIBLE
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun setUpData() {
        try {
            if (questionExamModel != null){
                binding!!.etQuestion.setText(questionExamModel!!.getQuestion())
                binding!!.etQuestion.setSelection(questionExamModel!!.getQuestion()!!.length)
                binding!!.etExamMarks.setText(questionExamModel!!.getMark())
                binding!!.etExamMarks.setSelection(questionExamModel!!.getMark()!!.length)
                if (questionExamModel!!.getImgUrl() != "") {
                    binding!!.ivImage.setBackgroundResource(0)
                    Utils.setImageUsingGlide(mActivity!!,questionExamModel!!.getImgUrl(),binding!!.ivImage)
                    binding!!.ivRemove.visibility = View.VISIBLE
                }
                setQuestionType()
                setObjectMultipleChoiceOption()
                setIntegerTypeOption()
                setSubject()
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun setQuestionType() {
        val qTypeId: String = questionExamModel!!.getqType()!!
        var position = -1
        var qTypeStr = ""
        for (i in questionTypeListSpinn!!.indices) {
            if (qTypeId == questionTypeListSpinn!![i]!!.getId()) {
                position = i
                qTypeStr = questionTypeListSpinn!![i]!!.getName()!!
                break
            }
        }
        if (position != -1) {
            binding!!.questionTypeSpinn.setSelection(position)
            questionTypeId = qTypeId
            questionType = qTypeStr
            showAnswerView()
        }
    }

    private fun setObjectMultipleChoiceOption() {
        binding!!.etMcqOptionA.setText(questionExamModel!!.getOptA())
        binding!!.etMcqOptionA.setSelection(questionExamModel!!.getOptA()!!.length)
        binding!!.etMcqOptionA.setText(questionExamModel!!.getOptB())
        binding!!.etMcqOptionA.setSelection(questionExamModel!!.getOptB()!!.length)
        binding!!.etMcqOptionA.setText(questionExamModel!!.getOptC())
        binding!!.etMcqOptionA.setSelection(questionExamModel!!.getOptC()!!.length)
        binding!!.etMcqOptionA.setText(questionExamModel!!.getOptD())
        binding!!.etMcqOptionA.setSelection(questionExamModel!!.getOptD()!!.length)
        when (questionExamModel!!.getCorrect()) {
            "opt_a" -> binding!!.mcqCheckboxOptionA.isChecked = true
            "opt_b" -> binding!!.mcqCheckboxOptionB.isChecked = true
            "opt_c" -> binding!!.mcqCheckboxOptionC.isChecked = true
            "opt_d" -> binding!!.mcqCheckboxOptionD.isChecked = true
        }
    }

    private fun setIntegerTypeOption() {
        binding!!.etIntegerAnswer.setText(questionExamModel!!.getOptA())
    }

    private fun setSubject() {
        val subjectId: String = questionExamModel!!.getSubjectId()!!
        var position = -1
        var subjectName = ""
        for (i in subjectListSpinn!!.indices) {
            if (subjectId == subjectListSpinn!![i]!!.getId()) {
                position = i
                subjectName = subjectListSpinn!![i]!!.getName()!!
                break
            }
        }
        if (position != -1) {
            binding!!.subjectSpinn.setSelection(position)
            this.subjectId = subjectId
        }
    }

    private fun removeImage() {
        try {
            binding!!.ivImage.setImageURI(null)
            Glide.with(mActivity!!).clear(binding!!.ivImage)
            binding!!.ivImage.setBackgroundResource(R.drawable.ic_no_data_found)
            binding!!.ivRemove.setVisibility(View.GONE)
            uploadImageFile = null
            if (questionExamModel != null)
                questionExamModel!!.setImgUrl("")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getQuesTypeList() {
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


            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getQuestionTypeList")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getQuestionTypeList(requestBody)
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
                            val typeListJsonArr = responseJsonObject.optJSONArray("type_list")
                            if (typeListJsonArr != null) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    QuestionTypeResponse::class.java
                                ) as QuestionTypeResponse
                                if (modelResponse.getTypeList()!!
                                        .isNotEmpty()
                                ) {
                                    for (model in modelResponse.getTypeList()!!) {
                                        if (model!!.getName() != "Match Box") {
                                            if (examType == "Practice") {
                                                if (model.getName()!!.lowercase() != "subjective")
                                                    (questionTypeListSpinn as ArrayList<QuestionType?>).add(
                                                        model
                                                    )
                                            } else (questionTypeListSpinn as ArrayList<QuestionType?>).add(
                                                model
                                            )
                                        }
                                    }
                                    val questionType = QuestionType()
                                    questionType.setName("Select type")
                                    (questionTypeListSpinn as ArrayList<QuestionType?>).add(
                                        0,
                                        questionType
                                    )
                                    questionTypeSpinnerAdapter =
                                        QuestionTypeSpinnerAdapter(
                                            mActivity!!,
                                            questionTypeListSpinn
                                        )
                                    binding!!.questionTypeSpinn.adapter = questionTypeSpinnerAdapter
                                }

                                if (modelResponse.getSubjectList()!!
                                        .isNotEmpty()
                                ) {
                                    val subjectModel = Subject()
                                    subjectModel.setName("Select subject")
                                    subjectListSpinn = modelResponse.getSubjectList()
                                    (subjectListSpinn as ArrayList<Subject?>).add(0, subjectModel)
                                    subjectSpinnerAdapter =
                                        SubjectSpinnerAdapter(mActivity!!, subjectListSpinn)
                                    binding!!.subjectSpinn.adapter = subjectSpinnerAdapter
                                }
                                setUpData()
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

    private fun showAnswerView() {
        when (questionType) {
            "MCQ" -> {
                binding!!.cvMcq.visibility = View.VISIBLE
                binding!!.cvInteger.visibility = View.GONE
            }
            "Multiple Option" -> {
                binding!!.cvMcq.visibility = View.VISIBLE
                binding!!.cvInteger.visibility = View.GONE
            }
            "Integer" -> {
                binding!!.cvMcq.visibility = View.GONE
                binding!!.cvInteger.visibility = View.VISIBLE
            }
            else -> {
                binding!!.cvMcq.visibility = View.GONE
                binding!!.cvInteger.visibility = View.GONE
            }
        }
    }

    private fun submitQuestion(type: String) {
        val titleStr = binding!!.etQuestion.text.toString().trim()
        val marksStr = binding!!.etExamMarks.text.toString().trim()
        val integerAnsStr = binding!!.etIntegerAnswer.text.toString().trim()

        if (titleStr.isEmpty())
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.online_exam_staff_title_validation)
            )
        else if (questionTypeId.isEmpty())
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.online_exam_staff_question_type_validation)
            )
        else if (subjectId.isEmpty())
            Utils.showToastPopup(
                mActivity!!,
                getString(R.string.online_exam_staff_subject_validation)
            )
        else {
            var correctAnswers = ""
            var optionAStr = ""
            var optionBStr = ""
            var optionCStr = ""
            var optionDStr = ""
            if (questionType == "MCQ" || questionType == "Multiple Option") {
                if (isOptAChecked)
                    correctAnswers += "opt_a,"
                if (isOptBChecked)
                    correctAnswers += "opt_b,"
                if (isOptCChecked)
                    correctAnswers += "opt_c,"
                if (isOptDChecked)
                    correctAnswers += "opt_d,"

                optionAStr = binding!!.etMcqOptionA.text.toString().trim()
                optionBStr = binding!!.etMcqOptionB.text.toString().trim()
                optionCStr = binding!!.etMcqOptionC.text.toString().trim()
                optionDStr = binding!!.etMcqOptionD.text.toString().trim()

            } else if (questionType == "Integer") {
                correctAnswers = "opt_a"
                optionAStr = integerAnsStr
                optionBStr = ""
                optionCStr = ""
                optionDStr = ""
            } else if (questionType == "Subjective") {
                correctAnswers = ""
                optionAStr = ""
                optionBStr = ""
                optionCStr = ""
                optionDStr = ""
            }
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
                builder.addFormDataPart(
                    Constants.ParamsStaff.STAFF_ID,
                    Utils.getStaffId(mActivity!!)
                )
                builder.addFormDataPart(Constants.ParamsStaff.ONLINE_EXAM_ID, examId)
                builder.addFormDataPart(Constants.ParamsStaff.QUESTION_ID, "")
                builder.addFormDataPart(Constants.ParamsStaff.QUESTION_TYPE_ID, questionTypeId)
                builder.addFormDataPart(Constants.ParamsStaff.CORRECT, correctAnswers)
                builder.addFormDataPart(Constants.ParamsStaff.SUBJECT_ID, subjectId)
                builder.addFormDataPart(Constants.ParamsStaff.QUESTION, titleStr)
                builder.addFormDataPart(Constants.ParamsStaff.QUESTION_MARK, marksStr)
                builder.addFormDataPart(Constants.ParamsStaff.QUESTION_NMARK, "0")
                builder.addFormDataPart(Constants.ParamsStaff.OPT_A, optionAStr)
                builder.addFormDataPart(Constants.ParamsStaff.OPT_B, optionBStr)
                builder.addFormDataPart(Constants.ParamsStaff.OPT_C, optionCStr)
                builder.addFormDataPart(Constants.ParamsStaff.OPT_D, optionDStr)
                builder.addFormDataPart(Constants.ParamsStaff.OPT_E, "")
                builder.addFormDataPart(Constants.ParamsStaff.QUESTION_ID, questionId)
                if (questionExamModel != null)
                    builder.addFormDataPart(
                        Constants.ParamsStaff.IMG_URL,
                        questionExamModel!!.getImgUrl()!!
                    )
                if (uploadImageFile != null)
                    builder.addFormDataPart(
                        Constants.ParamsStaff.ATTACH_FILE,
                        uploadImageFile!!.name,
                        RequestBody.create(
                            MediaType.parse("multipart/form-data"), uploadImageFile!!
                        )
                    )

                val requestBody = builder.build()
                Utils.printLog("Url", Constants.BASE_URL_STAFF + "addQuestion")

                val call: Call<ResponseBody> =
                    apiInterfaceWithHeader.addQuestion(requestBody)
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
                                val message =
                                    responseJsonObject.optString("message")
                                Utils.showToast(mActivity!!, message)
                                if (type == "add_more")
                                    resetAllData()
                                else onBackPressed()
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
                        Utils.showToastPopup(
                            mActivity!!,
                            getString(R.string.api_response_failure)
                        )
                    }

                })
            } else Utils.showToastPopup(
                mActivity!!,
                getString(R.string.internet_connection_error)
            )
        }
    }

    private fun addMoreQuestion() {
        submitQuestion("add_more")
    }

    private fun resetAllData() {
        binding!!.etQuestion.setText("")
        binding!!.etExamMarks.setText("")
        binding!!.etMcqOptionA.setText("")
        binding!!.etMcqOptionB.setText("")
        binding!!.etMcqOptionC.setText("")
        binding!!.etMcqOptionD.setText("")
        binding!!.etIntegerAnswer.setText("")
        binding!!.mcqCheckboxOptionA.isChecked = false
        binding!!.mcqCheckboxOptionB.isChecked = false
        binding!!.mcqCheckboxOptionC.isChecked = false
        binding!!.mcqCheckboxOptionD.isChecked = false
        removeImage()
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.iv_camera) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraOnActivityLaunch!!.launch(intent)
        } else if (id == R.id.iv_gallery) {
            val pickPhoto = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            galleryOnActivityLaunch!!.launch(pickPhoto)
        } else if (id == R.id.iv_remove)
            removeImage()
        else if (id == R.id.btn_submit)
            submitQuestion("submit")
        else if (id == R.id.btn_add_more)
            addMoreQuestion()
    }
}