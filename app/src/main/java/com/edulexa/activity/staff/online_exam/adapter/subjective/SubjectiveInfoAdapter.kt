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
import com.edulexa.activity.staff.online_exam.activity.*
import com.edulexa.activity.staff.online_exam.model.list.ExamOnlineExamStaff
import com.edulexa.activity.staff.online_exam.model.subjective.info.QuestionResult
import com.edulexa.activity.staff.online_exam.model.subjective.list.ResultList
import com.edulexa.activity.student.report_card.activity.ReportCardDetailActivity
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStaffOnlineExamListBinding
import com.edulexa.databinding.ItemStaffOnlineExamSubjectiveBinding
import com.edulexa.databinding.ItemStaffSubmittedSubjectiveBinding
import com.google.gson.Gson

class SubjectiveInfoAdapter(context: Activity, list: List<QuestionResult?>?) :
    RecyclerView.Adapter<SubjectiveInfoAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<QuestionResult?>? = null
    var binding: ItemStaffSubmittedSubjectiveBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffSubmittedSubjectiveBinding.inflate(
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


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffSubmittedSubjectiveBinding) :
        RecyclerView.ViewHolder(binding.root)
}