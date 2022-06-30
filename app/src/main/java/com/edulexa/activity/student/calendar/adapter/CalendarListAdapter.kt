package com.edulexa.activity.student.calendar.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.attendance.adapter.AttendanceStudentMonthAdapter
import com.edulexa.activity.student.calendar.model.DatumCalendar
import com.edulexa.databinding.ItemStudentAttedndanceMonthBinding
import com.edulexa.databinding.ItemStudentCalendarBinding
import com.edulexa.support.Utils
import java.util.*

class CalendarListAdapter(context: Activity,list : List<DatumCalendar?>?) :
    RecyclerView.Adapter<CalendarListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DatumCalendar?>? = null
    var binding : ItemStudentCalendarBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            binding!!.tvCalendarDate.text = getDate(list!!.get(position)!!.getDate()!!)
            binding!!.tvCalendarMonth.text = getMonthName(list!!.get(position)!!.getDate()!!)
            binding!!.tvCalendarEventName.text = list!!.get(position)!!.getName()
            binding!!.tvCalendarEventDescription.text = list!!.get(position)!!.getDesciption()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getDate(completeDate : String) : String{
        var dateStr = ""
        if (!completeDate.equals("")){
            val tokenizer = StringTokenizer(completeDate,"-")
            val year = tokenizer.nextToken()
            val month = tokenizer.nextToken()
            dateStr = tokenizer.nextToken()
        }
        return dateStr
    }
    private fun getMonthName(completeDate : String) : String{
        var monthStr = ""
        if (!completeDate.equals("")){
            val tokenizer = StringTokenizer(completeDate,"-")
            val year = tokenizer.nextToken()
            val month = tokenizer.nextToken()
            monthStr = Utils.getShortMonthNameFromMonthNo(month.toInt())
        }
        return monthStr
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentCalendarBinding) : RecyclerView.ViewHolder(binding.root)
}