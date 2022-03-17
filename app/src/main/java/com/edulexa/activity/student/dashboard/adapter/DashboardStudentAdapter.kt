package com.edulexa.activity.student.dashboard.adapter

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
import com.edulexa.activity.student.examination.activity.ExamStudentActivity
import com.edulexa.activity.student.fee.activity.FeeStudentActivity
import com.edulexa.activity.student.homework.activity.HomeworkStudentActivity

class DashboardStudentAdapter(context: Activity) :
    RecyclerView.Adapter<DashboardStudentAdapter.ViewHolder>() {
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
                .inflate(R.layout.item_student_dashboard, parent, false)
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
                    viewHolder.tvName!!.text = "Examination"
                }
                4 -> {
                    viewHolder.tvName!!.text = "Report Cards"
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
                    viewHolder.tvName!!.text = "Acadmic Year"
                }
                9 -> {
                    viewHolder.tvName!!.text = "Profile"
                }
            }

            viewHolder.itemView.setOnClickListener(object :View.OnClickListener{
                override fun onClick(p0: View?) {
                    when(viewHolder.tvName!!.text){
                        "Homework" -> {
                            context!!.startActivity(Intent(context!!, HomeworkStudentActivity::class.java))
                        }
                        "Attendance" -> {
                            context!!.startActivity(Intent(context!!, AttendanceStudentActivity::class.java))
                        }
                        "Fee Details" -> {
                            context!!.startActivity(Intent(context!!, FeeStudentActivity::class.java))
                        }
                        "Examination" -> {
                            context!!.startActivity(Intent(context!!, ExamStudentActivity::class.java))
                        }
                    }
                }

            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return 10;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView? = null

        init {
            tvName = itemView.findViewById(R.id.tv_dashboard_name)
        }
    }
}