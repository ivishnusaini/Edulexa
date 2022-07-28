package com.edulexa.activity.student.documents.adapter

import android.Manifest
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
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.documents.model.document_folder_detail.DatumFolderDetail
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStudentDocumentDetailBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions

class DocumentDetailAdapter(context: Activity, list : List<DatumFolderDetail?>?) :
    RecyclerView.Adapter<DocumentDetailAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DatumFolderDetail?>? = null
    var binding : ItemStudentDocumentDetailBinding? = null
    var downloadID: Long = 0
    var preference : Preference?= null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentDocumentDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            preference = Preference().getInstance(context!!)
            context!!.registerReceiver(
                onDownloadComplete,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )
            binding!!.tvDocumentName.text = list!!.get(position)!!.getTitle()
            binding!!.tvFileName.text = list!!.get(position)!!.getImageName()

            binding!!.ivDocumentDetailDownload.setOnClickListener(object :View.OnClickListener{
                override fun onClick(p0: View?) {
                    val permissions = arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    val rationale = "Please provide permission so that you can ..."
                    val options = Permissions.Options()
                        .setRationaleDialogTitle("Info")
                        .setSettingsDialogTitle("Warning")
                    Permissions.check(
                        context,
                        permissions,
                        rationale,
                        options,
                        object : PermissionHandler() {
                            override fun onGranted() {
                                var imageUrl = preference!!.getString(Constants.Preference.IMAGESURL_STUDENT)
                                imageUrl += list!!.get(position)!!.getDoc()
                                downloadID = Utils.startDownload(context!!,imageUrl!!,imageUrl)
                            }
                            override fun onDenied(
                                context: Context,
                                deniedPermissions: java.util.ArrayList<String>
                            ) {
                                Toast.makeText(context, context.getString(R.string.permission_denied), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        })
                }

            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentDocumentDetailBinding) : RecyclerView.ViewHolder(binding.root)

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