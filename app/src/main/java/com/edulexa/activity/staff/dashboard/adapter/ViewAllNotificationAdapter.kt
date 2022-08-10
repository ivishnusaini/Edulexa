package com.edulexa.activity.staff.dashboard.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.activity.staff.dashboard.model.notifications.DatumNotification
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStaffDashboardNotificationBinding
import com.edulexa.databinding.ItemStaffNotificationDetailBinding
import com.edulexa.support.Utils

class ViewAllNotificationAdapter(context: Activity, list: List<DatumNotification?>?) :
    RecyclerView.Adapter<ViewAllNotificationAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<DatumNotification?>? = null
    var binding: ItemStaffNotificationDetailBinding? = null

    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStaffNotificationDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            binding!!.tvTitle.text = list!![position]!!.getTitle()
            Utils.setHtmlText(list!![position]!!.getMessage()!!, binding!!.tvMessage)
            binding!!.tvDate.text = list!![position]!!.getPublishDate()
            if (!list!![position]!!.getDocument().equals("")) {
                binding!!.ivImage.visibility = View.VISIBLE
                Utils.setImageUsingGlide(
                    context,
                    Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF + list!![position]!!.getDocument(),
                    binding!!.ivImage
                )
            } else binding!!.ivImage.visibility = View.GONE

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffNotificationDetailBinding) :
        RecyclerView.ViewHolder(binding.root)
}