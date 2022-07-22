package com.edulexa.activity.student.online_exam.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.webkit.WebSettings
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.edulexa.R
import com.edulexa.activity.student.online_exam.model.question_ans.*
import com.edulexa.activity.student.online_exam.model.question_ans.subjective_image.SubjectiveQuestionResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityOnlineExamQuestionAnsStudentBinding
import com.edulexa.databinding.DialogSelectImageBinding
import com.edulexa.databinding.DialogSubmitOnlineExamBinding
import com.edulexa.support.FileUtils
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList

class OnlineExamQuestionAnsActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityOnlineExamQuestionAnsStudentBinding? = null
    var listQuestions: List<Question?>? = null
    var examModel: Exam? = null
    var questionNoIndex = 0
    var imageList: List<Uri?>? = null
    var documentFile: List<UploadFileModel?>? = null

    var cameraOnActivityLaunch: ActivityResultLauncher<Intent>? = null
    var galleryOnActivityLaunch: ActivityResultLauncher<Intent>? = null
    var uploadImageFile: File? = null

    var isBackPressed = false

    var onlineexamStudentId = ""
    var examId = ""
    var subjectiveRepeteCall = ""
    var integerRepeteCall = ""
    var ansSubjectiveStr = ""
    val userAnsList: List<UserAnsModel> = ArrayList()
    val multipleOptionList: List<MultipleOptionM> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineExamQuestionAnsStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        getBundleData()
        getQuestionAnsList()
        onActivityCamera()
        onActivityGallery()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.tvOnlineExamQAnsNext.setOnClickListener(this)
        binding!!.tvOnlineExamQAnsPrevious.setOnClickListener(this)
        binding!!.tvOnlineExamQAnsSkip.setOnClickListener(this)
        binding!!.tvOnlineExamQAnsSubmit.setOnClickListener(this)
    }

    private fun getBundleData() {
        try {
            val bundle = intent.extras
            examId = bundle!!.getString(Constants.StudentOnlineExam.EXAMID)!!
            val examName = bundle.getString(Constants.StudentOnlineExam.EXAM_NAME)!!
            binding!!.tvOnlineExamQuestionAnsTitle.text = examName
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getQuestionAnsList() {
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId =
                Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent =
                APIClientStudent.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    accessToken,
                    branchId,
                    userId
                ).create(
                    ApiInterfaceStudent::class.java
                )

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStudent.STUDENT_ID, Utils.getStudentId(mActivity!!))
            jsonObject.put(
                Constants.ParamsStudent.STUDENT_SESSION_ID,
                Utils.getStudentSessionId(mActivity!!)
            )
            jsonObject.put(Constants.ParamsStudent.EXAM_ID, examId)

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/Webservice/startOnlineExam")

            val call: Call<ResponseBody> =
                apiInterfaceWithHeader.getOnlineExamQuestionAnsList(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    try {
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            val jsonObjectResponse = JSONObject(responseStr)
                            val examJsonObject = jsonObjectResponse.optJSONObject("exam")
                            val questionListArr = jsonObjectResponse.optJSONArray("questionList")
                            if (examJsonObject != null && questionListArr != null) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    QuestionAnsResponse::class.java
                                ) as QuestionAnsResponse
                                if (modelResponse.getExam() != null)
                                    examModel = modelResponse.getExam()
                                if (modelResponse.getQuestionList()!!.size > 0)
                                    listQuestions = modelResponse.getQuestionList()
                                if (examModel != null && listQuestions!!.size > 0) {
                                    for (question in listQuestions!!) {
                                        if (question!!.getqTypeName().equals("Subjective")) {
                                            imageList = ArrayList()
                                            documentFile = ArrayList()
                                            (imageList as ArrayList<Uri?>).add(Uri.parse("null"))
                                            (documentFile as ArrayList<UploadFileModel?>).add(
                                                UploadFileModel(null, false)
                                            )
                                            question.setImageList(imageList)
                                            question.setDocumentFile(documentFile)
                                        }
                                    }
                                    onlineexamStudentId = examModel!!.getOnlineexamStudentId()!!
                                    setUpTimer()
                                    loadQuestion()

                                    Handler(Looper.getMainLooper()).postDelayed({
                                        Utils.hideProgressBar()
                                        binding!!.onlineExamQAnsDataLay.visibility = View.VISIBLE
                                    }, 1500)

                                } else {
                                    isBackPressed = true
                                    binding!!.onlineExamQAnsDataLay.visibility = View.GONE
                                }
                            }
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

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadQuestion() {
        try {
            val questionModel = listQuestions!!.get(questionNoIndex)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                binding!!.tvOnlineExamQAnsQuestionType.text = getString(
                    R.string.library_student_string_format,
                    getString(R.string.online_exam_student_question_ans_question_type),
                    Html.fromHtml(questionModel!!.getqTypeName(), 0)
                )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                binding!!.tvOnlineExamQAnsQuestionMark.text = getString(
                    R.string.library_student_string_format,
                    getString(R.string.online_exam_student_question_ans_mark),
                    Html.fromHtml(questionModel!!.getMark(), 0)
                )
            val questionNo = questionNoIndex + 1
            binding!!.tvOnlineExamQAnsQuestionNo.text = getString(
                R.string.concat_string_with_text_format,
                questionNo.toString(),
                " / ",
                listQuestions!!.size.toString()
            )

            binding!!.webViewQuestion.getSettings().setJavaScriptEnabled(true)
            binding!!.webViewQuestion.getSettings()
                .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)
            binding!!.webViewQuestion.getSettings().setDomStorageEnabled(true)
            binding!!.webViewQuestion.getSettings().setBuiltInZoomControls(true)
            binding!!.webViewQuestion.getSettings().setDisplayZoomControls(false)
            binding!!.webViewQuestion.getSettings()
                .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING)
            binding!!.webViewQuestion.loadDataWithBaseURL(
                null,
                getHtmlData(questionModel!!.getQuestion()!!),
                "text/html",
                "utf-8",
                null
            )

            when (questionModel.getqTypeName()) {
                "MCQ" -> {
                    binding!!.radioGroupQAns.visibility = View.VISIBLE
                    binding!!.multipleChoiceQAnsLay.visibility = View.GONE
                    binding!!.subjectiveQAnsLay.visibility = View.GONE
                    binding!!.qAnsIntegerLay.visibility = View.GONE
                    if (!questionModel.getOptA()!!.equals(""))
                        binding!!.radioQAnsOption1.visibility = View.VISIBLE
                    else binding!!.radioQAnsOption1.visibility = View.GONE

                    if (!questionModel.getOptB()!!.equals(""))
                        binding!!.radioQAnsOption2.visibility = View.VISIBLE
                    else binding!!.radioQAnsOption2.visibility = View.GONE

                    if (!questionModel.getOptC()!!.equals(""))
                        binding!!.radioQAnsOption3.visibility = View.VISIBLE
                    else binding!!.radioQAnsOption3.visibility = View.GONE

                    if (!questionModel.getOptD()!!.equals(""))
                        binding!!.radioQAnsOption4.visibility = View.VISIBLE
                    else binding!!.radioQAnsOption4.visibility = View.GONE

                    if (!questionModel.getOptE()!!.equals(""))
                        binding!!.radioQAnsOption5.visibility = View.VISIBLE
                    else binding!!.radioQAnsOption5.visibility = View.GONE

                    binding!!.radioQAnsOption1.text = HtmlCompat.fromHtml(
                        questionModel.getOptA()!!.replace("\\<.*?\\>", ""),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                    binding!!.radioQAnsOption2.text = HtmlCompat.fromHtml(
                        questionModel.getOptB()!!.replace("\\<.*?\\>", ""),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                    binding!!.radioQAnsOption3.text = HtmlCompat.fromHtml(
                        questionModel.getOptC()!!.replace("\\<.*?\\>", ""),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                    binding!!.radioQAnsOption4.text = HtmlCompat.fromHtml(
                        questionModel.getOptD()!!.replace("\\<.*?\\>", ""),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                    binding!!.radioQAnsOption5.text = HtmlCompat.fromHtml(
                        questionModel.getOptE()!!.replace("\\<.*?\\>", ""),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                    binding!!.radioGroupQAns.setOnCheckedChangeListener { radioGroup, i ->
                        if (radioGroup.checkedRadioButtonId != -1) {
                            val id = radioGroup.checkedRadioButtonId
                            val radioButton = radioGroup.findViewById<View>(id)
                            val radioId = radioGroup.indexOfChild(radioButton)
                            when (radioId) {
                                0 -> {
                                    setUserAnsOption(
                                        questionModel.getOnlineexamQuestionsId()!!,
                                        "opt_a",
                                        false
                                    )
                                    questionModel.setAnswer(radioId)
                                    questionModel.setAnsSubmit(true)
                                    setSkipEnable()
                                }
                                1 -> {
                                    setUserAnsOption(
                                        questionModel.getOnlineexamQuestionsId()!!,
                                        "opt_b",
                                        false
                                    )
                                    questionModel.setAnswer(radioId)
                                    questionModel.setAnsSubmit(true)
                                    setSkipEnable()
                                }
                                2 -> {
                                    setUserAnsOption(
                                        questionModel.getOnlineexamQuestionsId()!!,
                                        "opt_c",
                                        false
                                    )
                                    questionModel.setAnswer(radioId)
                                    questionModel.setAnsSubmit(true)
                                    setSkipEnable()
                                }
                                3 -> {
                                    setUserAnsOption(
                                        questionModel.getOnlineexamQuestionsId()!!,
                                        "opt_d",
                                        false
                                    )
                                    questionModel.setAnswer(radioId)
                                    questionModel.setAnsSubmit(true)
                                    setSkipEnable()
                                }
                                4 -> {
                                    setUserAnsOption(
                                        questionModel.getOnlineexamQuestionsId()!!,
                                        "opt_e",
                                        false
                                    )
                                    questionModel.setAnswer(radioId)
                                    questionModel.setAnsSubmit(true)
                                    setSkipEnable()
                                }
                            }
                        }
                    }
                }
                "Multiple Option" -> {
                    binding!!.radioGroupQAns.visibility = View.GONE
                    binding!!.multipleChoiceQAnsLay.visibility = View.VISIBLE
                    binding!!.subjectiveQAnsLay.visibility = View.GONE
                    binding!!.qAnsIntegerLay.visibility = View.GONE
                    if (!questionModel.getOptA()!!.equals(""))
                        binding!!.checkBoxQAns1.visibility = View.VISIBLE
                    else binding!!.checkBoxQAns1.visibility = View.GONE

                    if (!questionModel.getOptB()!!.equals(""))
                        binding!!.checkBoxQAns2.visibility = View.VISIBLE
                    else binding!!.checkBoxQAns2.visibility = View.GONE

                    if (!questionModel.getOptC()!!.equals(""))
                        binding!!.checkBoxQAns3.visibility = View.VISIBLE
                    else binding!!.checkBoxQAns3.visibility = View.GONE

                    if (!questionModel.getOptD()!!.equals(""))
                        binding!!.checkBoxQAns4.visibility = View.VISIBLE
                    else binding!!.checkBoxQAns4.visibility = View.GONE

                    if (!questionModel.getOptE()!!.equals(""))
                        binding!!.checkBoxQAns5.visibility = View.VISIBLE
                    else binding!!.checkBoxQAns5.visibility = View.GONE

                    binding!!.checkBoxQAns1.text = HtmlCompat.fromHtml(
                        questionModel.getOptA()!!.replace("\\<.*?\\>", ""),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                    binding!!.checkBoxQAns2.text = HtmlCompat.fromHtml(
                        questionModel.getOptB()!!.replace("\\<.*?\\>", ""),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                    binding!!.checkBoxQAns3.text = HtmlCompat.fromHtml(
                        questionModel.getOptC()!!.replace("\\<.*?\\>", ""),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                    binding!!.checkBoxQAns4.text = HtmlCompat.fromHtml(
                        questionModel.getOptD()!!.replace("\\<.*?\\>", ""),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                    binding!!.checkBoxQAns5.text = HtmlCompat.fromHtml(
                        questionModel.getOptE()!!.replace("\\<.*?\\>", ""),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )

                    binding!!.checkBoxQAns1.setOnCheckedChangeListener { compoundButton, isChecked ->
                        if (isChecked) {
                            addMultipleOption("opt_a", true, questionNoIndex)
                            questionModel.setAnsSubmit(true)
                        } else {
                            addMultipleOption("opt_a", false, questionNoIndex)
                            if (getCommaSepratedOption(questionNoIndex).equals(""))
                                questionModel.setAnsSubmit(false)
                        }
                        setSkipEnable()
                        setUserAnsOption(
                            questionModel.getOnlineexamQuestionsId()!!,
                            getCommaSepratedOption(questionNoIndex),
                            false
                        )
                    }

                    binding!!.checkBoxQAns2.setOnCheckedChangeListener { compoundButton, isChecked ->
                        if (isChecked) {
                            addMultipleOption("opt_b", true, questionNoIndex)
                            questionModel.setAnsSubmit(true)
                        } else {
                            addMultipleOption("opt_b", false, questionNoIndex)
                            if (getCommaSepratedOption(questionNoIndex).equals(""))
                                questionModel.setAnsSubmit(false)
                        }
                        setSkipEnable()
                        setUserAnsOption(
                            questionModel.getOnlineexamQuestionsId()!!,
                            getCommaSepratedOption(questionNoIndex),
                            false
                        )
                    }

                    binding!!.checkBoxQAns3.setOnCheckedChangeListener { compoundButton, isChecked ->
                        if (isChecked) {
                            addMultipleOption("opt_c", true, questionNoIndex)
                            questionModel.setAnsSubmit(true)
                        } else {
                            addMultipleOption("opt_c", false, questionNoIndex)
                            if (getCommaSepratedOption(questionNoIndex).equals(""))
                                questionModel.setAnsSubmit(false)
                        }
                        setSkipEnable()
                        setUserAnsOption(
                            questionModel.getOnlineexamQuestionsId()!!,
                            getCommaSepratedOption(questionNoIndex),
                            false
                        )
                    }

                    binding!!.checkBoxQAns4.setOnCheckedChangeListener { compoundButton, isChecked ->
                        if (isChecked) {
                            addMultipleOption("opt_d", true, questionNoIndex)
                            questionModel.setAnsSubmit(true)
                        } else {
                            addMultipleOption("opt_d", false, questionNoIndex)
                            if (getCommaSepratedOption(questionNoIndex).equals(""))
                                questionModel.setAnsSubmit(false)
                        }
                        setSkipEnable()
                        setUserAnsOption(
                            questionModel.getOnlineexamQuestionsId()!!,
                            getCommaSepratedOption(questionNoIndex),
                            false
                        )
                    }

                    binding!!.checkBoxQAns5.setOnCheckedChangeListener { compoundButton, isChecked ->
                        if (isChecked) {
                            addMultipleOption("opt_e", true, questionNoIndex)
                            questionModel.setAnsSubmit(true)
                        } else {
                            addMultipleOption("opt_e", false, questionNoIndex)
                            if (getCommaSepratedOption(questionNoIndex).equals(""))
                                questionModel.setAnsSubmit(false)
                        }
                        setSkipEnable()
                        setUserAnsOption(
                            questionModel.getOnlineexamQuestionsId()!!,
                            getCommaSepratedOption(questionNoIndex),
                            false
                        )
                    }

                }
                "Subjective" -> {
                    binding!!.radioGroupQAns.visibility = View.GONE
                    binding!!.multipleChoiceQAnsLay.visibility = View.GONE
                    binding!!.subjectiveQAnsLay.visibility = View.VISIBLE
                    binding!!.qAnsIntegerLay.visibility = View.GONE
                    setSubjectiveImageListData()
                    binding!!.etQAnsSubjective.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {

                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            subjectiveRepeteCall = ""
                        }

                        override fun afterTextChanged(editable: Editable?) {
                            questionModel.setAnsSubmit(
                                !binding!!.etQAnsSubjective.text.toString().trim().isEmpty()
                            )
                            setSkipEnable()
                            ansSubjectiveStr = binding!!.etQAnsSubjective.text.toString().trim()
                            if (!ansSubjectiveStr.equals("")) {
                                if (subjectiveRepeteCall.equals(""))
                                    setUserAnsOption(
                                        questionModel.getOnlineexamQuestionsId()!!,
                                        ansSubjectiveStr,
                                        false
                                    )
                            } else {
                                if (subjectiveRepeteCall.equals(""))
                                    setUserAnsOption(
                                        questionModel.getOnlineexamQuestionsId()!!,
                                        ansSubjectiveStr,
                                        true
                                    )
                            }
                        }

                    })
                }
                "Integer" -> {
                    binding!!.radioGroupQAns.visibility = View.GONE
                    binding!!.multipleChoiceQAnsLay.visibility = View.GONE
                    binding!!.subjectiveQAnsLay.visibility = View.GONE
                    binding!!.qAnsIntegerLay.visibility = View.VISIBLE
                    binding!!.etQAnsInteger.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {

                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            integerRepeteCall = ""
                        }

                        override fun afterTextChanged(editable: Editable?) {
                            questionModel.setAnsSubmit(
                                !binding!!.etQAnsInteger.text.toString().trim().isEmpty()
                            )
                            setSkipEnable()
                            val ansInteger = binding!!.etQAnsSubjective.text.toString().trim()
                            if (!ansInteger.equals("")) {
                                if (integerRepeteCall.equals(""))
                                    setUserAnsOption(
                                        questionModel.getOnlineexamQuestionsId()!!,
                                        ansInteger,
                                        false
                                    )
                            } else {
                                if (integerRepeteCall.equals(""))
                                    setUserAnsOption(
                                        questionModel.getOnlineexamQuestionsId()!!,
                                        ansInteger,
                                        true
                                    )
                            }
                        }

                    })
                }
                "Match Box" -> {
                    binding!!.radioGroupQAns.visibility = View.GONE
                    binding!!.multipleChoiceQAnsLay.visibility = View.GONE
                    binding!!.subjectiveQAnsLay.visibility = View.GONE
                    binding!!.qAnsIntegerLay.visibility = View.GONE
                }
                else -> {
                    binding!!.radioGroupQAns.visibility = View.GONE
                    binding!!.multipleChoiceQAnsLay.visibility = View.GONE
                    binding!!.subjectiveQAnsLay.visibility = View.GONE
                    binding!!.qAnsIntegerLay.visibility = View.GONE
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getHtmlData(bodyHTML: String): String {
        val head = "<head><style>img{max-width: 100%; width:auto; height: auto;}</style></head>"
        return "<html>$head<body>$bodyHTML</body></html>"
    }

    private fun setUpTimer() {
        var localTime: LocalTime? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            localTime = LocalTime.parse(examModel!!.getDuration())
            val millis = localTime.toSecondOfDay() * 1000
            object : CountDownTimer(millis.toLong(), 1000) {
                @SuppressLint("SetTextI18n")
                override fun onTick(millisUntilFinished: Long) {
                    val f: NumberFormat = DecimalFormat("00")
                    val hour = millisUntilFinished / 3600000 % 24
                    val min = millisUntilFinished / 60000 % 60
                    val sec = millisUntilFinished / 1000 % 60
                    binding!!.tvOnlineExamQAnsTimer.setText(
                        f.format(hour) + ":" + f.format(min) + ":" + f.format(
                            sec
                        )
                    )
                }

                override fun onFinish() {
                    binding!!.tvOnlineExamQAnsTimer.setText(getString(R.string.online_exam_student_question_ans_time_format))
                    submitDialog(
                        getString(R.string.online_exam_student_question_ans_timeout_submit),
                        getString(R.string.online_exam_student_question_ans_timeout)
                    )
                }
            }.start()
        }

    }

    private fun addMultipleOption(option: String, status: Boolean, position: Int) {
        try {
            if (multipleOptionList.size > 0) {
                var index = -1
                for (i in 0..multipleOptionList.size - 1) {
                    if (option.equals(multipleOptionList.get(i).getOption())) {
                        if (multipleOptionList.get(i).getPosition() == position) {
                            index = i
                            break
                        }
                    }
                }
                if (index != -1) {
                    if (!status)
                        (multipleOptionList as ArrayList<MultipleOptionM>).removeAt(index)
                } else (multipleOptionList as ArrayList<MultipleOptionM>).add(
                    MultipleOptionM(
                        option,
                        status,
                        position
                    )
                )
            } else (multipleOptionList as ArrayList<MultipleOptionM>).add(
                MultipleOptionM(
                    option,
                    status,
                    position
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getCommaSepratedOption(position: Int): String {
        val list: List<String> = ArrayList()
        var commaSepratedStr = StringBuilder()
        for (multipleOptionModel in multipleOptionList) {
            if (multipleOptionModel.getPosition() == position)
                (list as ArrayList<String>).add(multipleOptionModel.getOption()!!)
        }
        if (list.size > 0) {
            for (listModel in list) {
                if (commaSepratedStr.toString().equals(""))
                    commaSepratedStr = StringBuilder(listModel)
                else commaSepratedStr.append(",").append(listModel)
            }
        }
        return commaSepratedStr.toString().trim()
    }

    @SuppressLint("InflateParams")
    private fun setSubjectiveImageListData() {
        try {
            val questionModel = listQuestions!!.get(questionNoIndex)
            if (questionModel!!.getImageList() != null && questionModel.getImageList()!!.size > 0) {
                binding!!.onlineExamQAnsSubjctiveImageLay.removeAllViews()
                var position = 0
                while (position < questionModel.getImageList()!!.size) {
                    val inflater =
                        mActivity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                    val itemView: View = inflater!!.inflate(
                        R.layout.item_student_online_q_ans_subjective_image,
                        null,
                        true
                    )


                    val imageLay1 = itemView.findViewById<RelativeLayout>(R.id.image_lay_1)
                    val ivOnlineQAnsImage1 =
                        itemView.findViewById<ImageView>(R.id.iv_online_q_ans_image_1)
                    val ivCross1 = itemView.findViewById<ImageView>(R.id.iv_cross_1)

                    val imageLay2 = itemView.findViewById<RelativeLayout>(R.id.image_lay_2)
                    val ivOnlineQAnsImage2 =
                        itemView.findViewById<ImageView>(R.id.iv_online_q_ans_image_2)
                    val ivCross2 = itemView.findViewById<ImageView>(R.id.iv_cross_2)

                    val imageLay3 = itemView.findViewById<RelativeLayout>(R.id.image_lay_3)
                    val ivOnlineQAnsImage3 =
                        itemView.findViewById<ImageView>(R.id.iv_online_q_ans_image_3)
                    val ivCross3 = itemView.findViewById<ImageView>(R.id.iv_cross_3)

                    if (position < questionModel.getImageList()!!.size) {
                        imageLay1.visibility = View.VISIBLE
                        if (position == 0) {
                            ivOnlineQAnsImage1.setImageURI(null)
                            ivOnlineQAnsImage1.setBackgroundResource(R.drawable.ic_add_image)
                            ivCross1.visibility = View.GONE
                        } else {
                            ivOnlineQAnsImage1.setBackgroundResource(0)
                            ivOnlineQAnsImage1.setImageURI(
                                questionModel.getImageList()!!.get(position)
                            )
                            ivCross1.visibility = View.VISIBLE
                        }
                        ivOnlineQAnsImage1.tag = 1000 + position
                        ivCross1.tag = 2000 + position
                    }
                    val secondItemPosition = position + 1
                    if (secondItemPosition < questionModel.getImageList()!!.size) {
                        imageLay2.visibility = View.VISIBLE
                        ivOnlineQAnsImage2.setBackgroundResource(0)
                        ivOnlineQAnsImage2.setImageURI(
                            questionModel.getImageList()!!.get(secondItemPosition)
                        )
                        ivCross2.visibility = View.VISIBLE

                        ivOnlineQAnsImage2.tag = 10000 + secondItemPosition
                        ivCross2.tag = 20000 + secondItemPosition
                    }

                    val thirdItemPosition = position + 2
                    if (thirdItemPosition < questionModel.getImageList()!!.size) {
                        imageLay3.visibility = View.VISIBLE
                        ivOnlineQAnsImage3.setBackgroundResource(0)
                        ivOnlineQAnsImage3.setImageURI(
                            questionModel.getImageList()!!.get(thirdItemPosition)
                        )
                        ivCross3.visibility = View.VISIBLE

                        ivOnlineQAnsImage3.tag = 100000 + thirdItemPosition
                        ivCross3.tag = 200000 + thirdItemPosition
                    }

                    ivOnlineQAnsImage1.setOnClickListener {
                        val tag = ivOnlineQAnsImage1.tag as Int - 1000
                        if (tag == 0)
                            addImage()
                    }

                    ivCross1.setOnClickListener {
                        val tag = ivCross1.tag as Int - 2000
                        removeImage(tag)
                    }
                    ivCross2.setOnClickListener {
                        val tag = ivCross2.tag as Int - 20000
                        removeImage(tag)
                    }

                    ivCross3.setOnClickListener {
                        val tag = ivCross3.tag as Int - 200000
                        removeImage(tag)
                    }


                    binding!!.onlineExamQAnsSubjctiveImageLay.addView(itemView)
                    position += 3
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addImage() {
        val questionModel = listQuestions!!.get(questionNoIndex)
        if (questionModel!!.getImageList()!!.size < 9) {
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
        } else Utils.showToast(
            mActivity!!,
            getString(R.string.online_exam_student_question_ans_upload_max_8_images)
        )
    }

    private fun removeImage(position: Int) {
        try {
            val questionModel = listQuestions!!.get(questionNoIndex)
            documentFile = questionModel!!.getDocumentFile()
            imageList = questionModel.getImageList()
            if (!documentFile!!.get(position)!!.isUpload()) {
                (documentFile as ArrayList<UploadFileModel?>).removeAt(position)
                (imageList as ArrayList<Uri?>).removeAt(position)
                questionModel.setDocumentFile(documentFile)
                questionModel.setImageList(imageList)
                setSubjectiveImageListData()
            } else {
                val index = position - 1
                deleteSubjectiveImage(index)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun deleteSubjectiveImage(position: Int) {
        val questionModel = listQuestions!!.get(questionNoIndex)
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId =
                Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent =
                APIClientStudent.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    accessToken,
                    branchId,
                    userId
                ).create(
                    ApiInterfaceStudent::class.java
                )

            val jsonObject = JSONObject()
            jsonObject.put(
                Constants.ParamsStudent.IMG_ID,
                questionModel!!.getSubmittedImageId()!!.get(position)
            )
            jsonObject.put(
                Constants.ParamsStudent.STUDENT_SESSION_ID,
                Utils.getStudentSessionId(mActivity!!)
            )
            jsonObject.put(Constants.ParamsStudent.EXAM_ID, examId)

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/Webservice/deleteSubjectiveimages")

            val call: Call<ResponseBody> =
                apiInterfaceWithHeader.deleteSubjectiveimages(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            val jsonObjectResponse = JSONObject(responseStr)
                            val status = jsonObjectResponse.optInt("status")
                            val message = jsonObjectResponse.optString("message")
                            if (status == 1) {
                                Utils.showToast(mActivity!!, message)
                                imageList = questionModel.getImageList()
                                documentFile = questionModel.getDocumentFile()
                                (imageList as ArrayList<Uri?>).removeAt(position)
                                (documentFile as ArrayList<UploadFileModel?>).removeAt(position)
                                if (imageList!!.size == 1) {
                                    questionModel.setSubjectiveImageUpload(false)
                                    if (!questionModel.isAnsSubmit())
                                        setUserAnsOption(
                                            questionModel.getOnlineexamQuestionsId()!!,
                                            "",
                                            true
                                        )
                                }
                                questionModel.setImageList(imageList)
                                questionModel.setDocumentFile(documentFile)
                                setSubjectiveImageListData()
                            }
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

    private fun onActivityCamera() {
        cameraOnActivityLaunch = registerForActivityResult(
            StartActivityForResult(), @SuppressLint("NotifyDataSetChanged")
            fun(result: ActivityResult) {
                if (result.resultCode === RESULT_OK) {
                    try {
                        val data = result.data
                        val bitmap = data!!.extras!!["data"] as Bitmap?
                        val resultUri: Uri = Utils.getImageUri(mActivity!!, bitmap!!)
                        val filePath: String? = FileUtils.getPath(mActivity!!, resultUri)
                        if (filePath != null) {
                            val questionModel = listQuestions!!.get(questionNoIndex)
                            uploadImageFile = File(filePath)
                            documentFile = questionModel!!.getDocumentFile()
                            (documentFile as ArrayList<UploadFileModel?>).add(
                                UploadFileModel(
                                    uploadImageFile,
                                    false
                                )
                            )
                            imageList = questionModel.getImageList()
                            (imageList as ArrayList<Uri?>).add(resultUri)
                            if (imageList!!.size > 1) {
                                questionModel.setImageList(imageList)
                                questionModel.setDocumentFile(documentFile)
                                setSkipEnableSubjectiveImages()
                                setSubjectiveImageListData()
                            }
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
            StartActivityForResult()
        ) { result ->
            if (result.resultCode === RESULT_OK) {
                try {
                    val data = result.data
                    val resultUri =
                        Objects.requireNonNull(data)!!.data
                    val filePath = FileUtils.getPath(mActivity!!, resultUri!!)
                    if (filePath != null) {
                        val questionModel = listQuestions!!.get(questionNoIndex)
                        uploadImageFile = File(filePath)
                        documentFile = questionModel!!.getDocumentFile()
                        (documentFile as ArrayList<UploadFileModel?>).add(
                            UploadFileModel(
                                uploadImageFile,
                                false
                            )
                        )
                        imageList = questionModel.getImageList()
                        (imageList as ArrayList<Uri?>).add(resultUri)
                        if (imageList!!.size > 1) {
                            questionModel.setImageList(imageList)
                            questionModel.setDocumentFile(documentFile)
                            setSkipEnableSubjectiveImages()
                            setSubjectiveImageListData()
                        }
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun setSkipEnableSubjectiveImages() {
        val questionModel = listQuestions!!.get(questionNoIndex)
        if (questionModel!!.getImageList()!!.size == 1) {
            binding!!.tvOnlineExamQAnsSkip.setTextColor(
                ContextCompat.getColor(
                    mActivity!!,
                    R.color.primaray_text_color
                )
            )
            binding!!.tvOnlineExamQAnsSkip.isEnabled = true
        } else {
            binding!!.tvOnlineExamQAnsSkip.setTextColor(
                ContextCompat.getColor(
                    mActivity!!,
                    R.color.gray
                )
            )
            binding!!.tvOnlineExamQAnsSkip.isEnabled = false
        }
        if (questionNoIndex == listQuestions!!.size - 1) {
            binding!!.tvOnlineExamQAnsSkip.setTextColor(
                ContextCompat.getColor(
                    mActivity!!,
                    R.color.gray
                )
            )
            binding!!.tvOnlineExamQAnsSkip.isEnabled = false
        }
    }

    private fun setSkipEnable() {
        try {
            val questionModel = listQuestions!!.get(questionNoIndex)
            if (questionModel!!.isAnsSubmit() || questionNoIndex == listQuestions!!.size - 1) {
                binding!!.tvOnlineExamQAnsSkip.isEnabled = false
                binding!!.tvOnlineExamQAnsSkip.setTextColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.gray
                    )
                )
            } else {
                binding!!.tvOnlineExamQAnsSkip.isEnabled = true
                binding!!.tvOnlineExamQAnsSkip.setTextColor(
                    ContextCompat.getColor(
                        mActivity!!,
                        R.color.primaray_text_color
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun uploadSubjectiveImages(type: String) {
        val questionModel = listQuestions!!.get(questionNoIndex)
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId =
                Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent =
                APIClientStudent.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    accessToken,
                    branchId,
                    userId
                ).create(
                    ApiInterfaceStudent::class.java
                )

            val finalUploadImageList: List<File> = ArrayList()
            for (model in questionModel!!.getDocumentFile()!!) {
                if (model!!.getFile() != null && !model.isUpload())
                    (finalUploadImageList as ArrayList<File>).add(model.getFile()!!)
            }


            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(
                Constants.ParamsStudent.ONLINEEXAM_STUDENT_ID,
                onlineexamStudentId
            );
            builder.addFormDataPart(
                Constants.ParamsStudent.ONLINEEXAM_QUESTION_ID,
                questionModel.getOnlineexamQuestionsId()!!
            );
            builder.addFormDataPart(Constants.ParamsStudent.EXAM_ID, examId);
            for (model in finalUploadImageList) {
                builder.addFormDataPart(
                    Constants.ParamsStudent.FILE,
                    model.name,
                    RequestBody.create(MediaType.parse("multipart/form-data"), model)
                )
            }
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/Webservice/submitSubjectiveQuestion")

            val call: Call<ResponseBody> =
                apiInterfaceWithHeader.submitSubjectiveQuestion(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            val jsonObjectResponse = JSONObject(responseStr)
                            val status = jsonObjectResponse.optInt("status")
                            val messaage = jsonObjectResponse.optString("message")
                            if (status == 1) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    SubjectiveQuestionResponse::class.java
                                ) as SubjectiveQuestionResponse
                                Utils.showToast(mActivity!!, messaage)
                                questionModel.setSubmittedImageId(modelResponse.getIds())
                                questionModel.setSubjectiveImageUpload(true)
                                setUserAnsOption(
                                    questionModel.getOnlineexamQuestionsId()!!,
                                    ansSubjectiveStr,
                                    false
                                )
                                documentFile = questionModel.getDocumentFile()
                                for (uploadFileModel in documentFile!!) {
                                    if (uploadFileModel!!.getFile() != null)
                                        uploadFileModel.setUpload(true)
                                }
                                ansSubjectiveStr = ""
                                if (type.equals("next")) {
                                    questionNoIndex++
                                    if (questionNoIndex < listQuestions!!.size)
                                        loadQuestion()
                                    else questionNoIndex = listQuestions!!.size - 1
                                } else if (type.equals("previous")) {
                                    questionNoIndex--
                                    if (questionNoIndex >= 0)
                                        loadQuestion()
                                    else questionNoIndex = 0
                                    if (questionModel.getqTypeName().equals("Subjective"))
                                        setSkipEnableSubjectiveImages()
                                    else setSkipEnable()
                                    if (questionNoIndex == 0)
                                        binding!!.tvOnlineExamQAnsPrevious.visibility =
                                            View.INVISIBLE
                                    if (questionNoIndex > 0 || listQuestions!!.size - 1 > 0)
                                        binding!!.tvOnlineExamQAnsNext.visibility = View.VISIBLE
                                } else submitDialog(
                                    getString(R.string.online_exam_student_question_ans_submit_dialog),
                                    "submit"
                                )
                                setSkipEnableSubjectiveImages()
                            } else Utils.showToast(mActivity!!, messaage)
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

    private fun setUserAnsOption(questionId: String, ans: String, removeIndexStatus: Boolean) {
        subjectiveRepeteCall = "subjective"
        integerRepeteCall = "integer"
        if (userAnsList.size == 0)
            (userAnsList as ArrayList<UserAnsModel>).add(UserAnsModel(questionId, ans))
        else {
            var previousIdFlag = false
            var index = -1
            for (i in 0..userAnsList.size - 1) {
                if (questionId.equals(userAnsList.get(i).getQuestionId())) {
                    index = i
                    previousIdFlag = true
                    break
                }
            }
            if (!previousIdFlag)
                (userAnsList as ArrayList<UserAnsModel>).add(UserAnsModel(questionId, ans))
            if (previousIdFlag) {
                val userAnsModel = UserAnsModel(questionId, ans)
                (userAnsList as ArrayList<UserAnsModel>).set(index, userAnsModel)
            }
            if (removeIndexStatus && previousIdFlag)
                (userAnsList as ArrayList<UserAnsModel>).removeAt(index)
        }
    }

    private fun needToUpload(): Boolean {
        var flag = false
        val questionModel = listQuestions!!.get(questionNoIndex)
        documentFile = questionModel!!.getDocumentFile()
        if (documentFile != null) {
            for (uploadFileModel in documentFile!!) {
                if (uploadFileModel!!.getFile() != null)
                    if (!uploadFileModel.isUpload()) {
                        flag = true
                        break
                    }
            }
        }
        return flag
    }

    private fun submitDialog(message: String, type: String) {
        try {
            var dialogBinding: DialogSubmitOnlineExamBinding? = null
            val dialog = Dialog(mActivity!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBinding = DialogSubmitOnlineExamBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)

            dialogBinding.tvMsg.text = message
            dialogBinding.tvTotalQuestion.text = listQuestions!!.size.toString()
            dialogBinding.tvAttemptedQuestion.text = getAttemptedQuestion().toString()
            dialogBinding.tvSkipQuestion.text =
                (listQuestions!!.size - getAttemptedQuestion()).toString()

            if (type.equals("timeout"))
                dialogBinding.tvCancel.visibility = View.GONE

            dialogBinding.tvOk.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    dialog.dismiss()
                    submitExam()
                }
            })


            dialogBinding.tvCancel.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    dialog.dismiss()
                }
            })

            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun submitExam() {
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId =
                Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent =
                APIClientStudent.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    accessToken,
                    branchId,
                    userId
                ).create(
                    ApiInterfaceStudent::class.java
                )

            var objAnsUsr: JSONObject?
            val jsonArray = JSONArray()
            for (userAnsModel in userAnsList) {
                objAnsUsr = JSONObject()
                objAnsUsr.put(userAnsModel.getQuestionId(), userAnsModel.getQuestionAns())
                jsonArray.put(objAnsUsr)
            }

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStudent.STUDENT_ID, Utils.getStudentId(mActivity!!))
            jsonObject.put(
                Constants.ParamsStudent.STUDENT_SESSION_ID,
                Utils.getStudentSessionId(mActivity!!)
            )
            jsonObject.put(Constants.ParamsStudent.EXAM_ID, examId)
            jsonObject.put(Constants.ParamsStudent.ONLINEEXAM_STUDENT_ID, onlineexamStudentId)
            jsonObject.put(Constants.ParamsStudent.TOTAL_ROWS, jsonArray)

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/webservice/submitOnlineExam")

            val call: Call<ResponseBody> =
                apiInterfaceWithHeader.submitOnlineExam(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            val jsonObjectResponse = JSONObject(responseStr)
                            val status = jsonObjectResponse.optInt("status")
                            val message = jsonObjectResponse.optString("message")
                            if (status == 1) onBackPressed()
                            Utils.showToast(mActivity!!, message)
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

    private fun getAttemptedQuestion(): Int {
        var size = 0
        try {
            for (questionModel in listQuestions!!) {
                if (questionModel!!.getqTypeName().equals("Subjective")) {
                    if (questionModel.isAnsSubmit() || questionModel.isSubjectiveImageUpload())
                        size = size + 1
                } else {
                    if (questionModel.isAnsSubmit())
                        size = size + 1
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.tv_online_exam_q_ans_previous) {
            val questionTypeModel = listQuestions!!.get(questionNoIndex)
            if (needToUpload())
                uploadSubjectiveImages("previous")
            else {
                questionNoIndex--
                if (questionNoIndex >= 0)
                    loadQuestion()
                else questionNoIndex = 0
                if (questionTypeModel!!.getqTypeName().equals("Subjective"))
                    setSkipEnableSubjectiveImages()
                else setSkipEnable()
                if (questionNoIndex == 0)
                    binding!!.tvOnlineExamQAnsPrevious.visibility = View.INVISIBLE
                if (questionNoIndex == 0 || listQuestions!!.size - 1 > 0)
                    binding!!.tvOnlineExamQAnsNext.visibility = View.VISIBLE
            }

        } else if (id == R.id.tv_online_exam_q_ans_next) {
            val questionTypeModel = listQuestions!!.get(questionNoIndex)
            if (questionTypeModel!!.getqTypeName().equals("Subjective")) {
                if (questionTypeModel.isAnsSubmit() || questionTypeModel.getImageList()!!.size > 1) {
                    if (questionTypeModel.isAnsSubmit()) {
                        if (needToUpload())
                            uploadSubjectiveImages("next")
                        else {
                            questionNoIndex++
                            if (questionNoIndex < listQuestions!!.size)
                                loadQuestion()
                            else questionNoIndex = listQuestions!!.size - 1
                            setSkipEnable()
                        }
                    } else if (questionTypeModel.getImageList()!!.size > 1) {
                        if (!questionTypeModel.isSubjectiveImageUpload())
                            uploadSubjectiveImages("next")
                        else {
                            if (needToUpload())
                                uploadSubjectiveImages("next")
                            else {
                                questionNoIndex++
                                if (questionNoIndex < listQuestions!!.size)
                                    loadQuestion()
                                else questionNoIndex = listQuestions!!.size - 1
                            }
                        }
                    }
                } else {
                    Utils.showToast(
                        mActivity!!,
                        getString(R.string.online_exam_student_question_ans_write_or_upload_one_image)
                    )
                    setSkipEnable()
                }
            } else {
                if (questionTypeModel.isAnsSubmit()) {
                    questionNoIndex++
                    if (questionNoIndex < listQuestions!!.size)
                        loadQuestion()
                    else questionNoIndex = listQuestions!!.size - 1
                } else Utils.showToast(
                    mActivity!!,
                    getString(R.string.online_exam_student_question_ans_attemp_question_first)
                )
                setSkipEnable()
            }
            if (questionNoIndex > 0)
                binding!!.tvOnlineExamQAnsPrevious.visibility = View.VISIBLE
            if (questionNoIndex == listQuestions!!.size - 1)
                binding!!.tvOnlineExamQAnsNext.visibility = View.INVISIBLE
        } else if (id == R.id.tv_online_exam_q_ans_skip) {
            questionNoIndex++
            if (questionNoIndex < listQuestions!!.size)
                loadQuestion()
            else questionNoIndex = listQuestions!!.size - 1
            setSkipEnable()
            if (questionNoIndex > 0)
                binding!!.tvOnlineExamQAnsPrevious.visibility = View.VISIBLE
            if (questionNoIndex == listQuestions!!.size - 1)
                binding!!.tvOnlineExamQAnsNext.visibility = View.INVISIBLE
        } else if (id == R.id.tv_online_exam_q_ans_submit) {
            if (needToUpload())
                uploadSubjectiveImages("submit")
            else submitDialog(
                getString(R.string.online_exam_student_question_ans_submit_dialog),
                "submit"
            )
        }
    }

    override fun onBackPressed() {
        if (isBackPressed) {
            super.onBackPressed()
            finish()
        } else Utils.showToast(
            mActivity!!,
            getString(R.string.online_exam_student_question_ans_submit_your_exam_first)
        )
    }
}