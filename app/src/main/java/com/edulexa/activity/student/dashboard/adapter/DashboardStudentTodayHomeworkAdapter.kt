package com.edulexa.activity.student.dashboard.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.dashboard.model.HomeworkStudentDashboard
import com.edulexa.databinding.ItemStudentDashboardNoticeBoardBinding
import com.edulexa.databinding.ItemStudentDashboardTodayHomeworkBinding

class DashboardStudentTodayHomeworkAdapter(context: Activity,list : List<HomeworkStudentDashboard?>?) :
    RecyclerView.Adapter<DashboardStudentTodayHomeworkAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<HomeworkStudentDashboard?>? = null
    var binding : ItemStudentDashboardTodayHomeworkBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentDashboardTodayHomeworkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentDashboardTodayHomeworkBinding) : RecyclerView.ViewHolder(binding.root)
}