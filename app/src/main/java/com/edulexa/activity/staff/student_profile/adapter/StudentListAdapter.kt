package com.edulexa.activity.staff.student_profile.adapter

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
import com.edulexa.activity.staff.student_profile.activity.StudentListActivity
import com.edulexa.activity.staff.student_profile.activity.StudentProfileClassListActivity
import com.edulexa.activity.staff.student_profile.activity.StudentProfileDetailACtivity
import com.edulexa.activity.staff.student_profile.model.class_list.ClassData
import com.edulexa.activity.staff.student_profile.model.student_list.StudentDatum
import com.edulexa.activity.student.online_exam.adapter.OnlineExamListAdapter
import com.edulexa.activity.student.report_card.activity.ReportCardDetailActivity
import com.edulexa.activity.student.report_card.model.DatumReportCardList
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStaffClassListBinding
import com.edulexa.databinding.ItemStaffStudentListBinding
import com.edulexa.databinding.ItemStudentOnlineExamListBinding
import com.edulexa.databinding.ItemStudentReportCardBinding
import com.edulexa.support.Utils

class StudentListAdapter(context: Activity, list: List<StudentDatum?>?) :
    RecyclerView.Adapter<StudentListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<StudentDatum?>? = null
    var binding: ItemStaffStudentListBinding? = null

    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffStudentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        try {
            Utils.setpProfileImageUsingGlide(context,Constants.BASE_URL_STAFF+list!![position]!!.getImage(),binding!!.ivImage)
            binding!!.tvName.text = context!!.getString(R.string.dashboard_student_present_format,list!![position]!!.getFirstname()," ",list!![position]!!.getLastname())
            binding!!.tvAdmissionNo.text = list!![position]!!.getAdmissionNo()
            binding!!.tvRollNo.text = list!![position]!!.getRollNo()

            viewHolder.itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(Constants.StaffStudentProfile.TOKEN,list!![position]!!.getToken())
                bundle.putString(Constants.StaffStudentProfile.STUDENT_ID,list!![position]!!.getId())
                bundle.putString(Constants.StaffStudentProfile.STUDENT_SESSION_ID,list!![position]!!.getStudentSessionId())
                bundle.putString(Constants.StaffStudentProfile.IMAGE,list!![position]!!.getImage())
                bundle.putString(Constants.StaffStudentProfile.NAME,binding!!.tvName.text.toString())
                bundle.putString(Constants.StaffStudentProfile.ROLL_NO,binding!!.tvRollNo.text.toString())
                bundle.putString(Constants.StaffStudentProfile.CLASS,list!![position]!!.getClass_())
                bundle.putString(Constants.StaffStudentProfile.SECTION,list!![position]!!.getSection())
                context!!.startActivity(Intent(context, StudentProfileDetailACtivity::class.java).putExtras(bundle))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffStudentListBinding) : RecyclerView.ViewHolder(binding.root)
}