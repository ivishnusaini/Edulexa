package com.edulexa.activity.staff.online_exam.adapter.subjective

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.online_exam.activity.EditExamActivity
import com.edulexa.activity.staff.online_exam.activity.ExamWiseQuestionsActivity
import com.edulexa.activity.staff.online_exam.activity.OnlineExamActivity
import com.edulexa.activity.staff.online_exam.activity.SubjectiveActivity
import com.edulexa.activity.staff.online_exam.model.list.ExamOnlineExamStaff
import com.edulexa.activity.staff.online_exam.model.subjective.list.ResultList
import com.edulexa.activity.student.report_card.activity.ReportCardDetailActivity
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStaffOnlineExamListBinding
import com.edulexa.databinding.ItemStaffOnlineExamSubjectiveBinding
import com.google.gson.Gson

class SubjectiveAdapter(context: Activity, list: List<ResultList?>?,examId : String) :
    RecyclerView.Adapter<SubjectiveAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<ResultList?>? = null
    var binding: ItemStaffOnlineExamSubjectiveBinding? = null
    var examId : String = ""
    init {
        this.context = context
        this.list = list
        this.examId = examId
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffOnlineExamSubjectiveBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        try {
            binding!!.tvTitle.text = list!![position]!!.getFirstname()
            binding!!.tvRollNo.text = context!!.getString(R.string.library_student_string_format,"Roll No : ",list!![position]!!.getRollNo())

            binding!!.ivInfo.setOnClickListener {

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffOnlineExamSubjectiveBinding) :
        RecyclerView.ViewHolder(binding.root)
}