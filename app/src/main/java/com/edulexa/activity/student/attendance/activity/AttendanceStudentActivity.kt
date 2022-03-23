package com.edulexa.activity.student.attendance.activity

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
import com.edulexa.activity.student.attendance.adapter.AttendanceListAdapter
import com.edulexa.activity.student.attendance.adapter.MonthAdapter
import com.edulexa.activity.student.attendance.model.MonthModel
import com.edulexa.activity.student.fee.adapter.FeeTypeAdapter
import com.edulexa.databinding.ActivityAttendanceStudentBinding
import com.edulexa.support.Utils
import java.text.SimpleDateFormat
import java.util.*

class AttendanceStudentActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityAttendanceStudentBinding? = null
    val calendarGlobal: Calendar = Calendar.getInstance()
    var currentDateGlobal = 0
    var currentMonthGlobal = 0
    private var monthList: List<MonthModel>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceStudentBinding.inflate(layoutInflater)
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

            binding!!.tvMonthYear.setText(getString(R.string.concat_string_with_text_format,Utils.getMonthNameFromMonthNo((month).toInt())," ",year))

            val monthMinusOne = (month).toInt() - 1
            if (monthMinusOne == currentMonthGlobal)
                currentDateGlobal = Utils.getCurrentDayNumber()
            else currentDateGlobal = 0
            val numberOfDays = Utils.getNumberOfDaysInMonth((year).toInt(), monthMinusOne)
            for (i in 1..numberOfDays) {
                completeDateStr = year + "-"+month+"-"+i
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

    fun updateList(list: List<MonthModel>,completeDate : String) {
        binding!!.recyclerViewMonth.adapter = MonthAdapter(mActivity!!, list)
        setCurrentDayOfWeek(completeDate)
    }

    private fun getNextMonth() {
        calendarGlobal.add(Calendar.MONTH, 1)
        setUpMonthData()
    }

    private fun getPreviousMonth() {
        calendarGlobal.add(Calendar.MONTH, -1)
        setUpMonthData()
    }

    private fun setUpAttendanceList(){
        binding!!.recyclerViewAttendance.layoutManager = LinearLayoutManager(mActivity!!,
            RecyclerView.VERTICAL,false)
        binding!!.recyclerViewAttendance.adapter = AttendanceListAdapter(mActivity!!)
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