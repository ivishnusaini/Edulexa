package com.edulexa.activity.student.online_exam.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.online_exam.model.exam_list.ExamSchedule
import com.edulexa.databinding.ItemStudentOnlineExamListBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class OnlineExamListAdapter(context: Activity, list : List<ExamSchedule?>?) :
    RecyclerView.Adapter<OnlineExamListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<ExamSchedule?>? = null
    var binding : ItemStudentOnlineExamListBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentOnlineExamListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
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
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentOnlineExamListBinding) : RecyclerView.ViewHolder(binding.root)
}