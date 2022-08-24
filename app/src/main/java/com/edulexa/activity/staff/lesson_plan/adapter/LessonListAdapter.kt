package com.edulexa.activity.staff.lesson_plan.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.custom_lesson_plan.activity.CustomLessonPlanActivity
import com.edulexa.activity.staff.homework.activity.EvaluationActivity
import com.edulexa.activity.staff.lesson_plan.activity.LessonListActivity
import com.edulexa.activity.staff.lesson_plan.model.list.TimetableLessonPlan
import com.edulexa.activity.staff.online_exam.activity.*
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStaffLessonPlanListBinding
import com.edulexa.support.Utils

class LessonListAdapter(context: Activity, list: List<TimetableLessonPlan?>?) :
    RecyclerView.Adapter<LessonListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<TimetableLessonPlan?>? = null
    var binding: ItemStaffLessonPlanListBinding? = null
    var roleId = ""
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffLessonPlanListBinding.inflate(
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
            roleId = Utils.getStaffRoleId(context!!)
            binding!!.tvClass.text = list!![position]!!.getClass_()
            binding!!.tvSection.text = list!![position]!!.getSection()
            binding!!.tvSubjectName.text = list!![position]!!.getSubjectName()
            binding!!.tvTime.text = context!!.getString(R.string.dashboard_student_present_format,list!![position]!!.getTimeFrom()," to ",list!![position]!!.getTimeTo())

            if (list!![position]!!.getSyllabusId().equals("0")) {
                binding!!.editDeleteLay.visibility = View.GONE
                binding!!.tvAdd.visibility = View.VISIBLE
                if (roleId == "1" || roleId == "7")
                    binding!!.tvAdd.visibility = View.GONE
            } else {
                binding!!.editDeleteLay.visibility = View.VISIBLE
                binding!!.tvAdd.visibility = View.GONE
                if (roleId == "1" || roleId == "7") {
                    binding!!.tvDelete.visibility = View.GONE
                    binding!!.tvEdit.text = context!!.getString(R.string.lesson_plan_staff_view)
                }
            }

            binding!!.tvAdd.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(Constants.StaffLessonList.HOMEWORK_ID, list!![position]!!.getId())
                context!!.startActivity(Intent(context, EvaluationActivity::class.java).putExtras(bundle))
            }

            binding!!.tvDelete.setOnClickListener {
                (context as LessonListActivity).deleteSyllabus(list!![position]!!.getSyllabusId()!!)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffLessonPlanListBinding) :
        RecyclerView.ViewHolder(binding.root)
}