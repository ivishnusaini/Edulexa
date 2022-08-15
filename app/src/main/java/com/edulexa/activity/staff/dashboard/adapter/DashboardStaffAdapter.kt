package com.edulexa.activity.staff.dashboard.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.custom_lesson_plan.activity.CustomLessonClassListActivity
import com.edulexa.activity.staff.dashboard.model.DashboardModel
import com.edulexa.activity.staff.online_exam.activity.OnlineExamActivity
import com.edulexa.activity.staff.student_profile.activity.StudentProfileClassListActivity
import com.edulexa.activity.student.attendance.adapter.AttendanceStudentMonthAdapter
import com.edulexa.databinding.ItemStaffDashboardBinding
import com.edulexa.databinding.ItemStudentAttedndanceMonthBinding

class DashboardStaffAdapter(context: Activity,list : List<DashboardModel>) :
    RecyclerView.Adapter<DashboardStaffAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DashboardModel>? = null
    var binding : ItemStaffDashboardBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStaffDashboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            binding!!.ivDashboardStaff.setBackgroundResource(list!![position].getModuleImage())
            binding!!.tvDashboardStaff.text = list!![position].getModuleName()

            viewHolder.itemView.setOnClickListener {
                when(list!![position].getModuleName()){
                    context!!.getString(R.string.dashboard_staff_student_profile) -> {
                        context!!.startActivity(Intent(context,StudentProfileClassListActivity::class.java))
                    }
                    context!!.getString(R.string.dashboard_staff_custom_lesson_plan) -> {
                        context!!.startActivity(Intent(context,CustomLessonClassListActivity::class.java))
                    }
                    context!!.getString(R.string.dashboard_staff_online_exam) -> {
                        context!!.startActivity(Intent(context,OnlineExamActivity::class.java))
                    }
                    context!!.getString(R.string.dashboard_staff_homework) -> {

                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffDashboardBinding) : RecyclerView.ViewHolder(binding.root)
}