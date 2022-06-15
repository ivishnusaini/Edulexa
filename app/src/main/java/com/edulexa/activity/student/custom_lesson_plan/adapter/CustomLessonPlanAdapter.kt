package com.edulexa.activity.student.custom_lesson_plan.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.databinding.ItemStudentCustomLessonPlanBinding

class CustomLessonPlanAdapter(context: Activity/*, list : List<DashboardModuleModel?>?*/) :
    RecyclerView.Adapter<CustomLessonPlanAdapter.ViewHolder>() {
    var context: Activity? = null
//    var list : List<DashboardModuleModel?>? = null
    var binding : ItemStudentCustomLessonPlanBinding? = null
    init {
        this.context = context
//        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentCustomLessonPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return 5;
    }

    class ViewHolder(binding: ItemStudentCustomLessonPlanBinding) : RecyclerView.ViewHolder(binding.root)

}