package com.edulexa.activity.student.gallery.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.gallery.adapter.GalleryAlbumAdapter
import com.edulexa.activity.student.noticeboard.adapter.NoticeBoardAdapter
import com.edulexa.databinding.ActivityGalleryStudentBinding
import com.edulexa.databinding.ActivityNoticeboardStudentBinding

class GalleryStudentActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityGalleryStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        setUpExamList()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun setUpExamList() {
        binding!!.recyclerViewGallery.layoutManager = GridLayoutManager(
            mActivity!!,2,
            RecyclerView.VERTICAL, false
        )
        binding!!.recyclerViewGallery.adapter = GalleryAlbumAdapter(mActivity!!)
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}