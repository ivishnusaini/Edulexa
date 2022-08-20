package com.edulexa.activity.staff.homework.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.homework.activity.EvaluationActivity
import com.edulexa.activity.staff.homework.activity.HomeworkImagesActivity
import com.edulexa.activity.staff.homework.model.evaluation.StudentEvaluation
import com.edulexa.activity.staff.online_exam.activity.*
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStaffEvaluationListBinding
import com.google.gson.Gson

class EvaluationListAdapter(context: Activity, list: List<StudentEvaluation?>?) :
    RecyclerView.Adapter<EvaluationListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<StudentEvaluation?>? = null
    var binding: ItemStaffEvaluationListBinding? = null

    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffEvaluationListBinding.inflate(
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
            binding!!.tvName.text = context!!.getString(R.string.dashboard_student_class_section_format,list!![position]!!.getFirstname(),"(",list!![position]!!.getAdmissionNo(),")")
            binding!!.etComment.setText(list!![position]!!.getComment())
            binding!!.etComment.setSelection(list!![position]!!.getComment()!!.length)
            binding!!.nameCheckbox.isChecked = list!![position]!!.getHomeworkEvaluationId() != "0"
            if (list!![position]!!.getSubmitDoc() != null && list!![position]!!.getSubmitDoc()!!.isNotEmpty())
                binding!!.tvAttachedImages.visibility = View.VISIBLE
            else binding!!.tvAttachedImages.visibility = View.GONE

            binding!!.nameCheckbox.setOnCheckedChangeListener { p0, isChecked ->
                if (!isChecked) {
                    list!![position]!!.setNameSelect(false)
                    binding!!.nameCheckbox.isSelected = false
                } else {
                    list!![position]!!.setNameSelect(true)
                    binding!!.nameCheckbox.isSelected = true
                }
            }

            binding!!.resubmitCheckbox.setOnCheckedChangeListener { p0, isChecked ->
                if (!isChecked) {
                    list!![position]!!.setResubmitSelect(false)
                    binding!!.resubmitCheckbox.isSelected = false
                } else {
                    list!![position]!!.setResubmitSelect(true)
                    binding!!.resubmitCheckbox.isSelected = true
                }
            }

            binding!!.etComment.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    list!![position]!!.setComment(s.toString())
                }

            })

            binding!!.tvAttachedImages.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(Constants.StaffHomework.IMAGES, Gson().toJson(list!![position]!!.getSubmitDoc()))
                context!!.startActivity(Intent(context, HomeworkImagesActivity::class.java).putExtras(bundle))
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffEvaluationListBinding) :
        RecyclerView.ViewHolder(binding.root)
}