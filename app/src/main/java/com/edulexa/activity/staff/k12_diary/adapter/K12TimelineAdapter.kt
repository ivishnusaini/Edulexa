package com.edulexa.activity.staff.k12_diary.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.k12_diary.activity.K12DiaryActivity
import com.edulexa.activity.staff.k12_diary.model.Timeline
import com.edulexa.activity.staff.student_profile.activity.StudentListActivity
import com.edulexa.activity.staff.student_profile.activity.StudentProfileClassListActivity
import com.edulexa.activity.staff.student_profile.activity.StudentProfileDetailACtivity
import com.edulexa.activity.staff.student_profile.model.class_list.ClassData
import com.edulexa.activity.staff.student_profile.model.student_list.StudentDatum
import com.edulexa.activity.student.online_exam.adapter.OnlineExamListAdapter
import com.edulexa.activity.student.report_card.activity.ReportCardDetailActivity
import com.edulexa.activity.student.report_card.model.DatumReportCardList
import com.edulexa.api.Constants
import com.edulexa.databinding.*
import com.edulexa.support.Utils

class K12TimelineAdapter(context: Activity, list: List<Timeline?>?) :
    RecyclerView.Adapter<K12TimelineAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<Timeline?>? = null
    var binding: ItemStaffK12TimelineBinding? = null

    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffK12TimelineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        try {
            Utils.setpProfileImageUsingGlide(
                context,
                Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF /*+"uploads/student_timeline/"*/+ list!![position]!!.getDocument(),
                binding!!.ivImage
            )
            binding!!.tvTitle.text = list!![position]!!.getTitle()
            binding!!.tvDescription.text = list!![position]!!.getDescription()
            binding!!.tvDate.text = list!![position]!!.getDate()

            binding!!.ivDelete.setOnClickListener {
                (context as K12DiaryActivity).deleteTimelinePopup(list!![position]!!.getId()!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffK12TimelineBinding) : RecyclerView.ViewHolder(binding.root)
}