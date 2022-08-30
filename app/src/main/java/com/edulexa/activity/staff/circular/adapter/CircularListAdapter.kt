package com.edulexa.activity.staff.circular.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.circular.activity.CircularActivity
import com.edulexa.activity.staff.dashboard.model.notifications.DatumNotification
import com.edulexa.activity.staff.online_exam.activity.*
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStaffCircularBinding
import com.edulexa.support.Utils

class CircularListAdapter(context: Activity, list: List<DatumNotification?>?) :
    RecyclerView.Adapter<CircularListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<DatumNotification?>? = null
    var binding: ItemStaffCircularBinding? = null
    var downloadID: Long = 0
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffCircularBinding.inflate(
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
            context!!.registerReceiver(
                onDownloadComplete,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )
            binding!!.tvTitle.text = list!![position]!!.getTitle()
            binding!!.tvMessageShort.text = list!![position]!!.getMessage()
            binding!!.tvMessage.text = list!![position]!!.getMessage()
            binding!!.tvPublishDate.text = list!![position]!!.getPublishDate()
            binding!!.tvCircularDate.text = list!![position]!!.getDate()
            binding!!.tvCreatedBy.text = list!![position]!!.getCreatedBy()


            if (list!![position]!!.getDocument().equals(""))
                binding!!.attachmentLay.visibility = View.GONE
            else {
                binding!!.attachmentLay.visibility = View.VISIBLE
                if (list!![position]!!.getDocument()!!.contains(".jpg")
                    || list!![position]!!.getDocument()!!.contains(".jpeg")
                    || list!![position]!!.getDocument()!!.contains(".png")
                ) {
                    binding!!.ivDownload.setBackgroundResource(0)
                    Utils.setImageUsingGlide(
                        context,
                        Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF + list!![position]!!.getDocument(),
                        binding!!.ivDownload
                    )
                } else {
                    binding!!.ivDownload.background = null
                    binding!!.ivDownload.setBackgroundResource(R.drawable.ic_download)
                }
            }

            when (position % 3) {
                0 -> {
                    binding!!.view.setBackgroundColor(context!!.resources.getColor(R.color.circular_1))
                    binding!!.ivPublishDate.backgroundTintList = ContextCompat.getColorStateList(
                        context!!,
                        R.color.circular_1
                    )
                    binding!!.ivCircularDate.backgroundTintList = ContextCompat.getColorStateList(
                        context!!,
                        R.color.circular_1
                    )
                    binding!!.ivCreatedBy.backgroundTintList = ContextCompat.getColorStateList(
                        context!!,
                        R.color.circular_1
                    )
                    binding!!.ivDownload.backgroundTintList = ContextCompat.getColorStateList(
                        context!!,
                        R.color.circular_1
                    )
                }
                1 -> {
                    binding!!.view.setBackgroundColor(context!!.resources.getColor(R.color.circular_2))
                    binding!!.ivPublishDate.backgroundTintList = ContextCompat.getColorStateList(
                        context!!,
                        R.color.circular_2
                    )
                    binding!!.ivCircularDate.backgroundTintList = ContextCompat.getColorStateList(
                        context!!,
                        R.color.circular_2
                    )
                    binding!!.ivCreatedBy.backgroundTintList = ContextCompat.getColorStateList(
                        context!!,
                        R.color.circular_2
                    )
                    binding!!.ivDownload.backgroundTintList = ContextCompat.getColorStateList(
                        context!!,
                        R.color.circular_2
                    )
                }
                2 -> {
                    binding!!.view.setBackgroundColor(context!!.resources.getColor(R.color.circular_3))
                    binding!!.ivPublishDate.backgroundTintList = ContextCompat.getColorStateList(
                        context!!,
                        R.color.circular_3
                    )
                    binding!!.ivCircularDate.backgroundTintList = ContextCompat.getColorStateList(
                        context!!,
                        R.color.circular_3
                    )
                    binding!!.ivCreatedBy.backgroundTintList = ContextCompat.getColorStateList(
                        context!!,
                        R.color.circular_3
                    )
                    binding!!.ivDownload.backgroundTintList = ContextCompat.getColorStateList(
                        context!!,
                        R.color.circular_3
                    )
                }
                else -> {
                    binding!!.view.setBackgroundColor(context!!.resources.getColor(R.color.circular_1))
                    binding!!.ivPublishDate.backgroundTintList = ContextCompat.getColorStateList(
                        context!!,
                        R.color.circular_1
                    )
                    binding!!.ivCircularDate.backgroundTintList = ContextCompat.getColorStateList(
                        context!!,
                        R.color.circular_1
                    )
                    binding!!.ivCreatedBy.backgroundTintList = ContextCompat.getColorStateList(
                        context!!,
                        R.color.circular_1
                    )
                    binding!!.ivDownload.backgroundTintList = ContextCompat.getColorStateList(
                        context!!,
                        R.color.circular_1
                    )
                }
            }

            binding!!.titleLay.setOnClickListener {
                if (binding!!.cvDetail.visibility == View.VISIBLE) {
                    binding!!.cvDetail.visibility = View.GONE
                    binding!!.tvMessageShort.visibility = View.VISIBLE
                } else {
                    binding!!.cvDetail.visibility = View.VISIBLE
                    binding!!.tvMessageShort.visibility = View.GONE
                }
            }

            binding!!.ivDelete.setOnClickListener {
                (context as CircularActivity).deleNotification(list!![position]!!.getId()!!)
            }

            binding!!.ivDownload.setOnClickListener {
                val url = Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF + list!![position]!!.getDocument()
                if (url.contains(".jpg") || url.contains(".jpeg") || url.contains(".png")){

                }else downloadID = Utils.startDownload(context!!,url,url)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffCircularBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadID == id) {
                val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.app_icon)
                    .setContentTitle(context.applicationContext.getString(R.string.app_name))
                    .setContentText("All Download completed")
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(455, mBuilder.build())
            }
        }
    }
}