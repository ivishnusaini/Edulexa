package com.edulexa.activity.staff.dashboard.activity

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.dashboard.adapter.DashboardStaffAdapter
import com.edulexa.activity.staff.dashboard.model.DashboardModel
import com.edulexa.databinding.ActivityDashboardStaffBinding
import com.edulexa.support.Utils

class DashboardStaffActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityDashboardStaffBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    override fun onBackPressed() {
        exitAppPopup()
    }

    private fun init() {
        mActivity = this
        setUpclickListener()
        setUpDashboardData()
    }

    private fun setUpclickListener() {
        binding!!.tvLogout.setOnClickListener(this)
    }

    private fun setUpDashboardData() {
        binding!!.recyclerView.layoutManager =
            GridLayoutManager(mActivity, 3, RecyclerView.VERTICAL, false)
        binding!!.recyclerView.adapter = DashboardStaffAdapter(mActivity!!,getModuleList())
    }

    private fun getModuleList() : List<DashboardModel>{
        val moduleList : List<DashboardModel> = ArrayList()
        (moduleList as ArrayList<DashboardModel>).add(DashboardModel(R.drawable.student_profile_staff,getString(R.string.dashboard_staff_student_profile)))
        moduleList.add(DashboardModel(R.drawable.custom_lesson_staff,getString(R.string.dashboard_staff_custom_lesson_plan)))
        moduleList.add(DashboardModel(R.drawable.online_exam_staff,getString(R.string.dashboard_staff_online_exam)))
        moduleList.add(DashboardModel(R.drawable.homework_staff,getString(R.string.dashboard_staff_homework)))
        moduleList.add(DashboardModel(R.drawable.lesson_plan_staff,getString(R.string.dashboard_staff_lesson_plan)))
        moduleList.add(DashboardModel(R.drawable.circular_staff,getString(R.string.dashboard_staff_circular)))
        moduleList.add(DashboardModel(R.drawable.events_staff,getString(R.string.dashboard_staff_events)))
        moduleList.add(DashboardModel(R.drawable.gallery_staff,getString(R.string.dashboard_staff_gallery)))
        moduleList.add(DashboardModel(R.drawable.school_family_staff,getString(R.string.dashboard_staff_school_family)))
        moduleList.add(DashboardModel(R.drawable.syllabus_status_staff,getString(R.string.dashboard_staff_syllabus_status)))
        moduleList.add(DashboardModel(R.drawable.create_exam_staff,getString(R.string.dashboard_staff_create_exam)))
        moduleList.add(DashboardModel(R.drawable.student_admission_staff,getString(R.string.dashboard_staff_student_admission)))
        moduleList.add(DashboardModel(R.drawable.attendance_staff,getString(R.string.dashboard_staff_attendance)))
        moduleList.add(DashboardModel(R.drawable.add_lesson_staff,getString(R.string.dashboard_staff_add_lesson)))
        moduleList.add(DashboardModel(R.drawable.add_topic_staff,getString(R.string.dashboard_staff_add_topic)))
        moduleList.add(DashboardModel(R.drawable.approve_leave_staff,getString(R.string.dashboard_staff_approve_leave)))
        moduleList.add(DashboardModel(R.drawable.apply_leave_staff,getString(R.string.dashboard_staff_apply_leave)))
        moduleList.add(DashboardModel(R.drawable.view_all_staff,getString(R.string.dashboard_staff_view_all)))
        moduleList.add(DashboardModel(R.drawable.staff_meeting_staff,getString(R.string.dashboard_staff_staff_meeting)))
        moduleList.add(DashboardModel(R.drawable.live_class_staff,getString(R.string.dashboard_staff_live_classes)))
        return moduleList
    }

    fun exitAppPopup() {
        try {
            val dialog = Dialog(mActivity!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_exit)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)
            val tvMessage = dialog.findViewById<TextView>(R.id.tv_message)
            tvMessage.text = getString(R.string.exit_message)
            val cvCancel: CardView = dialog.findViewById(R.id.cv_cancel)
            val cvOk: CardView = dialog.findViewById(R.id.cv_ok)
            cvCancel.setOnClickListener { v: View? -> dialog.dismiss() }
            cvOk.setOnClickListener { v: View? ->
                Utils.hideKeyboard(mActivity!!)
                finishAffinity()
                dialog.dismiss()
            }
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun logoutPopup() {
        try {
            val dialog = Dialog(mActivity!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_exit)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)
            val tvMessage = dialog.findViewById<TextView>(R.id.tv_message)
            tvMessage.text = getString(R.string.exit_message)
            val cvCancel: CardView = dialog.findViewById(R.id.cv_cancel)
            val cvOk: CardView = dialog.findViewById(R.id.cv_ok)
            cvCancel.setOnClickListener { v: View? -> dialog.dismiss() }
            cvOk.setOnClickListener { v: View? ->
                Utils.hideKeyboard(mActivity!!)
                finishAffinity()
                dialog.dismiss()
            }
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.tv_logout)
            logoutPopup()
    }
}