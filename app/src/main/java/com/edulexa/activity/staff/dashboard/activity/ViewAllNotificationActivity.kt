package com.edulexa.activity.staff.dashboard.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.dashboard.adapter.ViewAllNotificationAdapter
import com.edulexa.activity.staff.dashboard.model.notifications.DatumNotification
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityStudentListStaffBinding
import com.edulexa.databinding.ActivityViewAllNotificationStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils

class ViewAllNotificationActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityViewAllNotificationStaffBinding? = null
    var fromWhere = ""
    var datumNotification = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllNotificationStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        getBundleData()
        setUpData()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getBundleData() {
        try {
            val bundle = intent.extras
            fromWhere = bundle!!.getString(Constants.StaffNotification.FROM_WHERE)!!
            if (fromWhere == "detail")
                datumNotification =
                    bundle.getString(Constants.StaffNotification.DATUM_NOTIFICATION)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setUpData() {
        try {
            if (fromWhere == "detail") {
                val datumNotification = Utils.getObject(
                    datumNotification,
                    DatumNotification::class.java
                ) as DatumNotification
                val listNotification: List<DatumNotification?> = ArrayList()
                (listNotification as ArrayList<DatumNotification?>).add(datumNotification)
                if (listNotification.isNotEmpty()) {
                    binding!!.recyclerView.visibility = View.VISIBLE
                    binding!!.tvNoData.visibility = View.GONE
                    binding!!.recyclerView.layoutManager =
                        LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
                    binding!!.recyclerView.adapter =
                        ViewAllNotificationAdapter(mActivity!!, listNotification)
                } else {
                    binding!!.recyclerView.visibility = View.GONE
                    binding!!.tvNoData.visibility = View.VISIBLE
                }
            } else {
                val listNotification: List<DatumNotification?> =
                    Constants.AppSaveData.staffNotificationList!!
                if (listNotification.isNotEmpty()) {
                    binding!!.recyclerView.visibility = View.VISIBLE
                    binding!!.tvNoData.visibility = View.GONE
                    binding!!.recyclerView.layoutManager =
                        LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
                    binding!!.recyclerView.adapter =
                        ViewAllNotificationAdapter(mActivity!!, listNotification)
                } else {
                    binding!!.recyclerView.visibility = View.GONE
                    binding!!.tvNoData.visibility = View.VISIBLE
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}