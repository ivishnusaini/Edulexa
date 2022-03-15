package com.edulexa.activity.student.dashboard.activity

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
import com.edulexa.activity.student.dashboard.adapter.DashboardStudentAdapter
import com.edulexa.databinding.ActivityDashboardStaffBinding
import com.edulexa.databinding.ActivityDashboardStudentBinding
import com.edulexa.support.Utils

class DashboardStudentActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityDashboardStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardStudentBinding.inflate(layoutInflater)
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
        binding!!.recyclerView.adapter = DashboardStudentAdapter(mActivity!!)
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