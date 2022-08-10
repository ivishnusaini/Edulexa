package com.edulexa.activity.staff.dashboard.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.activity.staff.dashboard.activity.ViewAllNotificationActivity
import com.edulexa.activity.staff.dashboard.model.notifications.DatumNotification
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStaffDashboardNotificationBinding
import com.edulexa.support.Utils
import com.google.gson.Gson

class DashboardNotificationAdapter(context: Activity, list : List<DatumNotification?>?) :
    RecyclerView.Adapter<DashboardNotificationAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DatumNotification?>? = null
    var binding : ItemStaffDashboardNotificationBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStaffDashboardNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            binding!!.tvTitle.text = list!![position]!!.getTitle()
            Utils.setHtmlText(list!![position]!!.getMessage()!!,binding!!.tvMessage)

            viewHolder.itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(Constants.StaffNotification.FROM_WHERE,"detail")
                bundle.putString(Constants.StaffNotification.DATUM_NOTIFICATION,Gson().toJson(list!![position]))
                context!!.startActivity(Intent(context,ViewAllNotificationActivity::class.java).putExtras(bundle))
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffDashboardNotificationBinding) : RecyclerView.ViewHolder(binding.root)
}