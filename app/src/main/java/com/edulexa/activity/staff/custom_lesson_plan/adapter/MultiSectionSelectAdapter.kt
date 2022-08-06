package com.edulexa.activity.staff.custom_lesson_plan.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.activity.staff.student_profile.model.section.Section
import com.edulexa.databinding.ItemStaffAddCustomLessonMultiSectionBinding

class MultiSectionSelectAdapter(context: Activity, list: List<Section?>?) :
    RecyclerView.Adapter<MultiSectionSelectAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<Section?>? = null
    var binding: ItemStaffAddCustomLessonMultiSectionBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffAddCustomLessonMultiSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        try {
            binding!!.tvSection.text = list!![position]!!.getSection()
            binding!!.checkBox.isChecked = false
            binding!!.checkBox.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                list!![position]!!.setSectionSelect(isChecked)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffAddCustomLessonMultiSectionBinding) : RecyclerView.ViewHolder(binding.root)
}