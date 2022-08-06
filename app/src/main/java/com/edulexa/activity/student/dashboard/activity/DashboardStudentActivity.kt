package com.edulexa.activity.student.dashboard.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.LoginActivity
import com.edulexa.activity.student.dashboard.adapter.DashboardStudentAdapter
import com.edulexa.activity.student.dashboard.adapter.DashboardStudentNoticeBoardAdapter
import com.edulexa.activity.student.dashboard.adapter.DashboardStudentTodayHomeworkAdapter
import com.edulexa.activity.student.dashboard.model.*
import com.edulexa.activity.student.homework.adapter.SubjectSpinnerAdapter
import com.edulexa.activity.student.homework.model.subject_list.SubjectListDatum
import com.edulexa.activity.student.homework.model.subject_list.SubjectListResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityDashboardStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class DashboardStudentActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityDashboardStudentBinding? = null
    var preference : Preference? = null
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
        preference = Preference().getInstance(mActivity!!)
        setUpclickListener()
        setUpData()
        getDashboardData();
    }

    private fun setUpclickListener() {
        binding!!.menuLay.setOnClickListener(this)
        binding!!.tvLogout.setOnClickListener(this)
    }

    private fun setUpData() {
        try {
            binding!!.tvStudentName.text =
                Utils.getStudentLoginResponse(mActivity)!!.getRecord()!!.getUsername()
            binding!!.tvStudentClass.text = getString(
                R.string.dashboard_student_class_section_format,
                "Class ",
                Utils.getStudentLoginResponse(mActivity)!!.getRecord()!!.getClass_(),
                " ",
                Utils.getStudentLoginResponse(mActivity)!!.getRecord()!!.getSection()
            )
            Utils.setpProfileImageUsingGlide(
                mActivity,
                Constants.BASE_URL_STUDENT + Utils.getStudentLoginResponse(mActivity)!!
                    .getRecord()!!.getImage(),
                binding!!.ivStudentImage
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getDashboardData() {
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId =
                Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent =
                APIClientStudent.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    accessToken,
                    branchId,
                    userId
                ).create(
                    ApiInterfaceStudent::class.java
                )

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStudent.STUDENT_ID, Utils.getStudentId(mActivity!!))
            jsonObject.put(Constants.ParamsStudent.DATE_FROM, Utils.getFirstDateOfWeek())
            jsonObject.put(Constants.ParamsStudent.DATE_TO, Utils.getLastDateOfWeek())

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/Webservice/Dashboard")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getDashboardData(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    binding!!.topLay.visibility = View.VISIBLE
                    try {
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            val jsonObjectFromResponse = JSONObject(responseStr)
                            val status = jsonObjectFromResponse.optInt("status")
                            if (status == 200) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    StudentDashboardResponse::class.java
                                ) as StudentDashboardResponse
                                if (modelResponse.getDashboardDatum() != null) {
                                    if (modelResponse.getDashboardDatum()!!
                                            .getNotification() != null
                                    ) {
                                        if (modelResponse.getDashboardDatum()!!
                                                .getNotification()!!.size > 0
                                        )
                                            setUpNoticeBoardData(
                                                modelResponse.getDashboardDatum()!!
                                                    .getNotification()
                                            )
                                        else {
                                            binding!!.studentNoticeBoardRecycler.visibility =
                                                View.GONE
                                            binding!!.tvNoticeBoardNoData.visibility = View.VISIBLE
                                        }
                                    } else {
                                        binding!!.studentNoticeBoardRecycler.visibility = View.GONE
                                        binding!!.tvNoticeBoardNoData.visibility = View.VISIBLE
                                    }
                                }
                                if (modelResponse.getDashboardDatum() != null && modelResponse.getDashboardDatum()!!
                                        .getHomeworklist()!!.size > 0
                                ) {
                                    setUpTodayHomeworkData(
                                        modelResponse.getDashboardDatum()!!.getHomeworklist()
                                    )
                                } else {
                                    binding!!.studentTodayHomeworkRecycler.visibility = View.GONE
                                    binding!!.tvTodayHomeworkNoData.visibility = View.VISIBLE
                                }

                                if (jsonObjectFromResponse.optJSONObject("attendence") != null) {
                                    val attendanceType = jsonObjectFromResponse.optString("type")
                                    binding!!.tvStudentDashboardPresent.text = getString(
                                        R.string.dashboard_student_present_format,
                                        "You are ",
                                        attendanceType,
                                        " today"
                                    )
                                }
                                if (modelResponse.getDashboardDatum() != null)
                                    setUpDashboardData(modelResponse.getDashboardDatum()!!.getModuleList())
                                if (modelResponse.getDashboardDatum()!!.getLiveClassConfig() != null){
                                    if (modelResponse.getDashboardDatum()!!.getLiveClassConfig()!!.size > 0){
                                        preference!!.putString(Constants.Preference.ZOOM_SDK_KEY,modelResponse.getDashboardDatum()!!.getLiveClassConfig()!!.get(0)!!.getApiKey())
                                        preference!!.putString(Constants.Preference.ZOOM_SDK_SECRAT,modelResponse.getDashboardDatum()!!.getLiveClassConfig()!!.get(0)!!.getApiSecret())
                                    }
                                }
                            }
                        } else Utils.showToastPopup(
                            mActivity!!,
                            getString(R.string.response_null_or_empty_validation)
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                }

            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    private fun setUpNoticeBoardData(list: List<DatumNotification?>?) {
        binding!!.studentNoticeBoardRecycler.visibility = View.VISIBLE
        binding!!.tvNoticeBoardNoData.visibility = View.GONE
        binding!!.studentNoticeBoardRecycler.layoutManager =
            LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false)
        binding!!.studentNoticeBoardRecycler.adapter =
            DashboardStudentNoticeBoardAdapter(mActivity!!, list)
    }

    private fun setUpTodayHomeworkData(list: List<HomeworkStudentDashboard?>?) {
        binding!!.studentTodayHomeworkRecycler.visibility = View.VISIBLE
        binding!!.tvTodayHomeworkNoData.visibility = View.GONE
        binding!!.studentTodayHomeworkRecycler.layoutManager =
            LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false)
        binding!!.studentTodayHomeworkRecycler.adapter =
            DashboardStudentTodayHomeworkAdapter(mActivity!!, list)
    }

    private fun setUpDashboardData(list: List<ModuleDashboard?>?) {
        try{
        if (list != null && list.size > 0) {
            binding!!.recyclerView.layoutManager =
                GridLayoutManager(mActivity, 3, RecyclerView.VERTICAL, false)
            val dashboardList: ArrayList<DashboardModuleModel> = ArrayList()
            for (model in list) {
                if (model!!.getIsActive().equals("1"))
                    dashboardList.add(
                        DashboardModuleModel(
                            model.getName()!!,
                            model.getIconLink()!!,
                            model.getShortCode()!!
                        )
                    )
            }
            binding!!.recyclerView.adapter = DashboardStudentAdapter(mActivity!!, dashboardList)
        }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun getImageUrl(list: List<ModuleDashboard?>?, moduleName: String): String {
        var imageUrl = ""
        for (model in list!!) {
            if (moduleName.equals(model!!.getShortCode())) {
                imageUrl = model.getIconLink()!!
                break
            }
        }
        return imageUrl
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

    private fun scrollAtBottom() {
        binding!!.recyclerView.getParent()
            .requestChildFocus(binding!!.recyclerView, binding!!.recyclerView)
    }

    private fun dialogLogout(){
        try {
            val dialog = Dialog(mActivity!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_exit)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)
            val tvMessage = dialog.findViewById<TextView>(R.id.tv_message)
            tvMessage.text = getString(R.string.logout_message)
            val cvCancel: CardView = dialog.findViewById(R.id.cv_cancel)
            val cvOk: CardView = dialog.findViewById(R.id.cv_ok)
            cvCancel.setOnClickListener { v: View? -> dialog.dismiss() }
            cvOk.setOnClickListener { v: View? ->
                Utils.hideKeyboard(mActivity!!)
                dialog.dismiss()

                if (Utils.isNetworkAvailable(mActivity!!)){
                    Utils.showProgressBar(mActivity!!)
                    Utils.hideKeyboard(mActivity!!)

                    val branchId = Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
                    val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
                    val userId = Utils.getStudentUserId(mActivity!!)

                    val apiInterfaceWithHeader: ApiInterfaceStudent = APIClientStudent.getRetroFitClientWithNewKeyHeader(mActivity!!, accessToken,branchId,userId).create(
                        ApiInterfaceStudent::class.java)

                    Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/logout")

                    val call: Call<ResponseBody> = apiInterfaceWithHeader.logout()
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
                                        preference!!.clearPreference()
                                        preference!!.putString(Constants.Preference.STUDENT_IS_LOGIN,Constants.Preference.STUDENT_IS_LOGIN_NO)
                                        preference!!.putString(Constants.Preference.APP_TYPE,Constants.Preference.APP_TYPE_STUDENT)
                                        preference!!.putString(Constants.Preference.LOGOUTSTATUS,Constants.Preference.LOGOUTSTATUS_VALUE)
                                        startActivity(Intent(mActivity,LoginActivity::class.java))
                                        finishAffinity()
                                    }else Utils.showToast(mActivity!!,message)
                                }else Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                            }catch (e : Exception){
                                e.printStackTrace()
                            }
                        }
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Utils.hideProgressBar()
                            Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                        }
                    })
                }else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

            }
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.menu_lay)
            scrollAtBottom()
        else if (id == R.id.tv_logout)
            dialogLogout()
    }
}