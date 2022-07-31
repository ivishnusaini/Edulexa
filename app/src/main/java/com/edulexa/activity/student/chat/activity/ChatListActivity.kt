package com.edulexa.activity.student.chat.activity

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.chat.adapter.ChatListAdapter
import com.edulexa.activity.student.chat.adapter.ChatListGroupAdapter
import com.edulexa.activity.student.chat.model.user_list.ChatUserListData
import com.edulexa.activity.student.chat.model.user_list.ChatUserListResponse
import com.edulexa.activity.student.chat.model.user_list.Group
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityChatListStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatListActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityChatListStudentBinding? = null
    var listChats : List<ChatUserListData?>? = null
    var listGroup : List<Group?>? = null
    var chatUserId = ""
    var chatType = ""
    var modelResponse = null
    var chatListAdapter : ChatListAdapter? = null
    var chatListGroupAdapter : ChatListGroupAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        getChatUserList("start")
        filterList()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.tvChats.setOnClickListener(this)
        binding!!.tvGroup.setOnClickListener(this)
    }

    private fun getChatUserList(type: String) {
        if (Utils.isNetworkAvailable(mActivity!!)) {
            if (type.equals("start"))
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
            jsonObject.put(Constants.ParamsStudent.STUDENT_SESSION_ID, Utils.getStudentSessionId(mActivity!!))

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/Webservice/myuserlist")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getChatUserList(requestBody)
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
                                    ChatUserListResponse::class.java
                                ) as ChatUserListResponse
                                if (modelResponse.getData() != null && modelResponse.getData()!!.getUserList() != null){
                                    if (modelResponse.getData()!!.getUserList()!!.getChatUsers() != null
                                        && modelResponse.getData()!!.getUserList()!!.getChatUsers()!!.size > 0){
                                        binding!!.chatStudentRecycler.visibility = View.VISIBLE
                                        binding!!.searchLay.visibility = View.VISIBLE
                                        binding!!.tvChatNoData.visibility = View.GONE
                                        listChats = modelResponse.getData()!!.getUserList()!!.getChatUsers()
                                        chatUserId = modelResponse.getData()!!.getChatUser()!!.getId()!!
                                        binding!!.chatStudentRecycler.layoutManager = LinearLayoutManager(mActivity,RecyclerView.VERTICAL,false)
                                        chatListAdapter = ChatListAdapter(mActivity!!,listChats,chatUserId)
                                        binding!!.chatStudentRecycler.adapter = chatListAdapter
                                        chatType = "Chat"
                                        resetAll()
                                    }else{
                                        binding!!.chatStudentRecycler.visibility = View.GONE
                                        binding!!.searchLay.visibility = View.GONE
                                        binding!!.tvChatNoData.visibility = View.VISIBLE
                                    }
                                    if (modelResponse.getData()!!.getGroupList() != null
                                        && modelResponse.getData()!!.getGroupList()!!.size > 0)
                                        listGroup = modelResponse.getData()!!.getGroupList()!!
                                }else{
                                    binding!!.chatStudentRecycler.visibility = View.GONE
                                    binding!!.searchLay.visibility = View.GONE
                                    binding!!.tvChatNoData.visibility = View.VISIBLE
                                }
                            }else{
                                binding!!.chatStudentRecycler.visibility = View.GONE
                                binding!!.searchLay.visibility = View.GONE
                                binding!!.tvChatNoData.visibility = View.VISIBLE
                            }
                        } else {
                            Utils.showToastPopup(
                                mActivity!!,
                                getString(R.string.response_null_or_empty_validation)
                            )
                            binding!!.chatStudentRecycler.visibility = View.GONE
                            binding!!.searchLay.visibility = View.GONE
                            binding!!.tvChatNoData.visibility = View.VISIBLE
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        binding!!.chatStudentRecycler.visibility = View.GONE
                        binding!!.searchLay.visibility = View.GONE
                        binding!!.tvChatNoData.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.chatStudentRecycler.visibility = View.GONE
                    binding!!.searchLay.visibility = View.GONE
                    binding!!.tvChatNoData.visibility = View.VISIBLE
                }
            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    private fun filterList(){
        try {
            binding!!.etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (chatType == "Chat") {
                        if (!s.toString().isEmpty()) {
                            if (listChats != null && listChats!!.size > 0) {
                                val listFilter : List<ChatUserListData?> = ArrayList()
                                for (model in listChats!!) {
                                    if (model!!.getUserDetails()!!.getName()!!.lowercase().contains(s.toString().lowercase()))
                                        (listFilter as ArrayList<ChatUserListData?>).add(model)
                                }
                                if (listFilter.size > 0) {
                                    binding!!.chatStudentRecycler.visibility = View.VISIBLE
                                    binding!!.searchLay.visibility = View.VISIBLE
                                    binding!!.tvChatNoData.visibility = View.GONE
                                    chatListAdapter = ChatListAdapter(mActivity!!,listFilter,chatUserId)
                                    binding!!.chatStudentRecycler.adapter = chatListAdapter
                                } else {
                                    binding!!.chatStudentRecycler.visibility = View.GONE
                                    binding!!.searchLay.visibility = View.GONE
                                    binding!!.tvChatNoData.visibility = View.VISIBLE
                                }
                            }
                        } else {
                            binding!!.chatStudentRecycler.visibility = View.VISIBLE
                            binding!!.searchLay.visibility = View.VISIBLE
                            binding!!.tvChatNoData.visibility = View.GONE
                            chatListAdapter = ChatListAdapter(mActivity!!,listChats,chatUserId)
                            binding!!.chatStudentRecycler.adapter = chatListAdapter
                        }
                    } else {
                        if (!s.toString().isEmpty()) {
                            if (listGroup != null && listGroup!!.size > 0) {
                                val listFilter : List<Group?> = ArrayList()
                                for (model in listGroup!!) {
                                    if (model!!.getTitle()!!.lowercase().contains(s.toString().lowercase()))
                                        (listFilter as ArrayList<Group?>).add(model)
                                }
                                if (listFilter.size > 0) {
                                    binding!!.chatStudentRecycler.visibility = View.VISIBLE
                                    binding!!.searchLay.visibility = View.VISIBLE
                                    binding!!.tvChatNoData.visibility = View.GONE
                                    chatListGroupAdapter = ChatListGroupAdapter(mActivity!!,listFilter)
                                    binding!!.chatStudentRecycler.adapter = chatListGroupAdapter
                                } else {
                                    binding!!.chatStudentRecycler.visibility = View.GONE
                                    binding!!.searchLay.visibility = View.GONE
                                    binding!!.tvChatNoData.visibility = View.VISIBLE
                                }
                            }
                        } else {
                            binding!!.chatStudentRecycler.visibility = View.VISIBLE
                            binding!!.searchLay.visibility = View.VISIBLE
                            binding!!.tvChatNoData.visibility = View.GONE
                            chatListGroupAdapter = ChatListGroupAdapter(mActivity!!,listGroup)
                            binding!!.chatStudentRecycler.adapter = chatListGroupAdapter
                        }
                    }
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun resetAll(){
        binding!!.tvChats.backgroundTintList = ContextCompat.getColorStateList(mActivity!!,R.color.gray)
        binding!!.tvGroup.backgroundTintList = ContextCompat.getColorStateList(mActivity!!,R.color.gray)
        binding!!.tvChats.setTextColor(ContextCompat.getColor(mActivity!!,R.color.primaray_text_color))
        binding!!.tvGroup.setTextColor(ContextCompat.getColor(mActivity!!,R.color.primaray_text_color))
        if (chatType.equals("Chat")){
            binding!!.tvChats.backgroundTintList = ContextCompat.getColorStateList(mActivity!!,R.color.green)
            binding!!.tvChats.setTextColor(ContextCompat.getColor(mActivity!!,R.color.white))
            if (listChats != null && listChats!!.size > 0){
                binding!!.chatStudentRecycler.visibility = View.VISIBLE
                binding!!.searchLay.visibility = View.VISIBLE
                binding!!.tvChatNoData.visibility = View.GONE
                chatListAdapter = ChatListAdapter(mActivity!!,listChats,chatUserId)
                binding!!.chatStudentRecycler.adapter = chatListAdapter
            }else{
                binding!!.chatStudentRecycler.visibility = View.GONE
                binding!!.searchLay.visibility = View.GONE
                binding!!.tvChatNoData.visibility = View.VISIBLE
            }
        }else{
            binding!!.tvGroup.backgroundTintList = ContextCompat.getColorStateList(mActivity!!,R.color.green)
            binding!!.tvGroup.setTextColor(ContextCompat.getColor(mActivity!!,R.color.white))
            if (listGroup != null && listGroup!!.size > 0){
                binding!!.chatStudentRecycler.visibility = View.VISIBLE
                binding!!.searchLay.visibility = View.VISIBLE
                binding!!.tvChatNoData.visibility = View.GONE
                chatListGroupAdapter = ChatListGroupAdapter(mActivity!!,listGroup)
                binding!!.chatStudentRecycler.adapter = chatListGroupAdapter
            }else{
                binding!!.chatStudentRecycler.visibility = View.GONE
                binding!!.searchLay.visibility = View.GONE
                binding!!.tvChatNoData.visibility = View.VISIBLE
            }
        }
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.tv_chats){
            chatType = "Chat"
            resetAll()
        }else if (id == R.id.tv_group){
            chatType = "Group"
            resetAll()
        }
    }
}