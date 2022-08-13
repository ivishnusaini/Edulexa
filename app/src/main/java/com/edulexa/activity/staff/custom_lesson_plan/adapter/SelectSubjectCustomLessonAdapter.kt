package com.edulexa.activity.staff.custom_lesson_plan.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.custom_lesson_plan.activity.CustomLessonClassListActivity
import com.edulexa.activity.staff.custom_lesson_plan.activity.SelectSubjectActivity
import com.edulexa.activity.staff.custom_lesson_plan.model.subject.Subject
import com.edulexa.activity.staff.student_profile.model.class_list.ClassData
import com.edulexa.databinding.ItemStaffClassListBinding
import com.edulexa.databinding.ItemStaffSelectSubjectBinding

class SelectSubjectCustomLessonAdapter(context: Activity, list: List<Subject?>?) :
    RecyclerView.Adapter<SelectSubjectCustomLessonAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<Subject?>? = null
    var binding: ItemStaffSelectSubjectBinding? = null

    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffSelectSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        try {
            binding!!.tvClass.text = list!![position]!!.getName()
            /*if (list!![position]!!.isSelect()){
                binding!!.tvClass.setTextColor(ContextCompat.getColor(context!!,R.color.white))
                binding!!.classLay.setBackgroundResource(R.drawable.bg_button_5)
                binding!!.classLay.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.colorPrimary)
            }else{
                binding!!.tvClass.setTextColor(ContextCompat.getColor(context!!,R.color.primaray_text_color))
                binding!!.classLay.setBackgroundResource(R.drawable.bg_border_5)
                binding!!.classLay.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.card_view_bg)
            }*/
            viewHolder.itemView.setOnClickListener {
                /*resetAll(position)*/
                (context as SelectSubjectActivity).selectSubject(list!![position]!!.getId()!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun resetAll(position : Int){
        for (model in list!!){
            model!!.setSelect(false)
        }
        list!![position]!!.setSelect(true)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffSelectSubjectBinding) : RecyclerView.ViewHolder(binding.root)
}