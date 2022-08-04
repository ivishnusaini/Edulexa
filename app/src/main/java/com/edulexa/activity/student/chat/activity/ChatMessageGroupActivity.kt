package com.edulexa.activity.student.chat.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.edulexa.R
import com.edulexa.activity.student.chat.adapter.ChatMessageAdapter
import com.edulexa.activity.student.chat.adapter.ChatMessageGroupAdapter
import com.edulexa.activity.student.chat.model.chat_record.ChatRecord
import com.edulexa.activity.student.chat.model.chat_record.ChatRecordResponse
import com.edulexa.activity.student.chat.model.chat_record.custom.ChatDateM
import com.edulexa.activity.student.chat.model.chat_record.custom.ChatMessageModel
import com.edulexa.activity.student.chat.model.chat_update.ChatUpdateResponse
import com.edulexa.activity.student.chat.model.group.ChatGroup
import com.edulexa.activity.student.chat.model.group.ChatRecordGroupResponse
import com.edulexa.activity.student.chat.model.group.ChatUserGroup
import com.edulexa.activity.student.chat.model.send_message.SendMessageResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityChatMessageGroupStudentBinding
import com.edulexa.databinding.ActivityChatMessageStudentBinding
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

class ChatMessageGroupActivity : AppCompatActivity(),View.OnClickListener{
    var mActivity: Activity? = null
    var binding: ActivityChatMessageGroupStudentBinding? = null
    private var groupId : String = ""
    private var titleStr : String = ""
    private lateinit var layoutManager : LinearLayoutManager

    val messageList: List<ChatMessageModel> = ArrayList()
    val messageObjectList: List<Any> = ArrayList()

