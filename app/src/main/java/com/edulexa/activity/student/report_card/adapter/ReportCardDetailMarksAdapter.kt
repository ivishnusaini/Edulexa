package com.edulexa.activity.student.report_card.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.online_exam.adapter.OnlineExamListAdapter
import com.edulexa.activity.student.report_card.activity.ReportCardDetailActivity
import com.edulexa.activity.student.report_card.model.DatumReportCardList
import com.edulexa.activity.student.report_card.model.detail.ExamResult
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStudentOnlineExamListBinding
import com.edulexa.databinding.ItemStudentReportCardBinding
import com.edulexa.databinding.ItemStudentReportCardDetailMarksBinding

class ReportCardDetailMarksAdapter(context: Activity, list : List<ExamResult?>?) : RecyclerView.Adapter<ReportCardDetailMarksAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<ExamResult?>?= null
    var binding : ItemStudentReportCardDetailMarksBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentReportCardDetailMarksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            binding!!.tvReportCardDetailSubject.text = list!!.get(position)!!.getExamName()
            binding!!.tvReportCardDetailPassingMarks.text = list!!.get(position)!!.getPassingMarks()
            binding!!.tvReportCardDetailObtainedMarks.text = context!!.getString(R.string.concat_string_with_text_format,list!!.get(position)!!.getGetMarks(),"/",list!!.get(position)!!.getFullMarks())
            binding!!.tvReportCardDetailResultStatus.text = list!!.get(position)!!.getStatus()

            if (list!!.get(position)!!.getStatus().equals("Pass"))
                binding!!.tvReportCardDetailResultStatus.setTextColor(ContextCompat.getColor(context!!,R.color.green))
            else binding!!.tvReportCardDetailResultStatus.setTextColor(ContextCompat.getColor(context!!,R.color.red))

            if (position == list!!.size - 1)
                binding!!.viewReportCardDetail.visibility = View.GONE
            else binding!!.viewReportCardDetail.visibility = View.VISIBLE

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentReportCardDetailMarksBinding) : RecyclerView.ViewHolder(binding.root)
}