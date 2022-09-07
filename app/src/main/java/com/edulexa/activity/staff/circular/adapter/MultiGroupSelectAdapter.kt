package com.edulexa.activity.staff.circular.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.activity.staff.circular.model.role_type.Role
import com.edulexa.databinding.ItemStaffAddCustomLessonMultiSectionBinding
import com.edulexa.databinding.ItemStaffCircularPostMessageGroupBinding

class MultiGroupSelectAdapter(context: Activity, list: List<Role?>?) :
    RecyclerView.Adapter<MultiGroupSelectAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<Role?>? = null
    var binding: ItemStaffCircularPostMessageGroupBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffCircularPostMessageGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        try {
            binding!!.tvSection.text = list!![position]!!.getName()
            binding!!.checkBox.isChecked = false
            binding!!.checkBox.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                list!![position]!!.setSelectRole(isChecked)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffCircularPostMessageGroupBinding) : RecyclerView.ViewHolder(binding.root)
}