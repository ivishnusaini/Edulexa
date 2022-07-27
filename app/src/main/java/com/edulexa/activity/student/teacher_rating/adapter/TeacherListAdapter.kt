package com.edulexa.activity.student.teacher_rating.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.homework.model.HomeworkData
import com.edulexa.activity.student.teacher_rating.model.DatumTeacherList
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStudentDashboardHomeworkInnerListBinding
import com.edulexa.databinding.ItemStudentHomeworkDateBinding
import com.edulexa.databinding.ItemStudentTeacherListBinding
import com.edulexa.support.Utils

class TeacherListAdapter(context: Activity, list : List<DatumTeacherList?>?) :
    RecyclerView.Adapter<TeacherListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DatumTeacherList?>? = null
    var binding : ItemStudentTeacherListBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentTeacherListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            Utils.setImageUsingGlide(context,Constants.BASE_URL_STUDENT+list!!.get(position)!!.getImagePath()!!+list!!.get(position)!!.getImage()!!,binding!!.ivTeacherListImage)
            binding!!.tvTeacherListName.text = context!!.getString(R.string.concat_string_with_text_format,list!!.get(position)!!.getName()," ",list!!.get(position)!!.getSurname())
            binding!!.tvTeacherListDesignation.text = list!!.get(position)!!.getDesignation()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentTeacherListBinding) : RecyclerView.ViewHolder(binding.root)
}