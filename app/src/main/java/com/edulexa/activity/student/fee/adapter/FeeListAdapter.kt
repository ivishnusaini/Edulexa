package com.edulexa.activity.student.fee.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.attendance.activity.AttendanceStudentActivity
import com.edulexa.activity.student.dashboard.activity.DashboardStudentActivity
import com.edulexa.activity.student.fee.activity.FeeStudentActivity
import com.edulexa.activity.student.homework.activity.HomeworkStudentActivity

class FeeListAdapter(context: Activity) :
    RecyclerView.Adapter<FeeListAdapter.ViewHolder>() {
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
                .inflate(R.layout.item_student_fee_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return 5;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView? = null

        init {

        }
    }
}