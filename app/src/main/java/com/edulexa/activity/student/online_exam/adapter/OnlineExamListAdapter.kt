package com.edulexa.activity.student.online_exam.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.online_exam.activity.OnlineExamDetailActivity
import com.edulexa.activity.student.online_exam.model.exam_list.ExamSchedule
import com.edulexa.activity.student.report_card.activity.ReportCardDetailActivity
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStudentOnlineExamListBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class OnlineExamListAdapter(context: Activity, list : List<ExamSchedule?>?,onlineExamNative : String,webviewUrl : String) :
    RecyclerView.Adapter<OnlineExamListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<ExamSchedule?>? = null
    var binding : ItemStudentOnlineExamListBinding? = null
    var onlineExamNative = ""
    var webviewUrl = ""
    init {
        this.context = context
        this.list = list
        this.onlineExamNative = onlineExamNative
        this.webviewUrl = webviewUrl
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentOnlineExamListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            binding!!.tvOnlineExamName.text = list!!.get(position)!!.getExam()
            var timeMatch = 0
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val parsed = sdf.parse(list!!.get(position)!!.getExamEndDate()!!)
            val now = Date(System.currentTimeMillis())
            timeMatch = parsed!!.compareTo(now)

            var resultPrepare = ""
            if (list!!.get(position)!!.getResultPrepare() == null
                || list!!.get(position)!!.getResultPrepare()!!.size == 0)
                resultPrepare = "0"
            else resultPrepare = "1"

            if (timeMatch != -1 && resultPrepare.equals("0") && list!!.get(position)!!.getPublishResult().equals("0")) {
                binding!!.tvOnlineExamStatus.text = context!!.getString(R.string.online_exam_student_active)
            } else if (list!!.get(position)!!.getPublishResult().equals("0") && resultPrepare.equals("1")) {
                binding!!.tvOnlineExamStatus.text = context!!.getString(R.string.online_exam_student_submitted)
            } else if (list!!.get(position)!!.getPublishResult().equals("1") && resultPrepare.equals("1")) {
                binding!!.tvOnlineExamStatus.text = context!!.getString(R.string.online_exam_student_result)
            } else if (timeMatch == -1 && resultPrepare.equals("0")) {
                binding!!.tvOnlineExamStatus.text = context!!.getString(R.string.online_exam_student_expire)
            }

            viewHolder.itemView.setOnClickListener(object :View.OnClickListener{
                override fun onClick(p0: View?) {
                    val typeStr = binding!!.tvOnlineExamStatus.text.toString()
                    when(typeStr){
                        "Active" -> {

                        }
                        "Result" -> {

                        }
                        "Submitted" -> {

                        }
                        "Expire" -> {
                            // for testing oterwise no click handle when exam is expire
                            val bundle = Bundle()
                            bundle.putString(Constants.StudentOnlineExam.EXAM_NAME,list!!.get(position)!!.getExam())
                            bundle.putString(Constants.StudentOnlineExam.EXAMID,list!!.get(position)!!.getId())
                            bundle.putString(Constants.StudentOnlineExam.DURATION,list!!.get(position)!!.getDuration())
                            bundle.putString(Constants.StudentOnlineExam.TOTAL_QUESTION,list!!.get(position)!!.getTotalQuestions())
                            bundle.putString(Constants.StudentOnlineExam.DESCRIPTION,list!!.get(position)!!.getDescription())
                            bundle.putString(Constants.StudentOnlineExam.ONLINEEXAMNATIVE,onlineExamNative)
                            bundle.putString(Constants.StudentOnlineExam.WEBVIEWURL,webviewUrl)
                            bundle.putString(Constants.StudentOnlineExam.EXAM_FROM,list!!.get(position)!!.getExamFrom())
                            context!!.startActivity(Intent(context, OnlineExamDetailActivity::class.java).putExtras(bundle))
                        }
                    }
                }

            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentOnlineExamListBinding) : RecyclerView.ViewHolder(binding.root)
}