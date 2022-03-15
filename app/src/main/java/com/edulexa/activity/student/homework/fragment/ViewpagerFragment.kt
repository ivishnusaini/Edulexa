package com.edulexa.activity.student.homework.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edulexa.databinding.FragmentHomeworkViewpagerStudentBinding

class ViewpagerFragment : Fragment() {
    var binding: FragmentHomeworkViewpagerStudentBinding? = null
    var mActivity: Activity? = null
    var rootView: View? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragment: ViewpagerFragment? = null
        fun newInstance(): ViewpagerFragment? {
            fragment = ViewpagerFragment()
            return fragment
        }

        fun getInstance(): ViewpagerFragment? {
            return if (fragment == null) ViewpagerFragment() else fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeworkViewpagerStudentBinding.inflate(layoutInflater)
        rootView = binding!!.root
        init()
        return rootView
    }
    private fun init(){
        mActivity = activity
    }
}