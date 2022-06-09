package com.edulexa.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.branch_code.adapter.BranchSpinnerAdapter
import com.edulexa.activity.branch_code.model.Branch
import com.edulexa.activity.branch_code.model.BranchCodeResponse
import com.edulexa.activity.select_school.activity.SelectSchoolActivity
import com.edulexa.activity.staff.dashboard.activity.DashboardStaffActivity
import com.edulexa.activity.staff.login.StaffLoginResponse
import com.edulexa.activity.student.dashboard.activity.DashboardStudentActivity
import com.edulexa.activity.student.login.ParentChildList
import com.edulexa.activity.student.login.StudentLoginResponse
import com.edulexa.activity.student.login.adapter.MultipleChildAdapter
import com.edulexa.api.*
import com.edulexa.databinding.ActivityLoginBinding
import com.edulexa.databinding.DialogStudentMultipleChildBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.loopj.android.http.RequestParams
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityLoginBinding? = null
    var preference : Preference? = null
    var staffStudentType = "";
    var branchId = "";
    var branchListSpinn: List<Branch?> = ArrayList()
    var branchSpinnerAdapter : BranchSpinnerAdapter? = null
    var firebaseToken: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        setBaseUrl()
        setUpData()
        getFirebaseToken()
        getBranchCode()
    }

    private fun setUpClickListener() {
        binding!!.tvBack.setOnClickListener(this)
        binding!!.tvStaff.setOnClickListener(this)
        binding!!.tvStudent.setOnClickListener(this)
        binding!!.tvForgotPassword.setOnClickListener(this)
        binding!!.tvSubmit.setOnClickListener(this)
    }

    private fun setBaseUrl(){
        preference!!.putString(Constants.Preference.STUDENT_BASE_URL,Constants.BASE_URL_STUDENT)
        preference!!.putString(Constants.Preference.APIURL_STUDENT,Constants.DOMAIN_STUDENT)
        Constants.BASE_URL_STUDENT = preference!!.getString(Constants.Preference.STUDENT_BASE_URL)!!
        if (Constants.BASE_URL_STUDENT.endsWith("/"))
            preference!!.putString(Constants.Preference.IMAGESURL_STUDENT,Constants.BASE_URL_STUDENT)
        else preference!!.putString(Constants.Preference.IMAGESURL_STUDENT,Constants.BASE_URL_STUDENT + "/")
        Constants.DOMAIN_STUDENT = preference!!.getString(Constants.Preference.APIURL_STUDENT)!!
        Constants.PG_RETURN_URL_STUDENT = Constants.BASE_URL_STUDENT+Constants.API_TRAKNPAY
        Constants.PG_RETURN_BULK_URL_STUDENT = Constants.BASE_URL_STUDENT+Constants.API_TRAKNPAY_BALKFEEADD
        Constants.PG_RETURN_TRANSPORT_BULK_URL_STUDENT = Constants.BASE_URL_STUDENT+Constants.API_TRAKNPAY_BALKTRANSPORTFEEADD
        preference!!.putString(Constants.Preference.SCHOOL_LOGO,Constants.BASE_URL_SCHOOL_LOGO)
        Constants.BASE_URL_SCHOOL_LOGO = preference!!.getString(Constants.Preference.SCHOOL_LOGO)!!

        preference!!.putString(Constants.Preference.STAFF_BASE_URL,Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF)
        if (preference!!.getString(Constants.Preference.STAFF_BASE_URL)!!.endsWith("/"))
            Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF = preference!!.getString(Constants.Preference.STAFF_BASE_URL)!!
        else Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF = preference!!.getString(Constants.Preference.STAFF_BASE_URL)!! + "/"
        Constants.BASE_URL_STAFF = Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF + Constants.STAFF_API_WEBSERVICE
        Constants.BASE_URL_WEBVIEW_STAFF = Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF + Constants.SITE_WEBVIEWLOGIN_USERNAME

    }

    private fun setUpData(){
        try {
            Utils.setImageUsingGlide(mActivity!!,preference!!.getString(Constants.Preference.SCHOOL_LOGO),binding!!.ivLoginLogo)
            binding!!.tvLoginSchoolName.text = preference!!.getString(Constants.Preference.SCHOOL_NAME)
            binding!!.branchSpinn.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                    if (branchListSpinn.get(position)!!.getId() != null)
                        branchId = branchListSpinn.get(position)!!.getId()!!
                    else branchId = ""
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            })

        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task: Task<String?> ->
                if (!task.isSuccessful) {
                    return@addOnCompleteListener
                }
                firebaseToken = task.result
            }
    }

    private fun getBranchCode(){
        (branchListSpinn as ArrayList<Branch?>).clear()
        if (branchSpinnerAdapter != null)
            branchSpinnerAdapter!!.notifyDataSetChanged()
        branchId = ""
        if (Utils.isNetworkAvailable(mActivity!!)){
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)
            val communicator = Communicator()
            communicator.post(101,mActivity!!,Constants.Api.GET_BRANCH_CODE, RequestParams(),object : CustomResponseListener{
                override fun onResponse(requestCode: Int, response: String?) {
                    Utils.hideProgressBar()
                    try{
                        if (!response.isNullOrEmpty()){
                            val modelResponse = Utils.getObject(response, BranchCodeResponse::class.java) as BranchCodeResponse
                            if (modelResponse.getStatus() == 200) {
                                if (modelResponse.getBranch() != null) {
                                    if (modelResponse.getBranch()!!.size > 1){
                                        binding!!.branchSpinnLay.visibility = View.VISIBLE
                                        val branch = Branch()
                                        branch.setInstituteName("Select Branch")
                                        branchListSpinn = modelResponse.getBranch() as List<Branch?>
                                        (branchListSpinn as ArrayList<Branch?>).add(0,branch)
                                        binding!!.branchSpinn.adapter = BranchSpinnerAdapter(mActivity!!,branchListSpinn)
                                    }else{
                                        if (modelResponse.getBranch()!!.size == 1)
                                            branchId = modelResponse.getBranch()!!.get(0)!!.getId()!!
                                    }
                                }
                            }else  Utils.showToastPopup(mActivity!!, getString(R.string.did_not_fetch_data))
                        } else Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                }

                override fun onFailure(statusCode: Int, error: Throwable?) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                }

            })
        }else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))
    }

    private fun resetStaffStudentButton(type: String) {
        binding!!.tvStaff.setBackgroundResource(R.drawable.edit_text_bg_25)
        binding!!.tvStudent.setBackgroundResource(R.drawable.edit_text_bg_25)
        binding!!.tvStaff.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.tvStudent.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        when (type) {
            "staff" -> {
                staffStudentType = "staff"
                binding!!.tvStaff.setBackgroundResource(R.drawable.bg_button_25)
                binding!!.tvStaff.setTextColor(ContextCompat.getColor(mActivity!!, R.color.white))
            }
            "student" -> {
                staffStudentType = "student"
                binding!!.tvStudent.setBackgroundResource(R.drawable.bg_button_25)
                binding!!.tvStudent.setTextColor(ContextCompat.getColor(mActivity!!, R.color.white))
            }
        }
    }

    private fun goToHomeActivity() {
        if (binding!!.etUserName.text.toString().isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.login_enter_user_name))
        else if (binding!!.etPassword.text.toString().isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.login_enter_password))
        else if (staffStudentType.isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.login_select_login_type))
        else if (branchId.isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.login_select_branch))
        else{
            preference!!.putString(Constants.Preference.BRANCH_ID,branchId)
            if (staffStudentType.equals("staff")) {
                if (Utils.isNetworkAvailable(mActivity!!)){
                    Utils.showProgressBar(mActivity!!)
                    Utils.hideKeyboard(mActivity!!)

                    val apiInterfaceWithHeader: ApiInterfaceStaff = APIClientStaff.getRetroFitClientWithHeader(mActivity!!).create(ApiInterfaceStaff::class.java)
                    val userNameRequestBody = RequestBody.create(MediaType.parse("text/plain"), binding!!.etUserName.text.toString().trim())
                    val passwordRequestBody = RequestBody.create(MediaType.parse("text/plain"), binding!!.etPassword.text.toString().trim())
                    val tokenRequestBody = RequestBody.create(MediaType.parse("text/plain"), firebaseToken!!)

                    Utils.printLog("Url",Constants.BASE_URL_STAFF+"login")

                    val call: Call<ResponseBody> = apiInterfaceWithHeader.staffLogin(userNameRequestBody, passwordRequestBody, tokenRequestBody)
                    call.enqueue(object :Callback<ResponseBody>{
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            Utils.hideProgressBar()
                            try{
                                val responseStr = response.body()!!.string()
                                if (!responseStr.isNullOrEmpty()){
                                    val responseJsonObject = JSONObject(responseStr)
                                    val status = responseJsonObject.optInt("status")
                                    if (status == 200){
                                        val recordJsonObject = responseJsonObject.optJSONObject("record")
                                        if (recordJsonObject != null){
                                            val modelResponse = Utils.getObject(responseStr, StaffLoginResponse::class.java) as StaffLoginResponse
                                            Utils.saveStaffLoginResponse(mActivity,modelResponse)
                                            preference!!.putString(Constants.Preference.APP_TYPE,Constants.Preference.APP_TYPE_STAFF)
                                            preference!!.putString(Constants.Preference.STAFF_IS_LOGIN,Constants.Preference.STAFF_IS_LOGIN_YES)
                                            startActivity(Intent(mActivity!!, DashboardStaffActivity::class.java))
                                            finish()
                                        }
                                    }else {
                                        val message = responseJsonObject.optString("message")
                                        if (!message.isEmpty())
                                            Utils.showToastPopup(mActivity!!,message)
                                        else Utils.showToastPopup(mActivity!!,getString(R.string.did_not_fetch_data))
                                    }
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
            } else {
                if (Utils.isNetworkAvailable(mActivity!!)){
                    Utils.showProgressBar(mActivity!!)
                    Utils.hideKeyboard(mActivity!!)

                    Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
                    val apiInterfaceWithHeader: ApiInterfaceStudent = APIClientStudent.getRetroFitClientWithHeader(mActivity!!).create(ApiInterfaceStudent::class.java)

                    val jsonObject = JSONObject()
                    jsonObject.put(Constants.ParamsStudent.USERNAME, binding!!.etUserName.text.toString().trim())
                    jsonObject.put(Constants.ParamsStudent.PASSWORD, binding!!.etPassword.text.toString().trim())
                    jsonObject.put(Constants.ParamsStudent.DEVICETOKEN, firebaseToken)

                    val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

                    Utils.printLog("Url",Constants.DOMAIN_STUDENT+"/Webservice/login")

                    val call: Call<ResponseBody> = apiInterfaceWithHeader.studentLogin(requestBody)
                    call.enqueue(object :Callback<ResponseBody>{
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            Utils.hideProgressBar()
                            try{
                                val responseStr = response.body()!!.string()
                                if (!responseStr.isNullOrEmpty()){
                                    val responseJsonObject = JSONObject(responseStr)
                                    val status = responseJsonObject.optInt("status")
                                    if (status == 200){
                                        val recordJsonObject = responseJsonObject.optJSONObject("record")
                                        if (recordJsonObject != null){
                                            val modelResponse = Utils.getObject(responseStr, StudentLoginResponse::class.java) as StudentLoginResponse
                                            Utils.saveStudentLoginResponse(mActivity,modelResponse)
                                            if (modelResponse.getRole().equals("parent")){
                                                if (modelResponse.getRecord()!!.getParentChildList() != null
                                                    && modelResponse.getRecord()!!.getParentChildList()!!.size == 1){
                                                    preference!!.putString(Constants.Preference.STUDENT_IS_LOGIN,Constants.Preference.STUDENT_IS_LOGIN_YES)
                                                    preference!!.putString(Constants.Preference.HAS_MULTIPLE_CHILD, Constants.Preference.HAS_MULTIPLE_CHILD_NO)
                                                    preference!!.putString(Constants.Preference.STUDENT_ID, modelResponse.getRecord()!!.getParentChildList()!!.get(0).getStudentId())
                                                    preference!!.putString(Constants.Preference.SESSION_ID, "")
                                                    preference!!.putString(Constants.Preference.STUDENT_SESSION_ID, modelResponse.getRecord()!!.getParentChildList()!!.get(0).getStudentSessionId())
                                                    preference!!.putString(Constants.Preference.CLASS_SECTION, modelResponse.getRecord()!!.getParentChildList()!!.get(0).getClass() + "-"+modelResponse.getRecord()!!.getParentChildList()!!.get(0).getSection())
                                                    preference!!.putString(Constants.Preference.STUDENT_NAME, modelResponse.getRecord()!!.getParentChildList()!!.get(0).getName())
                                                    preference!!.putString(Constants.Preference.CLASS_ID, modelResponse.getRecord()!!.getClassId())
                                                    preference!!.putString(Constants.Preference.SECTION_ID, modelResponse.getRecord()!!.getSectionId())
                                                    startActivity(Intent(mActivity!!, DashboardStudentActivity::class.java))
                                                    finish()
                                                }else{
                                                    preference!!.putString(Constants.Preference.HAS_MULTIPLE_CHILD, Constants.Preference.HAS_MULTIPLE_CHILD_YES)
                                                    showChildListPopup(modelResponse.getRecord()!!.getParentChildList())
                                                }
                                            }else if (modelResponse.getRole().equals("student")){
                                                preference!!.putString(Constants.Preference.STUDENT_IS_LOGIN,Constants.Preference.STUDENT_IS_LOGIN_YES)
                                                preference!!.putString(Constants.Preference.HAS_MULTIPLE_CHILD, Constants.Preference.HAS_MULTIPLE_CHILD_NO)
                                                preference!!.putString(Constants.Preference.STUDENT_ID, modelResponse.getRecord()!!.getStudentId())
                                                preference!!.putString(Constants.Preference.SESSION_ID,modelResponse.getRecord()!!.getSessionId())
                                                preference!!.putString(Constants.Preference.STUDENT_SESSION_ID, modelResponse.getRecord()!!.getStudentSessionId())
                                                preference!!.putString(Constants.Preference.CLASS_SECTION, modelResponse.getRecord()!!.getClass_() + "-"+modelResponse.getRecord()!!.getSection())
                                                preference!!.putString(Constants.Preference.STUDENT_NAME, modelResponse.getRecord()!!.getUsername())
                                                preference!!.putString(Constants.Preference.CLASS_ID, modelResponse.getRecord()!!.getClassId())
                                                preference!!.putString(Constants.Preference.SECTION_ID, modelResponse.getRecord()!!.getSectionId())
                                                startActivity(Intent(mActivity!!, DashboardStudentActivity::class.java))
                                                finish()
                                            }

                                        }
                                    }else {
                                        val message = responseJsonObject.optString("message")
                                        if (!message.isEmpty())
                                            Utils.showToastPopup(mActivity!!,message)
                                        else Utils.showToastPopup(mActivity!!,getString(R.string.did_not_fetch_data))
                                    }
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
        }
    }

    private fun showChildListPopup(list: List<ParentChildList>?){
        try {
            var binding : DialogStudentMultipleChildBinding? = null
            val dialog = Dialog(mActivity!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            binding = DialogStudentMultipleChildBinding.inflate(layoutInflater)
            dialog.setContentView(binding.root)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)

            binding.studentMultipleChildRecycler.layoutManager = LinearLayoutManager(mActivity,RecyclerView.VERTICAL,false)
            binding.studentMultipleChildRecycler.adapter = MultipleChildAdapter(mActivity!!,list)

            binding.btnCancel.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    dialog.dismiss()
                }
            })
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun goToForgotPasswordActivity(){
        startActivity(Intent(mActivity!!, ForgotPasswordActivity::class.java))
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.tv_back)
            startActivity(Intent(mActivity!!, SelectSchoolActivity::class.java))
        else if (id == R.id.tv_staff)
            resetStaffStudentButton("staff")
        else if (id == R.id.tv_student)
            resetStaffStudentButton("student")
        else if (id == R.id.tv_submit)
            goToHomeActivity()
        else if (id == R.id.tv_forgot_password)
            goToForgotPasswordActivity()
    }
}