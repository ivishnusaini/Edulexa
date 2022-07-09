package com.edulexa.activity.student.class_timetable.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.class_timetable.model.ClassTimetableModel
import com.edulexa.activity.student.class_timetable.model.DayWiseListModel
import com.edulexa.databinding.ItemStudentClassTimeTableDayWiseDataBinding
import com.edulexa.support.Utils

class ClassTimeTableDayWiseAdapter(context: Activity, list: List<DayWiseListModel>?) : RecyclerView.Adapter<ClassTimeTableDayWiseAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<DayWiseListModel>? = null
    var binding : ItemStudentClassTimeTableDayWiseDataBinding? = null
    init {
        this.context = context
        this.list = list
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentClassTimeTableDayWiseDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            binding!!.tvClassTimeTableSubjectName.text = list!!.get(position).getSubject()
            binding!!.tvClassTimeTableTeacherName.text = list!!.get(position).getTeacherName()
            binding!!.tvClassTimeTableStartEndTime.text = context!!.getString(
                R.string.dashboard_student_present_format,
                list!!.get(position).getStartTime(),
                " - ",
                list!!.get(position).getEndTime(),
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentClassTimeTableDayWiseDataBinding) : RecyclerView.ViewHolder(binding.root)
}