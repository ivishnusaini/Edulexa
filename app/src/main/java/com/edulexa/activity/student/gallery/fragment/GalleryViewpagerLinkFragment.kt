package com.edulexa.activity.student.gallery.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.activity.student.gallery.adapter.GalleryAlbumAdapter
import com.edulexa.activity.student.gallery.model.GalleryTypeModel
import com.edulexa.databinding.FragmentGalleryViewpagerStudentBinding

class GalleryViewpagerLinkFragment : Fragment() {
    var binding: FragmentGalleryViewpagerStudentBinding? = null
    var mActivity: Activity? = null
    var rootView: View? = null
    private var galleryList: List<GalleryTypeModel>? = ArrayList()

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragment: GalleryViewpagerLinkFragment? = null
        fun newInstance(): GalleryViewpagerLinkFragment? {
            fragment = GalleryViewpagerLinkFragment()
            return fragment
        }

        fun getInstance(): GalleryViewpagerLinkFragment? {
            return if (fragment == null) GalleryViewpagerLinkFragment() else fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryViewpagerStudentBinding.inflate(layoutInflater)
        rootView = binding!!.root
        init()
        return rootView
    }

    private fun init() {
        mActivity = activity
        setUpFeeList()
    }

    fun setUpFeeList() {
        (galleryList as ArrayList<GalleryTypeModel>).clear()
        (galleryList as ArrayList<GalleryTypeModel>).add(GalleryTypeModel("document"))
        (galleryList as ArrayList<GalleryTypeModel>).add(GalleryTypeModel("video"))
        (galleryList as ArrayList<GalleryTypeModel>).add(GalleryTypeModel("document"))
        (galleryList as ArrayList<GalleryTypeModel>).add(GalleryTypeModel("document"))
        (galleryList as ArrayList<GalleryTypeModel>).add(GalleryTypeModel("document"))
        (galleryList as ArrayList<GalleryTypeModel>).add(GalleryTypeModel("document"))
        (galleryList as ArrayList<GalleryTypeModel>).add(GalleryTypeModel("video"))
        (galleryList as ArrayList<GalleryTypeModel>).add(GalleryTypeModel("image"))
        (galleryList as ArrayList<GalleryTypeModel>).add(GalleryTypeModel("image"))
        (galleryList as ArrayList<GalleryTypeModel>).add(GalleryTypeModel("image"))
        (galleryList as ArrayList<GalleryTypeModel>).add(GalleryTypeModel("video"))

        binding!!.galleryStudentRecyclerView.layoutManager = LinearLayoutManager(
            mActivity!!,
            RecyclerView.VERTICAL, false
        )
        binding!!.galleryStudentRecyclerView.adapter =
            GalleryAlbumAdapter(mActivity!!, galleryList!!, "link")
    }

}