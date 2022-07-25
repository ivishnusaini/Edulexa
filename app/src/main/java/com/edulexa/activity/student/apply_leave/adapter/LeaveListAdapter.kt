package com.edulexa.activity.student.apply_leave.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.apply_leave.model.ListApplyLeave
import com.edulexa.activity.student.dashboard.adapter.DashboardStudentAdapter
import com.edulexa.activity.student.homework.model.Homework
import com.edulexa.databinding.ItemStudentDashboardBinding
import com.edulexa.databinding.ItemStudentHomeworkDateBinding
import com.edulexa.databinding.ItemStudentLeaveListBinding
import com.edulexa.support.Utils

class LeaveListAdapter(context: Activity, list: List<ListApplyLeave?>?) :
    RecyclerView.Adapter<LeaveListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<ListApplyLeave?>? = null
    var binding: ItemStudentLeaveListBinding? = null

    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStudentLeaveListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            if (list!!.get(position)!!.getStatus().equals("0")) {
                binding!!.tvApplyLeaveStatus.text =
                    context!!.getString(R.string.apply_leave_student_pending)
                binding!!.tvApplyLeaveStatus.setTextColor(
                    ContextCompat.getColor(
                        context!!,
                        R.color.leave_pending_color
                    )
                )
            } else if (list!!.get(position)!!.getStatus().equals("1")) {
                binding!!.tvApplyLeaveStatus.text =
                    context!!.getString(R.string.apply_leave_student_approved)
                binding!!.tvApplyLeaveStatus.setTextColor(
                    ContextCompat.getColor(
                        context!!,
                        R.color.green
                    )
                )
            } else {
                binding!!.tvApplyLeaveStatus.text =
                    context!!.getString(R.string.apply_leave_student_cancel)
                binding!!.tvApplyLeaveStatus.setTextColor(
                    ContextCompat.getColor(
                        context!!,
                        R.color.red
                    )
                )
            }
            binding!!.tvApplyLeaveFromDate.text = list!!.get(position)!!.getFromDate()
            binding!!.tvApplyLeaveToDate.text = list!!.get(position)!!.getToDate()
            binding!!.tvApplyLeaveAppliedDate.text = list!!.get(position)!!.getApplyDate()
            binding!!.tvApplyLeaveReason.text = list!!.get(position)!!.getReason()
            Utils.setImageUsingGlide(context,list!!.get(position)!!.getDocs(),binding!!.ivLeaveDocument)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentLeaveListBinding) : RecyclerView.ViewHolder(binding.root)
}