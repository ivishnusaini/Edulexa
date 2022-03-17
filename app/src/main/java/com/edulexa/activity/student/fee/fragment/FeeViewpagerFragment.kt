package com.edulexa.activity.student.fee.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.activity.student.fee.adapter.FeeTypeAdapter
import com.edulexa.databinding.FragmentFeeViewpagerStudentBinding
import com.edulexa.databinding.FragmentHomeworkViewpagerStudentBinding

class FeeViewpagerFragment : Fragment() {
    var binding: FragmentFeeViewpagerStudentBinding? = null
    var mActivity: Activity? = null
    var rootView: View? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragment: FeeViewpagerFragment? = null
        fun newInstance(): FeeViewpagerFragment? {
            fragment = FeeViewpagerFragment()
            return fragment
        }

        fun getInstance(): FeeViewpagerFragment? {
            return if (fragment == null) FeeViewpagerFragment() else fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeeViewpagerStudentBinding.inflate(layoutInflater)
        rootView = binding!!.root
        init()
        return rootView
    }
    private fun init(){
        mActivity = activity
        setUpFeeTypeAdapter()
    }
    private fun setUpFeeTypeAdapter(){
        binding!!.recyclerViewFeeType.layoutManager = LinearLayoutManager(mActivity!!,RecyclerView.HORIZONTAL,false)
        binding!!.recyclerViewFeeType.adapter = FeeTypeAdapter(mActivity!!)
    }
}