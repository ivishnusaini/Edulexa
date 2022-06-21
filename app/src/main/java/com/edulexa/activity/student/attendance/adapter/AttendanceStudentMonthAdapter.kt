package com.edulexa.activity.student.attendance.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.attendance.model.Attendance
import com.edulexa.activity.student.attendance.model.AttendanceStudentMonthModel
import com.edulexa.activity.student.dashboard.adapter.DashboardStudentAdapter
import com.edulexa.databinding.ItemStudentAttedndanceMonthBinding
import com.edulexa.databinding.ItemStudentDashboardBinding
import java.util.*

class AttendanceStudentMonthAdapter(context: Activity, list: List<AttendanceStudentMonthModel>?,attendanceList : List<Attendance?>?) :
    RecyclerView.Adapter<AttendanceStudentMonthAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<AttendanceStudentMonthModel>? = null
    var attendanceList : List<Attendance?>? = null
    var binding : ItemStudentAttedndanceMonthBinding? = null
    init {
        this.context = context
        this.list = list
        this.attendanceList = attendanceList
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentAttedndanceMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        try {
            if (list!!.get(position).getStartDateFlag()!!) {
                binding!!.tvDate.text = list!!.get(position).getDay()
                when(getAttendanceType(list!!.get(position).getDay()!!).lowercase()){
                    "present" -> {
                        binding!!.tvDate.setBackgroundResource(R.drawable.circle)
                        binding!!.tvDate.background.setTint(
                            ContextCompat.getColor(
                                context!!,
                                R.color.present_bg
                            )
                        )
                        binding!!.tvDate.setTextColor(
                            ContextCompat.getColor(
                                context!!,
                                R.color.present_text_color
                            )
                        )
                    }
                    "absent" -> {
                        binding!!.tvDate.setBackgroundResource(R.drawable.circle)
                        binding!!.tvDate.background.setTint(
                            ContextCompat.getColor(
                                context!!,
                                R.color.absent_bg
                            )
                        )
                        binding!!.tvDate.setTextColor(
                            ContextCompat.getColor(
                                context!!,
                                R.color.absent_text_color
                            )
                        )
                    }
                    "holiday" -> {
                        binding!!.tvDate.setBackgroundResource(R.drawable.circle)
                        binding!!.tvDate.background.setTint(
                            ContextCompat.getColor(
                                context!!,
                                R.color.holiday_bg
                            )
                        )
                        binding!!.tvDate.setTextColor(
                            ContextCompat.getColor(
                                context!!,
                                R.color.holiday_text_color
                            )
                        )
                    }
                    else -> {
                        binding!!.tvDate.setBackgroundResource(R.drawable.circle)
                        binding!!.tvDate.background.setTint(
                            ContextCompat.getColor(
                                context!!,
                                R.color.default_bg
                            )
                        )
                        binding!!.tvDate.setTextColor(
                            ContextCompat.getColor(
                                context!!,
                                R.color.default_bg_text_color
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getAttendanceType(dateStr: String) : String{
        var attendaceType = ""
        try{
            if (attendanceList != null && attendanceList!!.size > 0){
                for (model in attendanceList!!){
                    val completeDateStr = model!!.getDate()
                    val separated: List<String> = completeDateStr!!.split("-")
                    if (dateStr.toInt() == separated.get(2).toInt()){
                        attendaceType = model.getType()!!
                        break
                    }
                }
            }
            return attendaceType
        }catch (e : Exception){
            e.printStackTrace()
            return attendaceType
        }
    }
    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentAttedndanceMonthBinding) : RecyclerView.ViewHolder(binding.root) 
}