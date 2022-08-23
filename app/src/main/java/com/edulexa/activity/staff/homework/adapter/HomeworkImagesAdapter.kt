package com.edulexa.activity.staff.homework.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.activity.staff.homework.activity.HomeworkImagesViewActivity
import com.edulexa.activity.staff.homework.model.evaluation.SubmitDoc
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStaffOnlineExamAlbumBinding
import com.edulexa.support.Utils

class HomeworkImagesAdapter(context: Activity, list: List<SubmitDoc?>?) :
    RecyclerView.Adapter<HomeworkImagesAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<SubmitDoc?>? = null
    var binding: ItemStaffOnlineExamAlbumBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffOnlineExamAlbumBinding.inflate(
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
            Utils.setImageUsingGlide(context,list!![position]!!.getFileUrl(),binding!!.ivImage)
            binding!!.tvName.visibility = View.GONE


            viewHolder.itemView.setOnClickListener {
                val imageUrl = list!![position]!!.getFileUrl()
                val bundle = Bundle()
                bundle.putString(Constants.StaffHomework.IMAGEURL, imageUrl)
                context!!.startActivity(Intent(context, HomeworkImagesViewActivity::class.java).putExtras(bundle))
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffOnlineExamAlbumBinding) :
        RecyclerView.ViewHolder(binding.root)
}