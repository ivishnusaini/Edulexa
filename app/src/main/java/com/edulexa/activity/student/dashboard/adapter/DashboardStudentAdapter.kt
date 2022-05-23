package com.edulexa.activity.student.dashboard.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.attendance.activity.AttendanceStudentActivity
import com.edulexa.activity.student.calendar.activity.CalendarStudentActivity
import com.edulexa.activity.student.dashboard.model.DashboardModuleModel
import com.edulexa.activity.student.dashboard.model.ModuleDashboard
import com.edulexa.activity.student.examination.activity.ExamStudentActivity
import com.edulexa.activity.student.fee.activity.FeeStudentActivity
import com.edulexa.activity.student.gallery.activity.GalleryStudentActivity
import com.edulexa.activity.student.homework.activity.HomeworkStudentActivity
import com.edulexa.activity.student.noticeboard.activity.NoticeBoardStudentActivity
import com.edulexa.activity.student.profile.activity.ProfileStudentActivity
import com.edulexa.activity.student.report_card.activity.ReportCardStudentActivity
import com.edulexa.databinding.ItemStudentDashboardBinding
import com.edulexa.databinding.ItemStudentDashboardNoticeBoardBinding
import com.edulexa.support.Utils

class DashboardStudentAdapter(context: Activity,list : List<DashboardModuleModel?>?) :
    RecyclerView.Adapter<DashboardStudentAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DashboardModuleModel?>? = null
    var binding : ItemStudentDashboardBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentDashboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            Utils.setImageUsingGlide(context,list!!.get(position)!!.getIconLink(),binding!!.ivDashboardImage)
            binding!!.tvDashboardName.text = list!!.get(position)!!.getName()

            viewHolder.itemView.setOnClickListener(object :View.OnClickListener{
                override fun onClick(p0: View?) {
                    when(list!!.get(position)!!.getName()){
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
                        "Attendance" -> {
                            context!!.startActivity(Intent(context!!, AttendanceStudentActivity::class.java))
                        }
                    }
                }

            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentDashboardBinding) : RecyclerView.ViewHolder(binding.root)
}