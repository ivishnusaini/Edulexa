package com.edulexa.activity.staff.online_exam.adapter.subjective

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.online_exam.activity.*
import com.edulexa.activity.staff.online_exam.model.list.ExamOnlineExamStaff
import com.edulexa.activity.staff.online_exam.model.subjective.info.ImagesArray
import com.edulexa.activity.staff.online_exam.model.subjective.list.ResultList
import com.edulexa.activity.student.report_card.activity.ReportCardDetailActivity
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStaffOnlineExamAlbumBinding
import com.edulexa.databinding.ItemStaffOnlineExamListBinding
import com.edulexa.databinding.ItemStaffOnlineExamSubjectiveBinding
import com.edulexa.support.Utils
import com.google.gson.Gson

class SubjectiveImagesAdapter(context: Activity, list: List<ImagesArray?>?) :
    RecyclerView.Adapter<SubjectiveImagesAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<ImagesArray?>? = null
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
            Utils.setImageUsingGlide(context,Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF+list!![position]!!.getFileUrl(),binding!!.ivImage)
            binding!!.tvName.visibility = View.GONE


            viewHolder.itemView.setOnClickListener {
                val imageUrl = Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF + list!![position]!!.getFileUrl()
                val bundle = Bundle()
                bundle.putString(Constants.StaffOnlineExam.IMAGEURL, imageUrl)
                context!!.startActivity(Intent(context, SubjectiveImageViewActivity::class.java).putExtras(bundle))
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