package com.edulexa.activity.staff.online_exam.adapter.subjective

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.online_exam.activity.*
import com.edulexa.activity.staff.online_exam.model.subjective.info.QuestionResult
import com.edulexa.databinding.ItemStaffSubmittedSubjectiveBinding

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
            binding!!.tvQuestion.loadData(
                list!![position]!!.getQuestion()!!,
                "text/html; charset=utf-8",
                "UTF-8"
            )
            binding!!.tvAns.text = context!!.getString(R.string.library_student_string_format,"Ans : ",list!![position]!!.getSelectOption())
            binding!!.etMark.hint =
                context!!.getString(R.string.library_student_string_format,"Max : ",list!![position]!!.getMark())

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