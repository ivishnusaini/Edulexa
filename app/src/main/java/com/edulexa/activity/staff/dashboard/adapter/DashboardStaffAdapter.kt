package com.edulexa.activity.staff.dashboard.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R

class DashboardStaffAdapter(context: Activity) :
    RecyclerView.Adapter<DashboardStaffAdapter.ViewHolder>() {
    var context: Activity? = null

    init {
        this.context = context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_staff_dashboard, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            when (position) {
                0 -> {
                    viewHolder.tvName!!.text = "Homework"
                }
                1 -> {
                    viewHolder.tvName!!.text = "Attendance"
                }
                2 -> {
                    viewHolder.tvName!!.text = "Fee Details"
                }
                3 -> {
                    viewHolder.tvName!!.text = "Exam"
                }
                4 -> {
                    viewHolder.tvName!!.text = "Report Card"
                }
                5 -> {
                    viewHolder.tvName!!.text = "Calendar"
                }
                6 -> {
                    viewHolder.tvName!!.text = "Notice Board"
                }
                7 -> {
                    viewHolder.tvName!!.text = "Multimedia"
                }
                8 -> {
                    viewHolder.tvName!!.text = "Profile"
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return 12;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView? = null

        init {
            tvName = itemView.findViewById(R.id.tv_dashboard_name)
        }
    }
}