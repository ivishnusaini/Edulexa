package com.edulexa.activity.student.chat.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.chat.adapter.ChatListAdapter
import com.edulexa.activity.student.chat.adapter.ChatStaffListAdapter
import com.edulexa.activity.student.chat.model.new_chat.AddNewChatResponse
import com.edulexa.activity.student.chat.model.staff_list.StaffListResponse
import com.edulexa.activity.student.chat.model.user_list.ChatUserListResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityGroupInfoStudentBinding
import com.edulexa.databinding.ActivityStaffListForStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StaffListForStudentActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityStaffListForStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaffListForStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init(){
        mActivity = this
        setUpClickListener()
        getStaffList()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getStaffList(){
        if (Utils.isNetworkAvailable(mActivity!!)) {
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
            jsonObject.put(Constants.ParamsStudent.STUDENT_SESSION_ID, Utils.getStudentSessionId(mActivity!!))

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/webservice/searchNewUser")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getStaffListForChat(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            val jsonObjectResponse = JSONObject(responseStr)
                            val status = jsonObjectResponse.optBoolean("status")
                            if (status) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    StaffListResponse::class.java
                                ) as StaffListResponse
                                if (modelResponse.getData() != null && modelResponse.getData()!!.isNotEmpty()){
                                    binding!!.recyclerView.visibility = View.VISIBLE
                                    binding!!.tvNoData.visibility = View.GONE
                                    binding!!.recyclerView.layoutManager = LinearLayoutManager(mActivity,RecyclerView.VERTICAL,false)
                                    binding!!.recyclerView.adapter = ChatStaffListAdapter(mActivity!!,modelResponse.getData())
                                }else{
                                    binding!!.recyclerView.visibility = View.GONE
                                    binding!!.tvNoData.visibility = View.VISIBLE
                                }
                            }else{
                                binding!!.recyclerView.visibility = View.GONE
                                binding!!.tvNoData.visibility = View.VISIBLE
                            }
                        } else {
                            Utils.showToastPopup(
                                mActivity!!,
                                getString(R.string.response_null_or_empty_validation)
                            )
                            binding!!.recyclerView.visibility = View.GONE
                            binding!!.tvNoData.visibility = View.VISIBLE
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        binding!!.recyclerView.visibility = View.GONE
                        binding!!.tvNoData.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.recyclerView.visibility = View.GONE
                    binding!!.tvNoData.visibility = View.VISIBLE
                }
            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    fun addStaffForChat(staffId : String,staffName: String){
        if (Utils.isNetworkAvailable(mActivity!!)) {
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
            jsonObject.put(Constants.ParamsStudent.STUDENT_SESSION_ID, Utils.getStudentSessionId(mActivity!!))
            jsonObject.put(Constants.ParamsStudent.USER_TYPE, "staff")
            jsonObject.put(Constants.ParamsStudent.USER_ID, staffId)

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/webservice/addNewChatUser")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.addNewChatUser(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            val jsonObjectResponse = JSONObject(responseStr)
                            val status = jsonObjectResponse.optBoolean("status")
                            if (status) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    AddNewChatResponse::class.java
                                ) as AddNewChatResponse
                                if (modelResponse.getData() != null && modelResponse.getData()!!.getChatdata() != null){
                                    if (modelResponse.getData()!!.getChatdata()!!.getChatUser() != null){
                                        val bundle = Bundle()
                                        bundle.putString(Constants.StudentChat.CHAT_NAME,staffName)
                                        bundle.putString(Constants.StudentChat.CHAT_ID_UPDATE,modelResponse.getData()!!.getChatdata()!!.getChatUser()!!.getId())
                                        bundle.putString(Constants.StudentChat.CHAT_ID_NEW_MESSAGE,modelResponse.getData()!!.getNewUser()!!.getChatUserId())
                                        bundle.putString(Constants.StudentChat.CHAT_CONNECTION_ID,modelResponse.getData()!!.getChatConnectionId().toString())
                                        startActivity(Intent(mActivity, ChatMessageActivity::class.java).putExtras(bundle))
                                    }
                                }
                            }
                        } else {
                            Utils.showToastPopup(
                                mActivity!!,
                                getString(R.string.response_null_or_empty_validation)
                            )
                        }
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


    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}