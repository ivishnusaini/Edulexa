package com.edulexa.activity.student.calendar.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.calendar.adapter.CalendarListAdapter
import com.edulexa.activity.student.calendar.adapter.MonthAdapter
import com.edulexa.activity.student.calendar.model.CalendarListResponse
import com.edulexa.activity.student.calendar.model.MonthModel
import com.edulexa.activity.student.custom_lesson_plan.adapter.CustomLessonPlanAdapter
import com.edulexa.activity.student.custom_lesson_plan.model.CustomLessonPlanResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityCalendarStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class CalendarStudentActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityCalendarStudentBinding? = null
    val calendarGlobal: Calendar = Calendar.getInstance()
    var currentDateGlobal = 0
    var currentMonthGlobal = 0
    private var monthList: List<MonthModel>? = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        setUpCurrentMonthData()
        setUpMonthData()
        setUpAttendanceList()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.ivPrevious.setOnClickListener(this)
        binding!!.ivNext.setOnClickListener(this)
    }

    private fun setUpCurrentMonthData() {
        calendarGlobal.set(Calendar.HOUR_OF_DAY, 0)
        calendarGlobal.clear(Calendar.MINUTE)
        calendarGlobal.clear(Calendar.SECOND)
        calendarGlobal.clear(Calendar.MILLISECOND)
        calendarGlobal.set(Calendar.DAY_OF_MONTH, 1)
        currentDateGlobal = Utils.getCurrentDayNumber()
        currentMonthGlobal = Utils.getCurrentMonth()
    }

    private fun setCurrentDayOfWeek(currentDate: String) {
        binding!!.sunLay.setBackgroundResource(0)
        binding!!.tvSun.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.monLay.setBackgroundResource(0)
        binding!!.tvMon.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.tueLay.setBackgroundResource(0)
        binding!!.tvTue.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.wedLay.setBackgroundResource(0)
        binding!!.tvWed.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.thuLay.setBackgroundResource(0)
        binding!!.tvThu.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.friLay.setBackgroundResource(0)
        binding!!.tvFri.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.satLay.setBackgroundResource(0)
        binding!!.tvSat.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        val currentDayOfWeek = Utils.getCurrentDayOfWeek(currentDate)
        when (currentDayOfWeek.lowercase()) {
            "sunday" -> {
                binding!!.sunLay.setBackgroundResource(R.drawable.bg_button_25)
                binding!!.tvSun.setTextColor(ContextCompat.getColor(mActivity!!, R.color.white))
            }
            "monday" -> {
                binding!!.monLay.setBackgroundResource(R.drawable.bg_button_25)
                binding!!.tvMon.setTextColor(ContextCompat.getColor(mActivity!!, R.color.white))
            }
            "tuesday" -> {
                binding!!.tueLay.setBackgroundResource(R.drawable.bg_button_25)
                binding!!.tvTue.setTextColor(ContextCompat.getColor(mActivity!!, R.color.white))
            }
            "wednesday" -> {
                binding!!.wedLay.setBackgroundResource(R.drawable.bg_button_25)
                binding!!.tvWed.setTextColor(ContextCompat.getColor(mActivity!!, R.color.white))
            }
            "thursday" -> {
                binding!!.thuLay.setBackgroundResource(R.drawable.bg_button_25)
                binding!!.tvThu.setTextColor(ContextCompat.getColor(mActivity!!, R.color.white))
            }
            "friday" -> {
                binding!!.friLay.setBackgroundResource(R.drawable.bg_button_25)
                binding!!.tvFri.setTextColor(ContextCompat.getColor(mActivity!!, R.color.white))
            }
            "saturday" -> {
                binding!!.satLay.setBackgroundResource(R.drawable.bg_button_25)
                binding!!.tvSat.setTextColor(ContextCompat.getColor(mActivity!!, R.color.white))
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setUpMonthData() {
        val date: Date?
        val formatter = SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy")
        try {
            date = formatter.parse(calendarGlobal.getTime().toString())
            val formateDate = SimpleDateFormat("EEE yyyy-MM-dd").format(date!!)
            val tokenizerDayName = StringTokenizer(formateDate, " ")
            val dateNameStr = tokenizerDayName.nextToken()
            var completeDateStr = tokenizerDayName.nextToken()
            (monthList as ArrayList<MonthModel>).clear()
            when (dateNameStr.lowercase()) {
                "mon" -> {
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                }
                "tue" -> {
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                }
                "wed" -> {
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                }
                "thu" -> {
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                }
                "fri" -> {
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                }
                "sat" -> {
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            "",
                            false,
                            false,
                            false,
                            completeDateStr
                        )
                    )
                }

            }

            val tokenizerWithDayName = StringTokenizer(formateDate, " ")
            val dayName = tokenizerWithDayName.nextToken()
            val currentDate = tokenizerWithDayName.nextToken()
            val tokenizer = StringTokenizer(currentDate, "-")
            val year = tokenizer.nextToken()
            val month = tokenizer.nextToken()
            val currentDateOfMonth = tokenizer.nextToken()

            binding!!.tvMonthYear.setText(
                getString(
                    R.string.concat_string_with_text_format,
                    Utils.getMonthNameFromMonthNo((month).toInt()),
                    " ",
                    year
                )
            )

            val monthMinusOne = (month).toInt() - 1
            if (monthMinusOne == currentMonthGlobal)
                currentDateGlobal = Utils.getCurrentDayNumber()
            else currentDateGlobal = 0
            val numberOfDays = Utils.getNumberOfDaysInMonth((year).toInt(), monthMinusOne)
            for (i in 1..numberOfDays) {
                completeDateStr = year + "-" + month + "-" + i
                if (i == (currentDateOfMonth).toInt()) {
                    var flag = false
                    if (i == currentDateGlobal)
                        flag = true
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            i.toString(),
                            true,
                            true,
                            flag, completeDateStr
                        )
                    )
                } else {

                    var flag = false
                    if (i == currentDateGlobal)
                        flag = true
                    (monthList as ArrayList<MonthModel>).add(
                        MonthModel(
                            i.toString(),
                            false,
                            true,
                            flag, completeDateStr
                        )
                    )
                }
            }
            binding!!.recyclerViewMonth.layoutManager =
                GridLayoutManager(mActivity!!, 7, RecyclerView.VERTICAL, false)
            binding!!.recyclerViewMonth.adapter = MonthAdapter(mActivity!!, monthList)
            setCurrentDayOfWeek(currentDate)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateList(list: List<MonthModel>, completeDate: String) {
        binding!!.recyclerViewMonth.adapter = MonthAdapter(mActivity!!, list)
        setCurrentDayOfWeek(completeDate)
    }

    private fun getNextMonth() {
        calendarGlobal.add(Calendar.MONTH, 1)
        setUpMonthData()
        setUpAttendanceList()
    }

    private fun getPreviousMonth() {
        calendarGlobal.add(Calendar.MONTH, -1)
        setUpMonthData()
        setUpAttendanceList()
    }

    private fun getMonthNo() : Int{
        return calendarGlobal.get(Calendar.MONTH) + 1
    }
    private fun getYear() : Int{
        return calendarGlobal.get(Calendar.YEAR)
    }

    private fun setUpAttendanceList() {
        if (Utils.isNetworkAvailable(mActivity!!)){
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId = Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent = APIClientStudent.getRetroFitClientWithNewKeyHeader(mActivity!!, accessToken,branchId,userId).create(
                ApiInterfaceStudent::class.java)

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStudent.STUDENT_ID, Utils.getStudentId(mActivity!!))
            jsonObject.put(Constants.ParamsStudent.MONTH,getMonthNo())
            jsonObject.put(Constants.ParamsStudent.YEAR,getYear())

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/getPublicEvents")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getCalendarList(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try{
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()){
                            val jsonObjectResponse = JSONObject(responseStr)
                            val statusCode = jsonObjectResponse.optInt("status")
                            val message = jsonObjectResponse.optString("message")
                            if (statusCode == 200){
                                val modelResponse = Utils.getObject(responseStr, CalendarListResponse::class.java) as CalendarListResponse
                                if (modelResponse.getData() != null && modelResponse.getData()!!.size > 0){
                                    binding!!.recyclerViewAttendance.visibility = View.VISIBLE
                                    binding!!.tvStudentCalendarNoEvent.visibility = View.GONE
                                    binding!!.recyclerViewAttendance.layoutManager = LinearLayoutManager(
                                        mActivity!!,
                                        RecyclerView.VERTICAL, false
                                    )
                                    binding!!.recyclerViewAttendance.adapter = CalendarListAdapter(mActivity!!,modelResponse.getData())
                                }else{
                                    binding!!.recyclerViewAttendance.visibility = View.GONE
                                    binding!!.tvStudentCalendarNoEvent.visibility = View.VISIBLE
                                }
                            }else {
                                Utils.showToast(mActivity!!,message)
                                binding!!.recyclerViewAttendance.visibility = View.GONE
                                binding!!.tvStudentCalendarNoEvent.visibility = View.VISIBLE
                            }
                        }else {
                            Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                            binding!!.recyclerViewAttendance.visibility = View.GONE
                            binding!!.tvStudentCalendarNoEvent.visibility = View.VISIBLE
                        }
                    }catch (e : Exception){
                        e.printStackTrace()
                        Utils.showToast(mActivity!!, e.localizedMessage!!)
                        binding!!.recyclerViewAttendance.visibility = View.GONE
                        binding!!.tvStudentCalendarNoEvent.visibility = View.VISIBLE
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.recyclerViewAttendance.visibility = View.GONE
                    binding!!.tvStudentCalendarNoEvent.visibility = View.VISIBLE
                }
            })
        }else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.iv_previous)
            getPreviousMonth()
        else if (id == R.id.iv_next)
            getNextMonth()
    }
}