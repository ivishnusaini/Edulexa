package com.edulexa.activity.staff.custom_lesson_plan.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.custom_lesson_plan.activity.CustomLessonClassListActivity
import com.edulexa.activity.staff.student_profile.model.section.Section
import com.edulexa.databinding.ItemStaffClassListBinding

class SectionCustomLessonAdapter(context: Activity, list: List<Section?>?) :
    RecyclerView.Adapter<SectionCustomLessonAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<Section?>? = null
    var binding: ItemStaffClassListBinding? = null

    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffClassListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        try {
            binding!!.tvClass.text = list!![position]!!.getSection()
            if (list!![position]!!.isSectionSelect()){
                binding!!.tvClass.setTextColor(ContextCompat.getColor(context!!,R.color.white))
                binding!!.classLay.setBackgroundResource(R.drawable.bg_button_5)
                binding!!.classLay.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.colorPrimary)
            }else{
                binding!!.tvClass.setTextColor(ContextCompat.getColor(context!!,R.color.primaray_text_color))
                binding!!.classLay.setBackgroundResource(R.drawable.bg_border_5)
                binding!!.classLay.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.card_view_bg)
            }
            viewHolder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    resetAll(position)
                    (context as CustomLessonClassListActivity).selectSection(list!![position]!!.getSectionId()!!
                        ,list!![position]!!.getSection()!!
                        ,list!![position]!!.getId()!!)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun resetAll(position : Int){
        for (model in list!!){
            model!!.setSectionSelect(false)
        }
        list!![position]!!.setSectionSelect(true)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffClassListBinding) : RecyclerView.ViewHolder(binding.root)
}