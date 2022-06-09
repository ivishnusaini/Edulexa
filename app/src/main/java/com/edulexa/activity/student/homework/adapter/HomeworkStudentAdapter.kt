package com.edulexa.activity.student.homework.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.dashboard.adapter.DashboardStudentAdapter
import com.edulexa.activity.student.homework.model.Homework
import com.edulexa.databinding.ItemStudentDashboardBinding
import com.edulexa.databinding.ItemStudentHomeworkDateBinding

class HomeworkStudentAdapter(context: Activity,list : List<Homework?>?) :
    RecyclerView.Adapter<HomeworkStudentAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<Homework?>? = null
    var binding : ItemStudentHomeworkDateBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentHomeworkDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            binding!!.tvHomeworkStudentDate.text = list!!.get(position)!!.getDate()
            if (list!!.get(position)!!.getHomeworklist() != null && list!!.get(position)!!.getHomeworklist()!!.size > 0){
                binding!!.studentHomeworkInnerRecycler.visibility = View.VISIBLE
                binding!!.tvHomeworkStudentNoData.visibility = View.GONE
                binding!!.studentHomeworkInnerRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                binding!!.studentHomeworkInnerRecycler.adapter = HomeworkInnerListStudentAdapter(context!!,list!!.get(position)!!.getHomeworklist())
            }else{
                binding!!.studentHomeworkInnerRecycler.visibility = View.GONE
                binding!!.tvHomeworkStudentNoData.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentHomeworkDateBinding) : RecyclerView.ViewHolder(binding.root)
}