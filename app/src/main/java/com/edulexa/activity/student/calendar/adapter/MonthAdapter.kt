package com.edulexa.activity.student.calendar.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.calendar.activity.CalendarStudentActivity
import com.edulexa.activity.student.calendar.model.MonthModel

class MonthAdapter(context: Activity, list: List<MonthModel>?) :
    RecyclerView.Adapter<MonthAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<MonthModel>? = null

    init {
        this.context = context
        this.list = list
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
                if (list!!.get(position).getSelectStatus()!!) {
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
                }
                if (list!!.get(position).getCurrentDateFlag()!!) {
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
                }

                viewHolder.itemView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        for (model in list!!) {
                            model.setSelectStatus(false)
                        }
                        list!!.get(position).setSelectStatus(true)
                        (context as CalendarStudentActivity).updateList(list!!,list!!.get(position).getCompleteDate()!!)
                    }

                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
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