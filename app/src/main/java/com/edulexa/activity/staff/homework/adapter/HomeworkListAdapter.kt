package com.edulexa.activity.staff.homework.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.homework.activity.EvaluationActivity
import com.edulexa.activity.staff.homework.model.homeworklist.Homework
import com.edulexa.activity.staff.online_exam.activity.*
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStaffHomeworkListBinding

class HomeworkListAdapter(context: Activity, list: List<Homework?>?) :
    RecyclerView.Adapter<HomeworkListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<Homework?>? = null
    var binding: ItemStaffHomeworkListBinding? = null

    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffHomeworkListBinding.inflate(
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
            binding!!.tvClassName.text = context!!.getString(R.string.library_student_string_format,"Class : ",list!![position]!!.getClass_())
            binding!!.tvSectionName.text = context!!.getString(R.string.library_student_string_format,"Section : ",list!![position]!!.getSection())
            binding!!.tvTeacherName.text = context!!.getString(R.string.library_student_string_format,"Teacher Name : ",list!![position]!!.getCreatedBy())
            binding!!.tvSubjectName.text = list!![position]!!.getSubjectName()
            binding!!.tvHomeworkDate.text = list!![position]!!.getHomeworkDate()
            binding!!.tvSubmitDate.text = list!![position]!!.getSubmitDate()
            binding!!.tvEvaluationDate.text = list!![position]!!.getEvaluationDate()

            binding!!.ivMenu.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(Constants.StaffHomework.HOMEWORK_ID, list!![position]!!.getId())
                context!!.startActivity(Intent(context, EvaluationActivity::class.java).putExtras(bundle))
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffHomeworkListBinding) :
        RecyclerView.ViewHolder(binding.root)
}