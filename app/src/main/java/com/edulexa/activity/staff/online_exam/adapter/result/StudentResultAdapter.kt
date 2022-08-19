package com.edulexa.activity.staff.online_exam.adapter.result

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.online_exam.activity.*
import com.edulexa.activity.staff.online_exam.model.list.ExamOnlineExamStaff
import com.edulexa.activity.staff.online_exam.model.result.FinalResult
import com.edulexa.activity.staff.online_exam.model.subjective.list.ResultList
import com.edulexa.activity.student.report_card.activity.ReportCardDetailActivity
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStaffOnlineExamListBinding
import com.edulexa.databinding.ItemStaffOnlineExamResultBinding
import com.edulexa.databinding.ItemStaffOnlineExamSubjectiveBinding
import com.google.gson.Gson

class StudentResultAdapter(context: Activity, list: List<FinalResult?>?) :
    RecyclerView.Adapter<StudentResultAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<FinalResult?>? = null
    var binding: ItemStaffOnlineExamResultBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffOnlineExamResultBinding.inflate(
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
            binding!!.tvName.text = context!!.getString(R.string.library_student_string_format,"Name : ",list!![position]!!.getStudentName())
            binding!!.tvQuestion.text = context!!.getString(R.string.library_student_string_format,"Total Question : ",list!![position]!!.getTotalQuestions())
            binding!!.tvMarks.text = context!!.getString(R.string.library_student_string_format,"Marks : ",list!![position]!!.getObtainedMarks().toString())

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffOnlineExamResultBinding) :
        RecyclerView.ViewHolder(binding.root)
}