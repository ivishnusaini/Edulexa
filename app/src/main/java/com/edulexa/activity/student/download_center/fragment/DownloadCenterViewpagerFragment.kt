package com.edulexa.activity.student.download_center.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edulexa.activity.student.class_timetable.model.DayWiseListModel
import com.edulexa.activity.student.download_center.model.DownloadDetailModel
import com.edulexa.databinding.FragmentDownloadCenterStudentBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DownloadCenterViewpagerFragment : Fragment() {
    var binding: FragmentDownloadCenterStudentBinding? = null
    var mActivity: Activity? = null
    var rootView: View? = null
    private var typeWiseList: List<DownloadDetailModel>? = ArrayList()
    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragment: DownloadCenterViewpagerFragment? = null
        fun newInstance(list : List<DownloadDetailModel>): DownloadCenterViewpagerFragment? {
            fragment = DownloadCenterViewpagerFragment()
            val args = Bundle()
            args.putString("list", Gson().toJson(list))
            fragment!!.setArguments(args)
            return fragment
        }

        fun getInstance(): DownloadCenterViewpagerFragment? {
            return if (fragment == null) DownloadCenterViewpagerFragment() else fragment
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gson = Gson()
        val listStr = requireArguments().getString("list")!!
        typeWiseList = gson.fromJson(listStr, object : TypeToken<List<DownloadDetailModel?>?>() {}.type)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadCenterStudentBinding.inflate(layoutInflater)
        rootView = binding!!.root
        init()
        return rootView
    }
    private fun init(){
        mActivity = activity
    }
}