package com.edulexa.activity.student.dashboard.activity

import android.app.Activity
import android.app.Dialog
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
import com.edulexa.activity.student.dashboard.adapter.DashboardStudentAdapter
import com.edulexa.activity.student.dashboard.adapter.DashboardStudentNoticeBoardAdapter
import com.edulexa.activity.student.dashboard.adapter.DashboardStudentTodayHomeworkAdapter
import com.edulexa.activity.student.dashboard.model.*
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
        setUpData()
        getDashboardData();
    }

    private fun setUpclickListener() {
        binding!!.menuLay.setOnClickListener(this)
    }

    private fun setUpData(){
        try {
            binding!!.tvStudentName.text = Utils.getStudentLoginResponse(mActivity)!!.getRecord()!!.getUsername()
            binding!!.tvStudentClass.text = getString(R.string.dashboard_student_class_section_format,"Class ",Utils.getStudentLoginResponse(mActivity)!!.getRecord()!!.getClass_()," ",Utils.getStudentLoginResponse(mActivity)!!.getRecord()!!.getSection())
            Utils.setpProfileImageUsingGlide(mActivity,Utils.getStudentLoginResponse(mActivity)!!.getRecord()!!.getImage(),binding!!.ivStudentImage)
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun getDashboardData(){
        if (Utils.isNetworkAvailable(mActivity!!)){
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId = Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent = APIClientStudent.getRetroFitClientWithNewKeyHeader(mActivity!!, accessToken,branchId,userId).create(
                ApiInterfaceStudent::class.java)

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStudent.STUDENT_ID,Utils.getStudentId(mActivity!!))
            jsonObject.put(Constants.ParamsStudent.DATE_FROM, Utils.getFirstDateOfWeek())
            jsonObject.put(Constants.ParamsStudent.DATE_TO, Utils.getLastDateOfWeek())

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/Dashboard")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getDashboardData(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try{
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()){
                            val modelResponse = Utils.getObject(responseStr, StudentDashboardResponse::class.java) as StudentDashboardResponse
                            if (modelResponse.getNotification() != null){
                                if (modelResponse.getNotification()!!.getSuccess() == 1){
                                    if (modelResponse.getNotification()!!.getData() != null && modelResponse.getNotification()!!.getData()!!.size > 0)
                                        setUpNoticeBoardData(modelResponse.getNotification()!!.getData())
                                    else{
                                        binding!!.studentNoticeBoardRecycler.visibility = View.GONE
                                        binding!!.tvNoticeBoardNoData.visibility = View.VISIBLE
                                    }
                                }else{
                                    binding!!.studentNoticeBoardRecycler.visibility = View.GONE
                                    binding!!.tvNoticeBoardNoData.visibility = View.VISIBLE
                                }
                            }else{
                                binding!!.studentNoticeBoardRecycler.visibility = View.GONE
                                binding!!.tvNoticeBoardNoData.visibility = View.VISIBLE
                            }
                            if (modelResponse.getHomeworklist() != null && modelResponse.getHomeworklist()!!.size > 0){
                                setUpTodayHomeworkData(modelResponse.getHomeworklist())
                            }else{
                                binding!!.studentTodayHomeworkRecycler.visibility = View.GONE
                                binding!!.tvTodayHomeworkNoData.visibility = View.VISIBLE
                            }
                            setUpDashboardData(modelResponse.getModuleList())
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

    private fun setUpNoticeBoardData(list : List<DatumNotification?>?) {
        binding!!.studentNoticeBoardRecycler.visibility = View.VISIBLE
        binding!!.tvNoticeBoardNoData.visibility = View.GONE
        binding!!.studentNoticeBoardRecycler.layoutManager =
            LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false)
        binding!!.studentNoticeBoardRecycler.adapter =
            DashboardStudentNoticeBoardAdapter(mActivity!!,list)
    }

    private fun setUpTodayHomeworkData(list : List<HomeworkStudentDashboard?>?) {
        binding!!.studentTodayHomeworkRecycler.visibility = View.VISIBLE
        binding!!.tvTodayHomeworkNoData.visibility = View.GONE
        binding!!.studentTodayHomeworkRecycler.layoutManager =
            LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false)
        binding!!.studentTodayHomeworkRecycler.adapter =
            DashboardStudentTodayHomeworkAdapter(mActivity!!,list)
    }

    private fun setUpDashboardData(list : List<ModuleDashboard?>?) {
        if (list != null && list.size > 0){
            binding!!.recyclerView.layoutManager =
                GridLayoutManager(mActivity, 3, RecyclerView.VERTICAL, false)

            val dashboardList : ArrayList<DashboardModuleModel> = ArrayList()
            dashboardList.add(DashboardModuleModel("Homework",getImageUrl(list,"homework")))
            dashboardList.add(DashboardModuleModel("Attendance",getImageUrl(list,"attendance")))
            dashboardList.add(DashboardModuleModel("Fee Details",getImageUrl(list,"fees")))
            dashboardList.add(DashboardModuleModel("Examination",getImageUrl(list,"examinations")))
            dashboardList.add(DashboardModuleModel("Report Cards",getImageUrl(list,"report_card")))
            dashboardList.add(DashboardModuleModel("Calendar",getImageUrl(list,"calendar_to_do_list")))
            dashboardList.add(DashboardModuleModel("Notice Board",getImageUrl(list,"notice_board")))
            dashboardList.add(DashboardModuleModel("Multimedia",getImageUrl(list,"gallery")))
            dashboardList.add(DashboardModuleModel("Profile",getImageUrl(list,"timeline")))
            binding!!.recyclerView.adapter = DashboardStudentAdapter(mActivity!!,dashboardList)
        }
    }

    private fun getImageUrl(list : List<ModuleDashboard?>?,moduleName : String) : String{
        var imageUrl = ""
        for (model in list!!){
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

    private fun scrollAtBottom(){
        binding!!.recyclerView.getParent().requestChildFocus(binding!!.recyclerView,binding!!.recyclerView)
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.menu_lay)
            scrollAtBottom()
    }
}