package com.edulexa.activity.student.gallery.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.gallery.activity.GalleryStudentImageZoomActivity
import com.edulexa.activity.student.gallery.model.GalleryTypeModel

class GalleryAlbumAdapter(context: Activity, list: List<GalleryTypeModel>, type: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var context: Activity? = null
    var list: List<GalleryTypeModel>? = null
    var type: String? = null

    init {
        this.context = context
        this.list = list
        this.type = type
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val itemView: View?
        if (type.equals("all")) {
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_student_gallery_all, parent, false)
            return ViewHolderAll(itemView)
        } else if (type.equals("video")) {
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_student_gallery_all, parent, false)
            return ViewHolderVideo(itemView)
        } else if (type.equals("image")) {
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_student_gallery_all, parent, false)
            return ViewHolderImage(itemView)
        } else if (type.equals("document")) {
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_student_gallery_all, parent, false)
            return ViewHolderDocument(itemView)
        } else {
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_student_gallery_all, parent, false)
            return ViewHolderLink(itemView)
        }

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (type.equals("all")) {
                val viewHolderAll = viewHolder as ViewHolderAll
                when(list!!.get(position).getGalleryType()){
                    "document" -> {
                        viewHolderAll.documentLay!!.visibility = View.VISIBLE
                    }
                    "video" -> {
                        viewHolderAll.videoLay!!.visibility = View.VISIBLE
                    }
                    "image" -> {
                        viewHolderAll.imageLay!!.visibility = View.VISIBLE
                    }
                }
                /*viewHolderAll.itemView.setOnClickListener(object : View.OnClickListener {
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
                })*/
            } else if (type.equals("video")) {

            } else if (type.equals("image")) {

            } else if (type.equals("document")) {

            } else {

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolderAll(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var documentLay: LinearLayout? = null
        var videoLay: LinearLayout? = null
        var imageLay: LinearLayout? = null
        init {
            documentLay = itemView.findViewById(R.id.gallery_student_document_lay)
            videoLay = itemView.findViewById(R.id.gallery_student_video_lay)
            imageLay = itemView.findViewById(R.id.gallery_student_image_lay)
        }
    }

    class ViewHolderVideo(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivGallery: ImageView? = null
        var tvGallery: TextView? = null

        init {

        }
    }

    class ViewHolderImage(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivGallery: ImageView? = null
        var tvGallery: TextView? = null

        init {

        }
    }

    class ViewHolderDocument(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivGallery: ImageView? = null
        var tvGallery: TextView? = null

        init {

        }
    }

    class ViewHolderLink(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivGallery: ImageView? = null
        var tvGallery: TextView? = null

        init {

        }
    }
}