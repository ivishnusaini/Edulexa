package com.edulexa.activity.student.download_center.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.class_timetable.model.ClassTimetableModel
import com.edulexa.activity.student.download_center.model.DownloadCenterModel
import com.edulexa.databinding.ItemStudentClassTimetableBinding

class DownloadCenterTypeAdapter(context: Activity, list: List<DownloadCenterModel>?) :
    RecyclerView.Adapter<DownloadCenterTypeAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<DownloadCenterModel>? = null
    var binding: ItemStudentClassTimetableBinding? = null

    init {
        this.context = context
        this.list = list
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentClassTimetableBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        try {
            if (list!!.get(position).getSelectedFlag()) {
                binding!!.tvStudentClassTimetable.setBackgroundResource(R.drawable.bg_button_5)
                binding!!.tvStudentClassTimetable.setTextColor(
                    ContextCompat.getColor(
                        context!!,
                        R.color.white
                    )
                )
            }
            else {
                binding!!.tvStudentClassTimetable.setBackgroundResource(0)
                binding!!.tvStudentClassTimetable.setTextColor(
                    ContextCompat.getColor(
                        context!!,
                        R.color.primaray_text_color
                    )
                )
            }

            binding!!.tvStudentClassTimetable.text = list!!.get(position).getType()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentClassTimetableBinding) :
        RecyclerView.ViewHolder(binding.root)
}