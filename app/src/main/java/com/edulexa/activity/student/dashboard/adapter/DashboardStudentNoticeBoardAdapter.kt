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

class DashboardStudentNoticeBoardAdapter(context: Activity) :
    RecyclerView.Adapter<DashboardStudentNoticeBoardAdapter.ViewHolder>() {
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
                .inflate(R.layout.item_student_dashboard_notice_board, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            when (position) {
                0 -> {
                    viewHolder.noticeBoardTopLay!!.setBackgroundColor(ContextCompat.getColor(context!!,R.color.notice_board_bg_1))
                    viewHolder.tvNoticeMessage!!.text = "School is going for vacation in next month"
                }
                1 -> {
                    viewHolder.noticeBoardTopLay!!.setBackgroundColor(ContextCompat.getColor(context!!,R.color.notice_board_bg_2))
                    viewHolder.tvNoticeMessage!!.text = "Summer book fair at school campus in June"
                }
                2 -> {
                    viewHolder.noticeBoardTopLay!!.setBackgroundColor(ContextCompat.getColor(context!!,R.color.notice_board_bg_3))
                    viewHolder.tvNoticeMessage!!.text = "School is going for vacation in next month"
                }
                3 -> {
                    viewHolder.noticeBoardTopLay!!.setBackgroundColor(ContextCompat.getColor(context!!,R.color.notice_board_bg_1))
                    viewHolder.tvNoticeMessage!!.text = "Summer book fair at school campus in June"
                }
                4 -> {
                    viewHolder.noticeBoardTopLay!!.setBackgroundColor(ContextCompat.getColor(context!!,R.color.notice_board_bg_2))
                    viewHolder.tvNoticeMessage!!.text = "School is going for vacation in next month"
                }
                5 -> {
                    viewHolder.noticeBoardTopLay!!.setBackgroundColor(ContextCompat.getColor(context!!,R.color.notice_board_bg_3))
                    viewHolder.tvNoticeMessage!!.text = "Summer book fair at school campus in June"
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
        var tvNoticeMessage: TextView? = null
        var noticeBoardTopLay: LinearLayout? = null
        init {
            tvNoticeMessage = itemView.findViewById(R.id.tv_notice_message)
            noticeBoardTopLay = itemView.findViewById(R.id.notice_board_top_lay)
        }
    }
}