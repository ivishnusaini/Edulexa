package com.edulexa.activity.staff.online_exam.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.online_exam.activity.OnlineExamActivity
import com.edulexa.activity.staff.online_exam.model.list.ExamOnlineExamStaff
import com.edulexa.databinding.ItemStaffOnlineExamListBinding

class OnlineExamListAdapter(context: Activity, list: List<ExamOnlineExamStaff?>?) :
    RecyclerView.Adapter<OnlineExamListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<ExamOnlineExamStaff?>? = null
    var binding: ItemStaffOnlineExamListBinding? = null

    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffOnlineExamListBinding.inflate(
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
            binding!!.tvExamName.text = list!![position]!!.getExam()
            if (list!![position]!!.getIsPractice().equals("0")) {
                binding!!.tvResults.visibility = View.VISIBLE
                binding!!.tvSubjective.visibility = View.VISIBLE
                binding!!.tvExamType.text = context!!.getString(R.string.online_exam_staff)
            } else {
                binding!!.tvResults.visibility = View.GONE
                binding!!.tvSubjective.visibility = View.GONE
                binding!!.tvExamType.text = context!!.getString(R.string.online_exam_staff_practise_exam)
            }

            binding!!.ivNotification.setOnClickListener {
                (context as OnlineExamActivity).sendNotification(list!![position]!!.getId()!!)
            }
            binding!!.ivDelete.setOnClickListener {
                (context as OnlineExamActivity).showDeleteExamDialog(list!![position]!!.getId()!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffOnlineExamListBinding) :
        RecyclerView.ViewHolder(binding.root)
}