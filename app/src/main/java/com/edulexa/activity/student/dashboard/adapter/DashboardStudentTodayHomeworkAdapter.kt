package com.edulexa.activity.student.dashboard.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.attendance.activity.AttendanceStudentActivity
import com.edulexa.activity.student.examination.activity.ExamStudentActivity
import com.edulexa.activity.student.fee.activity.FeeStudentActivity
import com.edulexa.activity.student.gallery.activity.GalleryStudentActivity
import com.edulexa.activity.student.homework.activity.HomeworkStudentActivity
import com.edulexa.activity.student.noticeboard.activity.NoticeBoardStudentActivity
import com.edulexa.activity.student.profile.activity.ProfileStudentActivity
import com.edulexa.activity.student.report_card.activity.ReportCardStudentActivity

class DashboardStudentTodayHomeworkAdapter(context: Activity) :
    RecyclerView.Adapter<DashboardStudentTodayHomeworkAdapter.ViewHolder>() {
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
                .inflate(R.layout.item_student_dashboard_today_homework, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            when (position) {
                0 -> {
                    viewHolder.tvTodayHomeworkMessage!!.text = "Learn Chapter 5 with one Essay"
                    viewHolder.tvTodayHomeworkSubject!!.text = "English"
                }
                1 -> {
                    viewHolder.tvTodayHomeworkMessage!!.text = "Exercise Trigonometry 1st Topic"
                    viewHolder.tvTodayHomeworkSubject!!.text = "Maths"
                }
                2 -> {
                    viewHolder.tvTodayHomeworkMessage!!.text = "Hindi writing 3 pages"
                    viewHolder.tvTodayHomeworkSubject!!.text = "Hindi"
                }
                3 -> {
                    viewHolder.tvTodayHomeworkMessage!!.text = "Test for History first lesson"
                    viewHolder.tvTodayHomeworkSubject!!.text = "Social Science"
                }
                4 -> {
                    viewHolder.tvTodayHomeworkMessage!!.text = "Exercise Trigonometry 1st Topic"
                    viewHolder.tvTodayHomeworkSubject!!.text = "Maths"
                }
                5 -> {
                    viewHolder.tvTodayHomeworkMessage!!.text = "Hindi writing 3 pages"
                    viewHolder.tvTodayHomeworkSubject!!.text = "Hindi"
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return 6;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTodayHomeworkMessage: TextView? = null
        var tvTodayHomeworkSubject: TextView? = null
        init {
            tvTodayHomeworkMessage = itemView.findViewById(R.id.tv_today_homework_message)
            tvTodayHomeworkSubject = itemView.findViewById(R.id.tv_today_homework_subject)
        }
    }
}