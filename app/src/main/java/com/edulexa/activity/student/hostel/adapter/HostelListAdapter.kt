package com.edulexa.activity.student.hostel.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.activity.student.hostel.activity.HostelDetailActivity
import com.edulexa.activity.student.hostel.model.DatumHostel
import com.edulexa.activity.student.report_card.activity.ReportCardDetailActivity
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStudentHostelListBinding

class HostelListAdapter(context: Activity, list : List<DatumHostel?>?) :
    RecyclerView.Adapter<HostelListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DatumHostel?>? = null
    var binding : ItemStudentHostelListBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentHostelListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            binding!!.tvHostelStudentName.text = list!!.get(position)!!.getHostelName()
            viewHolder.itemView.setOnClickListener(object :View.OnClickListener{
                override fun onClick(p0: View?) {
                    val bundle = Bundle()
                    bundle.putString(Constants.StudentHostel.TITLE,list!!.get(position)!!.getHostelName())
                    bundle.putString(Constants.StudentHostel.HOSTELID,list!!.get(position)!!.getId())
                    context!!.startActivity(Intent(context, HostelDetailActivity::class.java).putExtras(bundle))
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentHostelListBinding) : RecyclerView.ViewHolder(binding.root)
}