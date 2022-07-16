package com.edulexa.activity.student.online_exam.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.view.View
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.edulexa.R
import com.edulexa.activity.student.online_exam.model.question_ans.Exam
import com.edulexa.activity.student.online_exam.model.question_ans.Question
import com.edulexa.activity.student.online_exam.model.question_ans.QuestionAnsResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityOnlineExamQuestionAnsStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalTime

class OnlineExamQuestionAnsActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityOnlineExamQuestionAnsStudentBinding? = null
    var examId = ""
    var listQuestions: List<Question?>? = null
    var examModel: Exam? = null
    var questionNoIndex = 0
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
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
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
                    Utils.hideProgressBar()
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
                                    setUpTimer()
                                    loadQuestion()
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
            binding!!.tvOnlineExamQAnsQuestionNo.text = getString(R.string.concat_string_with_text_format,questionNo.toString()," / ",listQuestions!!.size.toString())

            binding!!.webViewQuestion.getSettings().setJavaScriptEnabled(true)
            binding!!.webViewQuestion.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)
            binding!!.webViewQuestion.getSettings().setDomStorageEnabled(true)
            binding!!.webViewQuestion.getSettings().setBuiltInZoomControls(true)
            binding!!.webViewQuestion.getSettings().setDisplayZoomControls(false)
            binding!!.webViewQuestion.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING)
            binding!!.webViewQuestion.loadDataWithBaseURL(null, getHtmlData(questionModel!!.getQuestion()!!), "text/html", "utf-8", null)

            when(questionModel.getqTypeName()){
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

                    binding!!.radioQAnsOption1.text = HtmlCompat.fromHtml(questionModel.getOptA()!!.replace("\\<.*?\\>", ""), HtmlCompat.FROM_HTML_MODE_LEGACY)
                    binding!!.radioQAnsOption2.text = HtmlCompat.fromHtml(questionModel.getOptB()!!.replace("\\<.*?\\>", ""), HtmlCompat.FROM_HTML_MODE_LEGACY)
                    binding!!.radioQAnsOption3.text = HtmlCompat.fromHtml(questionModel.getOptC()!!.replace("\\<.*?\\>", ""), HtmlCompat.FROM_HTML_MODE_LEGACY)
                    binding!!.radioQAnsOption4.text = HtmlCompat.fromHtml(questionModel.getOptD()!!.replace("\\<.*?\\>", ""), HtmlCompat.FROM_HTML_MODE_LEGACY)
                    binding!!.radioQAnsOption5.text = HtmlCompat.fromHtml(questionModel.getOptE()!!.replace("\\<.*?\\>", ""), HtmlCompat.FROM_HTML_MODE_LEGACY)
                }
                "Multiple Option" -> {
                    binding!!.radioGroupQAns.visibility = View.GONE
                    binding!!.multipleChoiceQAnsLay.visibility = View.VISIBLE
                    binding!!.subjectiveQAnsLay.visibility = View.GONE
                    binding!!.qAnsIntegerLay.visibility = View.GONE
                }
                "Subjective" -> {
                    binding!!.radioGroupQAns.visibility = View.GONE
                    binding!!.multipleChoiceQAnsLay.visibility = View.GONE
                    binding!!.subjectiveQAnsLay.visibility = View.VISIBLE
                    binding!!.qAnsIntegerLay.visibility = View.GONE
                }
                "Integer" -> {
                    binding!!.radioGroupQAns.visibility = View.GONE
                    binding!!.multipleChoiceQAnsLay.visibility = View.GONE
                    binding!!.subjectiveQAnsLay.visibility = View.GONE
                    binding!!.qAnsIntegerLay.visibility = View.VISIBLE
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

    private fun getHtmlData(bodyHTML : String) : String{
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

    private fun submitDialog(message: String, type: String) {

    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}