    lateinit var chatMessageGroupAdapter: ChatMessageGroupAdapter
    var handler = Handler(Looper.getMainLooper())
    var lastId = ""
    var chatMemberId = ""
    var pageNo = 0
    var paginationFlag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatMessageGroupStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        getBundleData()
        setUpData()
        getChatRecord()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.ivSendMessage.setOnClickListener(this)
        binding!!.tvGroupInfo.setOnClickListener(this)
    }

    private fun getBundleData() {
        try {
            val bundle = intent.extras
            titleStr = bundle!!.getString(Constants.StudentChat.CHAT_NAME)!!
            groupId = bundle.getString(Constants.StudentChat.GROUP_ID)!!

            binding!!.tvTitle.text = titleStr
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun setUpData(){
        (Objects.requireNonNull<RecyclerView.ItemAnimator>(binding!!.chatMessageRecycler.getItemAnimator()) as SimpleItemAnimator).supportsChangeAnimations =
            false
        layoutManager = LinearLayoutManager(mActivity!!, RecyclerView.VERTICAL, false)
        binding!!.chatMessageRecycler.layoutManager = layoutManager
        binding!!.chatMessageRecycler.addOnLayoutChangeListener { view: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int ->
            if (bottom < oldBottom) {
                binding!!.chatMessageRecycler.scrollBy(0, oldBottom - bottom)
            }
        }
    }
    private fun getChatRecord() {
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
            jsonObject.put(Constants.ParamsStudent.GROUP_ID, groupId)
            jsonObject.put(Constants.ParamsStudent.PAGENO, pageNo)

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/webservice/getGroupChatRecord")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getGroupChatRecord(requestBody)
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
                                    ChatRecordGroupResponse::class.java
                                ) as ChatRecordGroupResponse
                                if (modelResponse.getData() != null) {
                                    if (modelResponse.getData()!!
                                            .getChatList() != null && modelResponse.getData()!!
                                            .getChatList()!!.isNotEmpty()
                                    ) {
                                        if (modelResponse.getData()!!.getChatUser() != null)
                                            chatMemberId = modelResponse.getData()!!.getChatUser()!!.getChatMemberId()!!

                                        binding!!.chatMessageRecycler.visibility = View.VISIBLE
                                        binding!!.tvChatNoData.visibility = View.GONE
                                        (messageList as ArrayList<ChatMessageModel>).clear()
                                        (messageObjectList as ArrayList<Any>).clear()
                                        for (model in modelResponse.getData()!!.getChatList()!!) {
                                            val chatMessageModel = ChatMessageModel()
                                            chatMessageModel.setChatUserId(model!!.getGroupMemberId()!!)
                                            chatMessageModel.setIsFirst(model.getIsFirst()!!)
                                            chatMessageModel.setLastId(model.getId()!!)
                                            chatMessageModel.setMessage(model.getMessage()!!)
                                            chatMessageModel.setTime(model.getCreatedAt()!!)
                                            chatMessageModel.setStatus("save")
                                            messageList.add(chatMessageModel)
                                        }
                                        val objects: List<Any> = ArrayList()
                                        (objects as ArrayList<Any>).addAll(messageList)
                                        getFormattedList(objects)
                                        chatMessageGroupAdapter = ChatMessageGroupAdapter(
                                            mActivity!!,
                                            messageObjectList,
                                            chatMemberId
                                        )
                                        binding!!.chatMessageRecycler.adapter = chatMessageGroupAdapter
                                        binding!!.chatMessageRecycler.scrollToPosition(chatMessageGroupAdapter!!.getItemCount()-1)
                                        showConnectedMessage(
                                            modelResponse.getData()!!.getChatList()
                                        )
                                        lastId = getLastMessageId()
                                        updateChatStart()
                                    } else {
                                        binding!!.chatMessageRecycler.visibility = View.GONE
                                        binding!!.tvChatNoData.visibility = View.VISIBLE
                                    }
                                    if (modelResponse.getData()!!.getGroupUsers() != null && modelResponse.getData()!!.getGroupUsers()!!.isNotEmpty()) {
                                        Constants.AppSaveData.listGroupUsers = modelResponse.getData()!!.getGroupUsers()
                                        binding!!.tvGroupInfo.visibility = View.VISIBLE
                                    }else binding!!.tvGroupInfo.visibility = View.GONE
                                } else {
                                    binding!!.chatMessageRecycler.visibility = View.GONE
                                    binding!!.tvChatNoData.visibility = View.VISIBLE
                                }
                            } else {
                                binding!!.chatMessageRecycler.visibility = View.GONE
                                binding!!.tvChatNoData.visibility = View.VISIBLE
                            }
                        } else {
                            Utils.showToastPopup(
                                mActivity!!,
                                getString(R.string.response_null_or_empty_validation)
                            )
                            binding!!.chatMessageRecycler.visibility = View.GONE
                            binding!!.tvChatNoData.visibility = View.VISIBLE
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        binding!!.chatMessageRecycler.visibility = View.GONE
                        binding!!.tvChatNoData.visibility = View.VISIBLE
                    }
                    Handler().postDelayed({ pagination() }, 1000)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.chatMessageRecycler.visibility = View.GONE
                    binding!!.tvChatNoData.visibility = View.VISIBLE
                }
            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))
    }

    private fun getChatRecordPagination() {
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
            jsonObject.put(Constants.ParamsStudent.GROUP_ID, groupId)
            jsonObject.put(Constants.ParamsStudent.PAGENO, pageNo)

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/webservice/getGroupChatRecord")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getGroupChatRecord(requestBody)
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
                                    ChatRecordGroupResponse::class.java
                                ) as ChatRecordGroupResponse
                                if (modelResponse.getData() != null) {
                                    if (modelResponse.getData()!!
                                            .getChatList() != null && modelResponse.getData()!!
                                            .getChatList()!!.isNotEmpty()
                                    ) {
                                        if (modelResponse.getData()!!.getChatUser() != null)
                                            chatMemberId = modelResponse.getData()!!.getChatUser()!!.getChatMemberId()!!

                                        binding!!.chatMessageRecycler.visibility = View.VISIBLE
                                        binding!!.tvChatNoData.visibility = View.GONE
                                        (messageList as ArrayList<ChatMessageModel>).clear()
                                        (messageObjectList as ArrayList<Any>).clear()
                                        for (model in modelResponse.getData()!!.getChatList()!!) {
                                            val chatMessageModel = ChatMessageModel()
                                            chatMessageModel.setChatUserId(model!!.getGroupMemberId()!!)
                                            chatMessageModel.setIsFirst(model.getIsFirst()!!)
                                            chatMessageModel.setLastId(model.getId()!!)
                                            chatMessageModel.setMessage(model.getMessage()!!)
                                            chatMessageModel.setTime(model.getCreatedAt()!!)
                                            chatMessageModel.setStatus("save")
                                            messageList.add(chatMessageModel)
                                        }
                                        val objects: List<Any> = ArrayList()
                                        (objects as ArrayList<Any>).addAll(messageList)
                                        getFormattedListPagination(objects)
                                        chatMessageGroupAdapter.notifyItemInserted(0)
                                        val lastVisiblePosition =
                                            layoutManager.findLastVisibleItemPosition()
                                        binding!!.chatMessageRecycler.smoothScrollToPosition(
                                            messageList.size + lastVisiblePosition
                                        )
                                        showConnectedMessage(
                                            modelResponse.getData()!!.getChatList()
                                        )
                                        paginationFlag = true
                                    } else {
                                        binding!!.chatMessageRecycler.visibility = View.GONE
                                        binding!!.tvChatNoData.visibility = View.VISIBLE
                                    }
                                } else {
                                    binding!!.chatMessageRecycler.visibility = View.GONE
                                    binding!!.tvChatNoData.visibility = View.VISIBLE
                                }
                            } else {
                                binding!!.chatMessageRecycler.visibility = View.GONE
                                binding!!.tvChatNoData.visibility = View.VISIBLE
                            }
                        } else {
                            Utils.showToastPopup(
                                mActivity!!,
                                getString(R.string.response_null_or_empty_validation)
                            )
                            binding!!.chatMessageRecycler.visibility = View.GONE
                            binding!!.tvChatNoData.visibility = View.VISIBLE
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        binding!!.chatMessageRecycler.visibility = View.GONE
                        binding!!.tvChatNoData.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.chatMessageRecycler.visibility = View.GONE
                    binding!!.tvChatNoData.visibility = View.VISIBLE
                }
            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))
    }

    private fun getFormattedList(objects: List<Any>) {
        if (objects.size > 0) {
            if (objects.size == 1 && (objects.get(0) as ChatMessageModel).getIsFirst().equals("1"))
                (messageObjectList as ArrayList<Any>).add(objects.get(0))
            else {
                val date = getDate((objects.get(0) as ChatMessageModel).getTime())
                (messageObjectList as ArrayList<Any>).add(ChatDateM(date))
                messageObjectList.add(objects.get(0))
                for (i in 1..objects.size-1) {
                    val date1 = getDate((objects.get(i) as ChatMessageModel).getTime())
                    val date2 = getDate((objects.get(i - 1) as ChatMessageModel).getTime())
                    if (!date1.equals(date2)) {
                        messageObjectList.add(ChatDateM(getDate((objects.get(i) as ChatMessageModel).getTime())))
                        messageObjectList.add(objects.get(i))
                    } else messageObjectList.add(objects.get(i))
                }
            }
        }
    }

    private fun getFormattedListPagination(objects: List<Any>) {
        if (objects.isNotEmpty()) {
            val date = getDate((objects.get(0) as ChatMessageModel).getTime())
            (messageObjectList as ArrayList<Any>).add(0, objects[0])
            if (objects.size > 1)
                messageObjectList.add(0, ChatDateM(date))
            for (i in 1 until objects.size) {
                val date1 = getDate((objects[i] as ChatMessageModel).getTime())
                val date2 = getDate((objects.get(i - 1) as ChatMessageModel).getTime())
                if (date1 != date2) {
                    messageObjectList.add(0, objects[i])
                    messageObjectList.add(
                        0,
                        ChatDateM(getDate((objects[0] as ChatMessageModel).getTime()))
                    )
                } else messageObjectList.add(0, objects[i])
            }
        }
    }

    private fun getDate(dateStr: String): String {
        val tokenizer = StringTokenizer(dateStr, " ")
        return tokenizer.nextToken()
    }
    private fun showConnectedMessage(list: List<ChatGroup?>?) {
        var flag = false
        var message = ""
        for (model in list!!) {
            if (model!!.getIsFirst().equals("1")) {
                flag = true
                message = model.getMessage()!!
                break
            }
        }
        if (flag) {
            binding!!.tvConnectedMessage.visibility = View.VISIBLE
            binding!!.tvConnectedMessage.text = message
        }
    }

    private fun getLastMessageId(): String {
        var lastId = ""
        try {
            if (messageList.size > 0) {
                var index = -1
                for (i in messageList.indices) {
                    if (messageList.get(i).getStatus().equals("save"))
                        index = i
                }
                if (index != -1)
                    lastId = messageList.get(index).getLastId()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return lastId
    }

    private fun updateChatStart() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                getUpdateChatData()
                handler.postDelayed(this, 3000)
            }
        }, 3000)
    }

    private fun pagination() {
        binding!!.chatMessageRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemno: Int = layoutManager!!.findFirstVisibleItemPosition()
                if (itemno == 0 && paginationFlag) {
                    paginationFlag = false
                    pageNo++
                    handler.removeCallbacksAndMessages(null)
                    getChatRecordPagination()
                }
            }
        })
    }


    private fun getUpdateChatData() {
        if (Utils.isNetworkAvailable(mActivity!!)) {
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
            jsonObject.put(Constants.ParamsStudent.GROUP_MEMBER_ID, chatMemberId)
            jsonObject.put(Constants.ParamsStudent.GROUP_ID, groupId)
            jsonObject.put(Constants.ParamsStudent.LAST_CHAT_ID, lastId)

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/webservice/groupChatUpdate")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getGroupChatUpdate(requestBody)
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
                                    ChatUpdateResponse::class.java
                                ) as ChatUpdateResponse
                                if (modelResponse.getData() != null) {
                                    if (modelResponse.getData()!!.getUserNewChat() != null) {
                                        if (modelResponse.getData()!!.getUserNewChat()!!
                                                .getUpdatedChat() != null && modelResponse.getData()!!
                                                .getUserNewChat()!!.getUpdatedChat()!!.isNotEmpty()
                                        ) {
                                            for (model in modelResponse.getData()!!.getUserNewChat()!!.getUpdatedChat()!!) {
                                                (messageList as ArrayList<ChatMessageModel>).clear()
                                                val chatMessageModel = ChatMessageModel()
                                                chatMessageModel.setChatUserId(model!!.getChatMemberId()!!)
                                                chatMessageModel.setIsFirst(model.getIsFirst()!!)
                                                chatMessageModel.setLastId(model.getId()!!)
                                                chatMessageModel.setMessage(model.getMessage()!!)
                                                chatMessageModel.setTime(model.getTime()!!)
                                                chatMessageModel.setName(model.getName()!!)
                                                chatMessageModel.setStatus("save")
                                                messageList.add(chatMessageModel)
                                                getInsertLastMessageDate(model.getCreatedAt()!!)
                                            }
                                            chatMessageGroupAdapter.notifyItemInserted(
                                                messageObjectList.size - 1
                                            )
                                            binding!!.chatMessageRecycler.smoothScrollToPosition(
                                                messageObjectList.size
                                            )
                                            lastId = getLastMessageId()
                                        }
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                }
            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    private fun getInsertLastMessageDate(currentDate: String) {
        val objects: List<Any> = ArrayList()
        (objects as ArrayList<Any>).addAll(messageList)
        if (messageObjectList.isNotEmpty()) {
            val index = messageObjectList.size - 1
            val date = getDate((messageObjectList.get(index) as ChatMessageModel).getTime())
            if (getDate(currentDate) == date) {
                if (objects.isNotEmpty())
                    (messageObjectList as ArrayList<Any>).add(objects.get(0))
            } else {
                (messageObjectList as ArrayList<Any>).add(ChatDateM(getDate(currentDate)))
                messageObjectList.add(objects.get(0))
            }
        }
    }

    private fun sendMessage() {
        val messageStr = binding!!.etMessage.text.toString().trim()
        if (messageStr.isEmpty())
            Utils.showToast(mActivity!!, "Please enter message")
        else {
            handler.removeCallbacksAndMessages(null)
            Utils.hideKeyboard(mActivity!!)
            binding!!.etMessage.setText("")
            if (Utils.isNetworkAvailable(mActivity!!)) {
                Utils.showProgressBar(mActivity!!)
                Utils.hideKeyboard(mActivity!!)
                val branchId =
                    Preference().getInstance(mActivity!!)!!
                        .getString(Constants.Preference.BRANCH_ID)!!
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
                jsonObject.put(Constants.ParamsStudent.GROUP_ID, groupId)
                jsonObject.put(Constants.ParamsStudent.GROUP_MEMBER_ID, chatMemberId)
                jsonObject.put(Constants.ParamsStudent.MESSAGE, messageStr)

                val requestBody: RequestBody = RequestBody.create(
                    MediaType.parse("application/json; charset=utf-8"),
                    jsonObject.toString()
                )

                Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/webservice/newGroupMessage")

                val call: Call<ResponseBody> = apiInterfaceWithHeader.sendNewGroupMessage(requestBody)
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
                                        SendMessageResponse::class.java
                                    ) as SendMessageResponse
                                    if (modelResponse.getData() != null) {
                                        if (modelResponse.getData()!!.getUserLastChat() != null) {
                                            lastId = modelResponse.getData()!!.getLastInsertId()
                                                .toString()
                                            (messageList as ArrayList<ChatMessageModel>).clear()
                                            val userLastChat =
                                                modelResponse.getData()!!.getUserLastChat()
                                            val chatMessageModel = ChatMessageModel()
                                            chatMessageModel.setChatUserId(userLastChat!!.getChatUserId()!!)
                                            chatMessageModel.setIsFirst(userLastChat.getIsFirst()!!)
                                            chatMessageModel.setLastId(userLastChat.getId()!!)
                                            chatMessageModel.setMessage(userLastChat.getMessage()!!)
                                            chatMessageModel.setTime(userLastChat.getTime()!!)
                                            chatMessageModel.setStatus("save")
                                            messageList.add(chatMessageModel)
                                            getInsertLastMessageDate(userLastChat.getCreatedAt()!!)
                                            chatMessageGroupAdapter.notifyItemInserted(
                                                messageObjectList.size - 1
                                            )
                                            binding!!.chatMessageRecycler.smoothScrollToPosition(
                                                messageObjectList.size
                                            )
                                        }
                                        updateChatStart()
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Utils.hideProgressBar()
                    }
                })
            } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

        }
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.iv_send_message)
            sendMessage()
        else if (id == R.id.tv_group_info){
            val bundle = Bundle()
            bundle.putString(Constants.StudentChat.CHAT_NAME,titleStr)
            startActivity(Intent(mActivity, GroupInfoActivity::class.java).putExtras(bundle))
        }
    }

}