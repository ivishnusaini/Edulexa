package com.edulexa.activity.student.dashboard.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.activity.student.apply_leave.activity.LeaveListActivity
import com.edulexa.activity.student.attendance.activity.AttendanceStudentActivity
import com.edulexa.activity.student.calendar.activity.CalendarStudentActivity
import com.edulexa.activity.student.class_timetable.activity.ClassTimetableActivity
import com.edulexa.activity.student.custom_lesson_plan.activity.CustomLessonPlanActivity
import com.edulexa.activity.student.dashboard.model.DashboardModuleModel
import com.edulexa.activity.student.download_center.activity.DownloadCenterActivity
import com.edulexa.activity.student.examination.activity.ExamStudentActivity
import com.edulexa.activity.student.fee.activity.FeeStudentActivity
import com.edulexa.activity.student.gallery.activity.GalleryStudentActivity
import com.edulexa.activity.student.homework.activity.HomeworkStudentActivity
import com.edulexa.activity.student.hostel.activity.HostelActivity
import com.edulexa.activity.student.library.activity.LibraryActivity
import com.edulexa.activity.student.live_classes.activity.LiveClassesActivity
import com.edulexa.activity.student.noticeboard.activity.NoticeBoardStudentActivity
import com.edulexa.activity.student.online_exam.activity.OnlineExamListActivity
import com.edulexa.activity.student.profile.activity.ProfileStudentActivity
import com.edulexa.activity.student.report_card.activity.ReportCardStudentActivity
import com.edulexa.activity.student.transport.activity.TransportActivity
import com.edulexa.databinding.ItemStudentDashboardBinding
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
                    when(list!!.get(position)!!.getShortCode()){
                        "class_timetable" -> {
                            context!!.startActivity(Intent(context!!, ClassTimetableActivity::class.java))
                        }
                        "homework" -> {
                            context!!.startActivity(Intent(context!!, HomeworkStudentActivity::class.java))
                        }
                        "download_center" -> {
                            context!!.startActivity(Intent(context!!, DownloadCenterActivity::class.java))
                        }
                        "calendar_to_do_list" -> {
                            context!!.startActivity(Intent(context!!, CalendarStudentActivity::class.java))
                        }
                        "fees" -> {
                            context!!.startActivity(Intent(context!!, FeeStudentActivity::class.java))
                        }
                        "examinations" -> {
                            context!!.startActivity(Intent(context!!, ExamStudentActivity::class.java))
                        }
                        "notice_board" -> {
                            context!!.startActivity(Intent(context!!, NoticeBoardStudentActivity::class.java))
                        }
                        "library" -> {
                            context!!.startActivity(Intent(context!!, LibraryActivity::class.java))
                        }
                        "transport_routes" -> {
                            context!!.startActivity(Intent(context!!, TransportActivity::class.java))
                        }
                        "hostel_rooms" -> {
                            context!!.startActivity(Intent(context!!, HostelActivity::class.java))
                        }
                        "online_examination" -> {
                            context!!.startActivity(Intent(context!!, OnlineExamListActivity::class.java))
                        }
                        "live_classes" -> {
                            context!!.startActivity(Intent(context!!, LiveClassesActivity::class.java))
                        }
                        "apply_leave" -> {
                            context!!.startActivity(Intent(context!!, LeaveListActivity::class.java))
                        }
                        "report_card" -> {
                            context!!.startActivity(Intent(context!!, ReportCardStudentActivity::class.java))
                        }
                        "gallery" -> {
                            context!!.startActivity(Intent(context!!, GalleryStudentActivity::class.java))
                        }
                        "timeline" -> {
                            context!!.startActivity(Intent(context!!, ProfileStudentActivity::class.java))
                        }
                        "lesson_plan" -> {
                            context!!.startActivity(Intent(context!!, CustomLessonPlanActivity::class.java))
                        }
                        "attendance" -> {
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