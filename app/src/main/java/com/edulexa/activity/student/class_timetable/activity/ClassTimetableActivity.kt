package com.edulexa.activity.student.class_timetable.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.edulexa.R
import com.edulexa.activity.student.class_timetable.adapter.ClassTimeTableAdapter
import com.edulexa.activity.student.class_timetable.adapter.ViewpagerClassTimetableAdapter
import com.edulexa.activity.student.class_timetable.model.ClassTimetableModel
import com.edulexa.activity.student.class_timetable.model.DayWiseListModel
import com.edulexa.activity.student.fee.model.FeeModel
import com.edulexa.activity.student.gallery.adapter.ViewpagerGalleryAdapter
import com.edulexa.databinding.ActivityClassTimetableStudentBinding
import org.json.JSONArray
import org.json.JSONObject

class ClassTimetableActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityClassTimetableStudentBinding? = null
    var classTimeTableList : List<ClassTimetableModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassTimetableStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        getClassTimetableData()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getClassTimetableData(){
        val responseStr = "{\"class_id\":\"1\",\"section_id\":\"2\",\"status\":\"200\",\"result_array\":{\"Monday\":[{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"8:00 PM\",\"end_time\":\"9:00 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"1:00 PM\",\"end_time\":\"2:00 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"Mathematics\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"2:40 PM\",\"end_time\":\"3:40 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Kamlesh\",\"surname\":\"Dabde\",\"start_time\":\"6:15 AM\",\"end_time\":\"8:15 AM\",\"teacherName\":\"Kamlesh Dabde\"}],\"Tuesday\":[{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"5:00 PM\",\"end_time\":\"6:00 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"8:00 PM\",\"end_time\":\"9:00 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"1:00 PM\",\"end_time\":\"2:00 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"2:40 PM\",\"end_time\":\"3:40 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"nitin\",\"surname\":\"kumar\",\"start_time\":\"6:15 AM\",\"end_time\":\"8:15 AM\",\"teacherName\":\"nitin kumar\"}],\"Wednesday\":[{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"5:00 PM\",\"end_time\":\"6:00 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"8:00 PM\",\"end_time\":\"9:00 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"1:00 PM\",\"end_time\":\"2:00 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"2:40 PM\",\"end_time\":\"3:40 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"nitin\",\"surname\":\"kumar\",\"start_time\":\"6:15 AM\",\"end_time\":\"8:15 AM\",\"teacherName\":\"nitin kumar\"}],\"Thursday\":[{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"5:00 PM\",\"end_time\":\"6:00 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"8:00 PM\",\"end_time\":\"9:00 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"1:00 PM\",\"end_time\":\"2:00 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"2:40 PM\",\"end_time\":\"3:40 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"nitin\",\"surname\":\"kumar\",\"start_time\":\"6:15 AM\",\"end_time\":\"8:15 AM\",\"teacherName\":\"nitin kumar\"}],\"Friday\":[{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"9:50 AM\",\"end_time\":\"9:55 AM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"10:10 AM\",\"end_time\":\"10:50 AM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"8:00 PM\",\"end_time\":\"9:00 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"1:00 PM\",\"end_time\":\"2:00 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"2:40 PM\",\"end_time\":\"3:40 PM\",\"teacherName\":\"Abhay Sing\"}],\"Saturday\":[{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"9:50 AM\",\"end_time\":\"9:55 AM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"10:10 AM\",\"end_time\":\"10:50 AM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"8:00 PM\",\"end_time\":\"9:00 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"1:00 PM\",\"end_time\":\"2:00 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"2:40 PM\",\"end_time\":\"3:40 PM\",\"teacherName\":\"Abhay Sing\"}],\"Sunday\":[{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"8:00 PM\",\"end_time\":\"9:00 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"1:00 PM\",\"end_time\":\"2:00 PM\",\"teacherName\":\"Abhay Sing\"},{\"subject\":\"English\",\"name\":\"Abhay\",\"surname\":\"Sing\",\"start_time\":\"2:40 PM\",\"end_time\":\"3:40 PM\",\"teacherName\":\"Abhay Sing\"}]}}"
        try {
            val jsonObjectResponse = JSONObject(responseStr)
            val statusCode = jsonObjectResponse.optInt("status")
            if (statusCode == 200){
                val resultJsonObj = jsonObjectResponse.optJSONObject("result_array")
                if (resultJsonObj != null){
                    (classTimeTableList as ArrayList<ClassTimetableModel>).clear()
                    val iterator: Iterator<String> = resultJsonObj.keys()
                    while (iterator.hasNext()){
                        val classTimetableModel = ClassTimetableModel()
                        val keyName = iterator.next()
                        val dayWiseJsonArr = JSONArray(resultJsonObj.optString(keyName))
                        val dayWiseList : List<DayWiseListModel> = ArrayList()
                        if (dayWiseJsonArr.length() > 0){
                            for (i in 0..dayWiseJsonArr.length()-1){
                                val dayWiseJsonObj = dayWiseJsonArr.optJSONObject(i)
                                val dayWiseListModel = DayWiseListModel()
                                dayWiseListModel.setSubject(dayWiseJsonObj.optString("subject"))
                                dayWiseListModel.setName(dayWiseJsonObj.optString("name"))
                                dayWiseListModel.setSurName(dayWiseJsonObj.optString("surname"))
                                dayWiseListModel.setStartTime(dayWiseJsonObj.optString("start_time"))
                                dayWiseListModel.setEndTime(dayWiseJsonObj.optString("end_time"))
                                dayWiseListModel.setTeacherName(dayWiseJsonObj.optString("teacherName"))
                                (dayWiseList as ArrayList<DayWiseListModel>).add(dayWiseListModel)
                            }
                        }
                        classTimetableModel.setDayName(keyName)
                        classTimetableModel.setDayWiseList(dayWiseList)
                        (classTimeTableList as ArrayList<ClassTimetableModel>).add(classTimetableModel)
                    }
                    if (classTimeTableList.size > 0){
                        (classTimeTableList as ArrayList<ClassTimetableModel>).get(0).setSelectedFlag(true)
                        binding!!.studentClassTimetableDaysRecycler.layoutManager = LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false)
                        binding!!.studentClassTimetableDaysRecycler.adapter = ClassTimeTableAdapter(mActivity!!,classTimeTableList)
                        setUpViewPager()
                    }
                }
            }else{

            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun setUpViewPager(){
        binding!!.classTimetableStudentViewPager.adapter =
            ViewpagerClassTimetableAdapter(supportFragmentManager, classTimeTableList,classTimeTableList.size)
        binding!!.classTimetableStudentViewPager.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                resetTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun resetTab(position : Int){
        for (model in classTimeTableList){
            model.setSelectedFlag(false)
        }
        classTimeTableList.get(position).setSelectedFlag(true)
        binding!!.studentClassTimetableDaysRecycler.adapter = ClassTimeTableAdapter(mActivity!!,classTimeTableList)
        binding!!.studentClassTimetableDaysRecycler.smoothScrollToPosition(position)
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }

}