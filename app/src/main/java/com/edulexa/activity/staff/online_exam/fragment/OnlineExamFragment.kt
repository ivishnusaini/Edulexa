package com.edulexa.activity.staff.online_exam.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edulexa.databinding.FragmentOnlineExamStaffBinding

class OnlineExamFragment : Fragment() {
    var binding: FragmentOnlineExamStaffBinding? = null
    var mActivity: Activity? = null
    var rootView: View? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragment: OnlineExamFragment? = null
        fun newInstance(): OnlineExamFragment? {
            fragment = OnlineExamFragment()
            return fragment
        }

        fun getInstance(): OnlineExamFragment? {
            return if (fragment == null) OnlineExamFragment() else fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnlineExamStaffBinding.inflate(layoutInflater)
        rootView = binding!!.root
        init()
        return rootView
    }
    private fun init(){
        mActivity = activity
    }
}