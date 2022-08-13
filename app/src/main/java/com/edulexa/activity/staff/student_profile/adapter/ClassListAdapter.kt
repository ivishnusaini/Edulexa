package com.edulexa.activity.staff.student_profile.adapter

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
import com.edulexa.activity.staff.student_profile.activity.StudentProfileClassListActivity
import com.edulexa.activity.staff.student_profile.model.class_list.ClassData
import com.edulexa.activity.staff.student_profile.model.class_list.CustomModel
import com.edulexa.activity.student.online_exam.adapter.OnlineExamListAdapter
import com.edulexa.activity.student.report_card.activity.ReportCardDetailActivity
import com.edulexa.activity.student.report_card.model.DatumReportCardList
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStaffClassListBinding
import com.edulexa.databinding.ItemStudentOnlineExamListBinding
import com.edulexa.databinding.ItemStudentReportCardBinding

class ClassListAdapter(context: Activity, list: List<ClassData?>?) :
    RecyclerView.Adapter<ClassListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<ClassData?>? = null
    var binding: ItemStaffClassListBinding? = null
    val customList : List<CustomModel> = ArrayList()
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffClassListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        try {
            val customModel = CustomModel()
            customModel.setTextView(binding!!.tvClass)
            customModel.setRelativeLayout(binding!!.classLay)
            (customList as ArrayList<CustomModel>).add(customModel)
            binding!!.tvClass.text = list!![position]!!.getClass_()
            viewHolder.itemView.setOnClickListener {
                resetAll(position)
                (context as StudentProfileClassListActivity).getSection(list!![position]!!.getId()!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun resetAll(position : Int){
        for (model in customList){
            model.getTextView()!!.setTextColor(ContextCompat.getColor(context!!,R.color.primaray_text_color))
            model.getRelativeLayout()!!.setBackgroundResource(R.drawable.bg_border_5)
            model.getRelativeLayout()!!.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.card_view_bg)
        }
        customList[position].getTextView()!!.setTextColor(ContextCompat.getColor(context!!,R.color.white))
        customList[position].getRelativeLayout()!!.setBackgroundResource(R.drawable.bg_button_5)
        customList[position].getRelativeLayout()!!.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.colorPrimary)
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }


    class ViewHolder(binding: ItemStaffClassListBinding) : RecyclerView.ViewHolder(binding.root)
}