package com.edulexa.activity.staff.school_family.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.school_family.model.DatumSchoolFamily
import com.edulexa.activity.student.chat.model.staff_list.DatumStaffList
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStaffSchoolFamilyBinding
import com.edulexa.support.Utils

class SchoolFamilyListAdapter(context: Activity, list: List<DatumSchoolFamily?>?) :
    RecyclerView.Adapter<SchoolFamilyListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<DatumSchoolFamily?>? = null
    var binding: ItemStaffSchoolFamilyBinding? = null

    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffSchoolFamilyBinding.inflate(
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
            Utils.setImageUsingGlide(context,Constants.BASE_URL_STAFF+list!![position]!!.getImagePath()+list!![position]!!.getImage(),binding!!.ivImage)
            binding!!.tvName.text = context!!.getString(R.string.concat_string_with_text_format,list!![position]!!.getName()," ",list!![position]!!.getSurname())
            binding!!.tvDesignation.text = list!![position]!!.getDesignation()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    class ViewHolder(binding: ItemStaffSchoolFamilyBinding) :
        RecyclerView.ViewHolder(binding.root)
}