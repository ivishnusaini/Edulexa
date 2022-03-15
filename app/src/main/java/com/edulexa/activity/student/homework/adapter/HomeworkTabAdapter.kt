package com.edulexa.activity.student.homework.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.homework.model.ViewpagerModel
import com.edulexa.activity.student.homework.activity.HomeworkStudentActivity

class HomeworkTabAdapter(context: Activity, list: List<ViewpagerModel>?) :
    RecyclerView.Adapter<HomeworkTabAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<ViewpagerModel>? = null

    init {
        this.context = context
        this.list = list
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<ViewpagerModel>?) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_student_homework_tab, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        try {
            viewHolder.tvTabName!!.text = list!!.get(position).getTabName()
            if (list!!.get(position).getSelectStatus()!!) {
                viewHolder.tvTabName!!.setBackgroundResource(R.drawable.bg_button_25)
                viewHolder.tvTabName!!.setTextColor(
                    ContextCompat.getColor(
                        context!!,
                        R.color.white
                    )
                )
                (context as HomeworkStudentActivity).setViewPagerFromTabClick(position)
            } else {
                viewHolder.tvTabName!!.setBackgroundResource(R.drawable.edit_text_bg_25)
                viewHolder.tvTabName!!.setTextColor(
                    ContextCompat.getColor(
                        context!!,
                        R.color.primaray_text_color
                    )
                )
            }
            viewHolder.itemView.setOnClickListener(object : View.OnClickListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onClick(p0: View?) {
                    for (model in list!!) {
                        model.setSelectStatus(false)
                    }
                    list!!.get(position).setSelectStatus(true)
                    notifyDataSetChanged()
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTabName: TextView? = null

        init {
            tvTabName = itemView.findViewById(R.id.tv_homework_tab_name)
        }
    }
}