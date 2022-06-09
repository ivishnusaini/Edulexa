package com.edulexa.activity.student.homework.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.homework.model.HomeworkData
import com.edulexa.databinding.ItemStudentDashboardHomeworkInnerListBinding
import com.edulexa.databinding.ItemStudentHomeworkDateBinding
import com.edulexa.support.Utils

class HomeworkInnerListStudentAdapter(context: Activity,list : List<HomeworkData?>?) :
    RecyclerView.Adapter<HomeworkInnerListStudentAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<HomeworkData?>? = null
    var binding : ItemStudentDashboardHomeworkInnerListBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentDashboardHomeworkInnerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            Utils.setHtmlText(list!!.get(position)!!.getDescription()!!,binding!!.tvTodayHomeworkMessage)
            binding!!.tvTodayHomeworkSubject.text = list!!.get(position)!!.getName()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentDashboardHomeworkInnerListBinding) : RecyclerView.ViewHolder(binding.root)
}