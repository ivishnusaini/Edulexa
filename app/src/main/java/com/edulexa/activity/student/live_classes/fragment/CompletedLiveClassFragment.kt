package com.edulexa.activity.student.live_classes.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.fee.model.FeeDetail
import com.edulexa.activity.student.fee.model.FeeModel
import com.edulexa.activity.student.live_classes.adapter.CompletedLiveClassAdapter
import com.edulexa.activity.student.live_classes.adapter.OngoingLiveClassAdapter
import com.edulexa.activity.student.live_classes.model.DatumLiveClass
import com.edulexa.activity.student.live_classes.model.LiveClassResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.FragmentUpcomingLiveClassesStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class CompletedLiveClassFragment : Fragment() {
    var binding: FragmentUpcomingLiveClassesStudentBinding? = null
    var mActivity: Activity? = null
    var rootView: View? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragment: CompletedLiveClassFragment? = null
        fun newInstance(): CompletedLiveClassFragment? {
            fragment = CompletedLiveClassFragment()
            return fragment
        }

        fun getInstance(): CompletedLiveClassFragment? {
            return if (fragment == null) CompletedLiveClassFragment() else fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpcomingLiveClassesStudentBinding.inflate(layoutInflater)
        rootView = binding!!.root
        init()
        return rootView
    }
    private fun init(){
        mActivity = activity
        setUpData()
    }

    private fun setUpData(){
        if (Constants.AppSaveData.listCompleted != null){
            if (Constants.AppSaveData.listCompleted!!.size > 0){
                binding!!.liveClassesRecycler.visibility = View.VISIBLE
                binding!!.tvLiveClassNoData.visibility = View.GONE
                binding!!.liveClassesRecycler.layoutManager = LinearLayoutManager(mActivity!!,
                    RecyclerView.VERTICAL,false)
                binding!!.liveClassesRecycler.adapter = CompletedLiveClassAdapter(mActivity!!,Constants.AppSaveData.listCompleted)
            }else{
                binding!!.liveClassesRecycler.visibility = View.GONE
                binding!!.tvLiveClassNoData.visibility = View.VISIBLE
            }
        }else{
            binding!!.liveClassesRecycler.visibility = View.GONE
            binding!!.tvLiveClassNoData.visibility = View.VISIBLE
        }
    }

}