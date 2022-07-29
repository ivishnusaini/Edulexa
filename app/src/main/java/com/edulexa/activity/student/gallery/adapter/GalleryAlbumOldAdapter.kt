package com.edulexa.activity.student.gallery.adapter

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
import com.edulexa.databinding.ItemStudentGalleryAlbumBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions

class GalleryAlbumOldAdapter(context: Activity, list : List<DatumFolderDetail?>?) :
    RecyclerView.Adapter<GalleryAlbumOldAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DatumFolderDetail?>? = null
    var binding : ItemStudentGalleryAlbumBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentGalleryAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentGalleryAlbumBinding) : RecyclerView.ViewHolder(binding.root)

}