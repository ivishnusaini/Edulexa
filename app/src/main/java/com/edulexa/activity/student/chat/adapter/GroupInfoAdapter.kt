package com.edulexa.activity.student.chat.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.chat.model.group.GroupUser
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStudentGroupInfoBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils

class GroupInfoAdapter(context: Activity, list : List<GroupUser?>?) : RecyclerView.Adapter<GroupInfoAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<GroupUser?>?= null
    var binding : ItemStudentGroupInfoBinding? = null
    var preference : Preference?= null
    init {
        this.context = context
        this.list = list
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentGroupInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            preference = Preference().getInstance(context!!)
            binding!!.tvName.text = list!![position]!!.getName()
            if (!list!![position]!!.getImage().equals("")){
                var baseUrlForImage = preference!!.getString(Constants.Preference.IMAGESURL_STUDENT)
                if (!baseUrlForImage!!.endsWith("/"))
                    baseUrlForImage += "/"
                Utils.setpProfileImageUsingGlide(context,baseUrlForImage+list!![position]!!.getImage(),binding!!.ivImage)
            }else binding!!.ivImage.setBackgroundResource(R.drawable.group_student)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentGroupInfoBinding) : RecyclerView.ViewHolder(binding.root)
}