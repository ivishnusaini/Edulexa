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
import com.edulexa.activity.student.hostel.model.hostel_detail.DatumHostelDetail
import com.edulexa.activity.student.report_card.activity.ReportCardDetailActivity
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStudentHostelDetailBinding
import com.edulexa.databinding.ItemStudentHostelListBinding

class HostelDetailAdapter(context: Activity, list : List<DatumHostelDetail?>?) :
    RecyclerView.Adapter<HostelDetailAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DatumHostelDetail?>? = null
    var binding : ItemStudentHostelDetailBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentHostelDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            binding!!.tvHostelDetailRoomType.text = list!!.get(position)!!.getRoomType()
            binding!!.tvHostelDetailRoomNo.text = list!!.get(position)!!.getRoomNo()
            binding!!.tvHostelDetailRoomCost.text = list!!.get(position)!!.getCostPerBed()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentHostelDetailBinding) : RecyclerView.ViewHolder(binding.root)
}