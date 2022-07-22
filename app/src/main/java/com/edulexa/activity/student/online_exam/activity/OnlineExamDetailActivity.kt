package com.edulexa.activity.student.online_exam.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.edulexa.R
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityOnlineExamDetailStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class OnlineExamDetailActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityOnlineExamDetailStudentBinding? = null
    var onlineExamNative = ""
    var examId = ""
    var examName = ""
    var preference : Preference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineExamDetailStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        setUpData()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.tvOnlineExamDetailStartExam.setOnClickListener(this)
    }

    private fun setUpData(){
        try {
            val bundle = intent.extras
            examName = bundle!!.getString(Constants.StudentOnlineExam.EXAM_NAME)!!
            examId = bundle.getString(Constants.StudentOnlineExam.EXAMID)!!
            val duration = bundle.getString(Constants.StudentOnlineExam.DURATION)
            val totalQuestion = bundle.getString(Constants.StudentOnlineExam.TOTAL_QUESTION)
            val description = bundle.getString(Constants.StudentOnlineExam.DESCRIPTION)
            onlineExamNative = bundle.getString(Constants.StudentOnlineExam.ONLINEEXAMNATIVE)!!
            val webViewUrl = bundle.getString(Constants.StudentOnlineExam.WEBVIEWURL)
            val examFrom = bundle.getString(Constants.StudentOnlineExam.EXAM_FROM)

            binding!!.tvOnlineExamDetailTitle.text = examName
            binding!!.tvOnlineExamDetailName.text = examName
            binding!!.tvOnlineExamDetailDuration.text = duration
            binding!!.tvOnlineExamDetailTotalQuestion.text = totalQuestion
            binding!!.tvOnlineExamDetailDescription.text = HtmlCompat.fromHtml(description!!, 0)
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    val currentDateTime: String = Utils.getCurrentDateTime()!!
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (getExamFromTimeStamp(examFrom!!) > getExamFromTimeStamp(currentDateTime)) {
                            binding!!.tvOnlineExamDetailStartExam.text = getString(R.string.library_student_string_format,getString(R.string.online_exam_detail_student_exam_will_start),examFrom)
                            binding!!.tvOnlineExamDetailStartExam.setEnabled(false)
                        } else {
                            binding!!.tvOnlineExamDetailStartExam.text = getString(R.string.online_exam_detail_student_exam_start)
                            binding!!.tvOnlineExamDetailStartExam.setEnabled(true)
                            timer.cancel()
                        }
                    }, 100)
                }
            }, 0, 2000)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getExamFromTimeStamp(dateTime: String): Long {
        @SuppressLint("SimpleDateFormat") val formatter: DateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date: Date? = null
        try {
            date = formatter.parse(dateTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date!!.time
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.tv_online_exam_detail_start_exam){
            if (onlineExamNative.equals("1")){
                val bundle = Bundle()
                bundle.putString(Constants.StudentOnlineExam.EXAM_NAME,examName)
                bundle.putString(Constants.StudentOnlineExam.EXAMID,examId)
                startActivity(Intent(mActivity, OnlineExamQuestionAnsActivity::class.java).putExtras(bundle))
            }else{
                val email = preference!!.getString(Constants.Preference.STUDENT_EMAIL)
                val password = preference!!.getString(Constants.Preference.STUDENT_PASSWORD)
            }
        }
    }
}