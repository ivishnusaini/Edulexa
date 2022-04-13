package com.edulexa.activity.student.dashboard.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.calendar.activity.CalendarStudentActivity
import com.edulexa.activity.student.examination.activity.ExamStudentActivity
import com.edulexa.activity.student.fee.activity.FeeStudentActivity
import com.edulexa.activity.student.gallery.activity.GalleryStudentActivity
import com.edulexa.activity.student.homework.activity.HomeworkStudentActivity
import com.edulexa.activity.student.noticeboard.activity.NoticeBoardStudentActivity
import com.edulexa.activity.student.profile.activity.ProfileStudentActivity
import com.edulexa.activity.student.report_card.activity.ReportCardStudentActivity

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
                        "Calendar" -> {
                            context!!.startActivity(Intent(context!!, CalendarStudentActivity::class.java))
                        }
                        "Fee Details" -> {
                            context!!.startActivity(Intent(context!!, FeeStudentActivity::class.java))
                        }
                        "Examination" -> {
                            context!!.startActivity(Intent(context!!, ExamStudentActivity::class.java))
                        }
                        "Report Cards" -> {
                            context!!.startActivity(Intent(context!!, ReportCardStudentActivity::class.java))
                        }
                        "Notice Board" -> {
                            context!!.startActivity(Intent(context!!, NoticeBoardStudentActivity::class.java))
                        }
                        "Multimedia" -> {
                            context!!.startActivity(Intent(context!!, GalleryStudentActivity::class.java))
                        }
                        "Profile" -> {
                            context!!.startActivity(Intent(context!!, ProfileStudentActivity::class.java))
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