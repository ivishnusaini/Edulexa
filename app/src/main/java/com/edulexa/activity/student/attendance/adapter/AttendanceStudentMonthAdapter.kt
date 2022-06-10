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
import java.util.*

class AttendanceStudentMonthAdapter(context: Activity, list: List<AttendanceStudentMonthModel>?,attendanceList : List<Attendance?>?) :
    RecyclerView.Adapter<AttendanceStudentMonthAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<AttendanceStudentMonthModel>? = null
    var attendanceList : List<Attendance?>? = null
    init {
        this.context = context
        this.list = list
        this.attendanceList = attendanceList
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_student_attedndance_month, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        try {
            if (list!!.get(position).getStartDateFlag()!!) {
                viewHolder.tvDate!!.text = list!!.get(position).getDay()
                when(getAttendanceType(list!!.get(position).getDay()!!).lowercase()){
                    "present" -> {
                        viewHolder.tvDate!!.setBackgroundResource(R.drawable.circle)
                        viewHolder.tvDate!!.background.setTint(
                            ContextCompat.getColor(
                                context!!,
                                R.color.present_bg
                            )
                        )
                        viewHolder.tvDate!!.setTextColor(
                            ContextCompat.getColor(
                                context!!,
                                R.color.present_text_color
                            )
                        )
                    }
                    "absent" -> {
                        viewHolder.tvDate!!.setBackgroundResource(R.drawable.circle)
                        viewHolder.tvDate!!.background.setTint(
                            ContextCompat.getColor(
                                context!!,
                                R.color.absent_bg
                            )
                        )
                        viewHolder.tvDate!!.setTextColor(
                            ContextCompat.getColor(
                                context!!,
                                R.color.absent_text_color
                            )
                        )
                    }
                    "holiday" -> {
                        viewHolder.tvDate!!.setBackgroundResource(R.drawable.circle)
                        viewHolder.tvDate!!.background.setTint(
                            ContextCompat.getColor(
                                context!!,
                                R.color.holiday_bg
                            )
                        )
                        viewHolder.tvDate!!.setTextColor(
                            ContextCompat.getColor(
                                context!!,
                                R.color.holiday_text_color
                            )
                        )
                    }
                    else -> {
                        viewHolder.tvDate!!.setBackgroundResource(R.drawable.circle)
                        viewHolder.tvDate!!.background.setTint(
                            ContextCompat.getColor(
                                context!!,
                                R.color.default_bg
                            )
                        )
                        viewHolder.tvDate!!.setTextColor(
                            ContextCompat.getColor(
                                context!!,
                                R.color.default_bg_text_color
                            )
                        )
                    }
                }


                /*if (list!!.get(position).getSelectStatus()!!) {
                    viewHolder.tvDate!!.setBackgroundResource(R.drawable.circle_with_border)
                    viewHolder.tvDate!!.background.setTint(
                        ContextCompat.getColor(
                            context!!,
                            R.color.colorPrimary
                        )
                    )
                    viewHolder.tvDate!!.setTextColor(
                        ContextCompat.getColor(
                            context!!,
                            R.color.primaray_text_color
                        )
                    )
                } else {
                    viewHolder.tvDate!!.setBackgroundResource(0)
                    viewHolder.tvDate!!.setTextColor(
                        ContextCompat.getColor(
                            context!!,
                            R.color.primaray_text_color
                        )
                    )
                }*/

                /*if (list!!.get(position).getCurrentDateFlag()!!) {
                    viewHolder.tvDate!!.setBackgroundResource(R.drawable.circle)
                    viewHolder.tvDate!!.background.setTint(
                        ContextCompat.getColor(
                            context!!,
                            R.color.green
                        )
                    )
                    viewHolder.tvDate!!.setTextColor(
                        ContextCompat.getColor(
                            context!!,
                            R.color.white
                        )
                    )
                }*/

               /* viewHolder.itemView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        for (model in list!!) {
                            model.setSelectStatus(false)
                        }
                        list!!.get(position).setSelectStatus(true)
                        (context as AttendanceStudentActivity).updateList(list!!,list!!.get(position).getCompleteDate()!!)
                    }

                })*/
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDate: TextView? = null

        init {
            tvDate = itemView.findViewById(R.id.tv_date)
        }
    }
}