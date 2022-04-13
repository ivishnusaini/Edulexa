package com.edulexa.activity.student.gallery.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
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
                .inflate(R.layout.item_student_gallery_video, parent, false)
            return ViewHolderVideo(itemView)
        } else if (type.equals("image")) {
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_student_gallery_image, parent, false)
            return ViewHolderImage(itemView)
        } else if (type.equals("document")) {
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_student_gallery_document, parent, false)
            return ViewHolderDocument(itemView)
        } else {
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_student_gallery_link, parent, false)
            return ViewHolderLink(itemView)
        }

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (type.equals("all")) {
                val viewHolderAll = viewHolder as ViewHolderAll
                when (list!!.get(position).getGalleryType()) {
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
            } else if (type.equals("video")) {
                val viewHolderVideo = viewHolder as ViewHolderVideo
                viewHolderVideo.videoLay!!.visibility = View.VISIBLE
            } else if (type.equals("image")) {
                val viewHolderImage = viewHolder as ViewHolderImage
                viewHolderImage.imageLay!!.visibility = View.VISIBLE
            } else if (type.equals("document")) {
                val viewHolderDocument = viewHolder as ViewHolderDocument
                viewHolderDocument.documentLay!!.visibility = View.VISIBLE
            } else {

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    override fun getItemViewType(position: Int): Int {
        return position
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
        var videoLay: LinearLayout? = null

        init {
            videoLay = itemView.findViewById(R.id.gallery_student_video_lay)
        }
    }

    class ViewHolderImage(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageLay: LinearLayout? = null

        init {
            imageLay = itemView.findViewById(R.id.gallery_student_image_lay)
        }
    }

    class ViewHolderDocument(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var documentLay: LinearLayout? = null

        init {
            documentLay = itemView.findViewById(R.id.gallery_student_document_lay)
        }
    }

    class ViewHolderLink(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {

        }
    }
}