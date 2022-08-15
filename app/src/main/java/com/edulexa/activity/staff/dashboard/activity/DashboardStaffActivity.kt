package com.edulexa.activity.staff.dashboard.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.dashboard.adapter.DashboardNotificationAdapter
import com.edulexa.activity.staff.dashboard.adapter.DashboardStaffAdapter
import com.edulexa.activity.staff.dashboard.model.DashboardModel
import com.edulexa.activity.staff.dashboard.model.notifications.NotificationResponse
import com.edulexa.activity.staff.student_profile.adapter.StudentListAdapter
import com.edulexa.activity.staff.student_profile.model.student_list.StudentListResponse
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityDashboardStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardStaffActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityDashboardStaffBinding? = null
    var preference: Preference? = null
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
        preference = Preference().getInstance(mActivity!!)
        setUpclickListener()
        getNotificationData()
        setUpDashboardData()
    }

    private fun setUpclickListener() {
        binding!!.tvLogout.setOnClickListener(this)
        binding!!.tvViewAll.setOnClickListener(this)
    }

    private fun getNotificationData() {
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

            val apiInterfaceWithHeader: ApiInterfaceStaff =
                APIClientStaff.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    Utils.getStaffToken(mActivity!!),
                    Utils.getStaffId(mActivity!!), dbId!!
                ).create(ApiInterfaceStaff::class.java)


            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
            builder.addFormDataPart(Constants.ParamsStaff.MODULE_ID, Constants.MODULE_ID_VALUE)
            builder.addFormDataPart(
                Constants.ParamsStaff.ROLE_ID,
                Utils.getStaffRoleId(mActivity!!)
            )
            val requestBody = builder.build()


            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getNotifications")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getNotifications(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
                        Utils.printLog("response", responseStr)
                        if (!responseStr.isNullOrEmpty()) {
                            val responseJsonObject = JSONObject(responseStr)
                            val notificationJsonObj =
                                responseJsonObject.optJSONObject("notification")
                            if (notificationJsonObj != null) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    NotificationResponse::class.java
                                ) as NotificationResponse
                                if (modelResponse.getNotification()!!.getData()!!.isNotEmpty()) {
                                    Constants.AppSaveData.staffNotificationList =
                                        modelResponse.getNotification()!!.getData()
                                    binding!!.recyclerViewNoticeBoard.visibility = View.VISIBLE
                                    binding!!.tvViewAll.visibility = View.VISIBLE
                                    binding!!.tvNoData.visibility = View.GONE
                                    binding!!.recyclerViewNoticeBoard.layoutManager =
                                        LinearLayoutManager(
                                            mActivity,
                                            RecyclerView.HORIZONTAL,
                                            false
                                        )
                                    binding!!.recyclerViewNoticeBoard.adapter =
                                        DashboardNotificationAdapter(
                                            mActivity!!,
                                            modelResponse.getNotification()!!.getData()
                                        )
                                } else {
                                    binding!!.recyclerViewNoticeBoard.visibility = View.GONE
                                    binding!!.tvViewAll.visibility = View.GONE
                                    binding!!.tvNoData.visibility = View.VISIBLE
                                }
                            } else {
                                val message = responseJsonObject.optString("message")
                                if (!message.isEmpty())
                                    Utils.showToastPopup(mActivity!!, message)
                                else Utils.showToastPopup(
                                    mActivity!!,
                                    getString(R.string.did_not_fetch_data)
                                )
                                binding!!.recyclerViewNoticeBoard.visibility = View.GONE
                                binding!!.tvViewAll.visibility = View.GONE
                                binding!!.tvNoData.visibility = View.VISIBLE
                            }
                        } else {
                            Utils.showToastPopup(
                                mActivity!!,
                                getString(R.string.response_null_or_empty_validation)
                            )
                            binding!!.recyclerViewNoticeBoard.visibility = View.GONE
                            binding!!.tvViewAll.visibility = View.GONE
                            binding!!.tvNoData.visibility = View.VISIBLE
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        binding!!.recyclerViewNoticeBoard.visibility = View.GONE
                        binding!!.tvViewAll.visibility = View.GONE
                        binding!!.tvNoData.visibility = View.VISIBLE
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.recyclerViewNoticeBoard.visibility = View.GONE
                    binding!!.tvViewAll.visibility = View.GONE
                    binding!!.tvNoData.visibility = View.VISIBLE
                }

            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    private fun setUpDashboardData() {
        try {
            binding!!.tvName.text = getString(
                R.string.concat_string_with_text_format,
                Utils.getStaffLoginResponse(mActivity)!!.getRecord()!!.getName(),
                " ",
                Utils.getStaffLoginResponse(mActivity)!!.getRecord()!!.getSurname()
            )
            binding!!.recyclerView.layoutManager =
                GridLayoutManager(mActivity, 3, RecyclerView.VERTICAL, false)
            binding!!.recyclerView.adapter = DashboardStaffAdapter(mActivity!!, getModuleList())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getModuleList(): List<DashboardModel> {
        val moduleList: List<DashboardModel> = ArrayList()
        (moduleList as ArrayList<DashboardModel>).add(
            DashboardModel(
                R.drawable.student_profile_staff,
                getString(R.string.dashboard_staff_student_profile)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.custom_lesson_staff,
                getString(R.string.dashboard_staff_custom_lesson_plan)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.online_exam_staff,
                getString(R.string.dashboard_staff_online_exam)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.homework_staff,
                getString(R.string.dashboard_staff_homework)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.lesson_plan_staff,
                getString(R.string.dashboard_staff_lesson_plan)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.circular_staff,
                getString(R.string.dashboard_staff_circular)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.events_staff,
                getString(R.string.dashboard_staff_events)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.gallery_staff,
                getString(R.string.dashboard_staff_gallery)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.school_family_staff,
                getString(R.string.dashboard_staff_school_family)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.syllabus_status_staff,
                getString(R.string.dashboard_staff_syllabus_status)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.create_exam_staff,
                getString(R.string.dashboard_staff_create_exam)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.student_admission_staff,
                getString(R.string.dashboard_staff_student_admission)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.attendance_staff,
                getString(R.string.dashboard_staff_attendance)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.add_lesson_staff,
                getString(R.string.dashboard_staff_add_lesson)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.add_topic_staff,
                getString(R.string.dashboard_staff_add_topic)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.approve_leave_staff,
                getString(R.string.dashboard_staff_approve_leave)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.apply_leave_staff,
                getString(R.string.dashboard_staff_apply_leave)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.view_all_staff,
                getString(R.string.dashboard_staff_view_all)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.staff_meeting_staff,
                getString(R.string.dashboard_staff_staff_meeting)
            )
        )
        moduleList.add(
            DashboardModel(
                R.drawable.live_class_staff,
                getString(R.string.dashboard_staff_live_classes)
            )
        )
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
        else if (id == R.id.tv_view_all) {
            val bundle = Bundle()
            bundle.putString(Constants.StaffNotification.FROM_WHERE, "view_all")
            startActivity(
                Intent(mActivity, ViewAllNotificationActivity::class.java).putExtras(
                    bundle
                )
            )
        }
    }
}