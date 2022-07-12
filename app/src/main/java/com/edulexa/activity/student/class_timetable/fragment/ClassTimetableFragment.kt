package com.edulexa.activity.student.class_timetable.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.activity.student.class_timetable.adapter.ClassTimeTableDayWiseAdapter
import com.edulexa.activity.student.class_timetable.model.DayWiseListModel
import com.edulexa.databinding.FragmentClassTimetableViewpagerStudentBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ClassTimetableFragment : Fragment(){
    var binding: FragmentClassTimetableViewpagerStudentBinding? = null
    var mActivity: Activity? = null
    var rootView: View? = null
    private var dayWiseList: List<DayWiseListModel>? = ArrayList()

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragment: ClassTimetableFragment? = null
        fun newInstance(list : List<DayWiseListModel>): ClassTimetableFragment {
            val fragment = ClassTimetableFragment()
            val args = Bundle()
            args.putString("list", Gson().toJson(list))
            fragment.setArguments(args)
            return fragment
        }

        fun getInstance(): ClassTimetableFragment? {
            return if (fragment == null) ClassTimetableFragment() else fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gson = Gson()
        val listStr = requireArguments().getString("list")!!
        dayWiseList = gson.fromJson(listStr, object : TypeToken<List<DayWiseListModel?>?>() {}.type)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClassTimetableViewpagerStudentBinding.inflate(layoutInflater)
        rootView = binding!!.root
        init()
        return rootView
    }

    private fun init(){
        mActivity = activity
        setUpDayWiseData()
    }
    private fun setUpDayWiseData(){
        if (dayWiseList!!.size > 0){
            binding!!.classTimeTableStudentRecyclerView.visibility = View.VISIBLE
            binding!!.tvClassTimetableNoData.visibility = View.GONE
            binding!!.classTimeTableStudentRecyclerView.layoutManager = LinearLayoutManager(mActivity!!,RecyclerView.VERTICAL,false)
            binding!!.classTimeTableStudentRecyclerView.adapter = ClassTimeTableDayWiseAdapter(mActivity!!,dayWiseList)
        }else{
            binding!!.classTimeTableStudentRecyclerView.visibility = View.GONE
            binding!!.tvClassTimetableNoData.visibility = View.VISIBLE
        }

    }
}