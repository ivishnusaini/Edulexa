package com.edulexa.activity.student.gallery.adapter

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.gallery.activity.GalleryStudentImageZoomActivity

class GalleryAlbumAdapter(context: Activity) :
    RecyclerView.Adapter<GalleryAlbumAdapter.ViewHolder>() {
    var context: Activity? = null

    init {
        this.context = context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_student_gallery, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            viewHolder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        context!!,
                        viewHolder.ivGallery!!,
                        context!!.resources.getString(R.string.gallery_student_transition_name)
                    )
                    context!!.startActivity(
                        Intent(
                            context,
                            GalleryStudentImageZoomActivity::class.java
                        ), options.toBundle()
                    )
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return 10;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivGallery: ImageView? = null
        var tvGallery: TextView? = null

        init {
            ivGallery = itemView.findViewById(R.id.iv_gallery)
            tvGallery = itemView.findViewById(R.id.tv_gallery)
        }
    }
}