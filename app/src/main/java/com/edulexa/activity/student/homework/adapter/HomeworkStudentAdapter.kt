package com.edulexa.activity.student.homework.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.attendance.activity.AttendanceStudentActivity
import com.edulexa.activity.student.dashboard.adapter.DashboardStudentNoticeBoardAdapter
import com.edulexa.activity.student.examination.activity.ExamStudentActivity
import com.edulexa.activity.student.fee.activity.FeeStudentActivity
import com.edulexa.activity.student.gallery.activity.GalleryStudentActivity
import com.edulexa.activity.student.homework.activity.HomeworkStudentActivity
import com.edulexa.activity.student.noticeboard.activity.NoticeBoardStudentActivity
import com.edulexa.activity.student.profile.activity.ProfileStudentActivity
import com.edulexa.activity.student.report_card.activity.ReportCardStudentActivity

class HomeworkStudentAdapter(context: Activity) :
    RecyclerView.Adapter<HomeworkStudentAdapter.ViewHolder>() {
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
                .inflate(R.layout.item_student_homework_date, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            when (position) {
                0 -> {
                    viewHolder.tvDate!!.text = "Today"
                }
                1 -> {
                    viewHolder.tvDate!!.text = "Yesterday"
                }
                2 -> {
                    viewHolder.tvDate!!.text = "16 March 2020"
                }
                3 -> {
                    viewHolder.tvDate!!.text = "15 March 2020"
                }
                4 -> {
                    viewHolder.tvDate!!.text = "10 March 2020"
                }
            }

            viewHolder.recyclerView!!.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            viewHolder.recyclerView!!.adapter =
                HomeworkInnerListStudentAdapter(context!!)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return 5;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDate: TextView? = null
        var recyclerView: RecyclerView? = null

        init {
            tvDate = itemView.findViewById(R.id.tv_homework_student_date)
            recyclerView = itemView.findViewById(R.id.student_homework_inner_recycler)
        }
    }
}