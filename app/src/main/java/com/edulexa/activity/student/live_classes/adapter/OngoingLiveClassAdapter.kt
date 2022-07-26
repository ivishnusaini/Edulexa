package com.edulexa.activity.student.live_classes.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.live_classes.activity.LiveYoutubeActivity
import com.edulexa.activity.student.live_classes.model.DatumLiveClass
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemLiveClassOngoingBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import us.zoom.sdk.*
import java.text.SimpleDateFormat
import java.util.*

class OngoingLiveClassAdapter(context: Activity, list : List<DatumLiveClass?>?) :
    RecyclerView.Adapter<OngoingLiveClassAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DatumLiveClass?>? = null
    var binding : ItemLiveClassOngoingBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemLiveClassOngoingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            binding!!.tvLiveClassTitle.text = list!!.get(position)!!.getTitle()
            binding!!.tvLiveClassDescription.text = list!!.get(position)!!.getDescs()
            binding!!.tvLiveClassFromDate.text = context!!.getString(R.string.library_student_string_format,"From date : ",list!!.get(position)!!.getFdate())
            binding!!.tvLiveClassToDate.text = context!!.getString(R.string.library_student_string_format,"To date : ",list!!.get(position)!!.getTdate())

            binding!!.tvLiveClassDescription.setOnClickListener(object :View.OnClickListener{
                override fun onClick(p0: View?) {
                    dialogDescription(list!!.get(position)!!.getDescs()!!)
                }
            })

            binding!!.tvJoinLiveClass.setOnClickListener(object :View.OnClickListener{
                override fun onClick(p0: View?) {
                    if (list!!.get(position)!!.getType().equals("youtube")){
                        if (!list!!.get(position)!!.getVideoUrl().equals("")){
                            val bundle = Bundle()
                            bundle.putString(Constants.StudentLiveClass.VIDEO_URL,list!!.get(position)!!.getVideoUrl())
                            context!!.startActivity(Intent(context, LiveYoutubeActivity::class.java).putExtras(bundle))
                        }else Utils.showToastPopup(context!!,context!!.getString(R.string.live_class_student_video_url_validation))
                    }else if (list!!.get(position)!!.getType().equals("zoom")){
                        if (isValidZoomUrl(list!!.get(position)!!.getVideoUrl()!!)){
                            val meetingId = list!!.get(position)!!.getMeetingId()
                            val meetingPassword = list!!.get(position)!!.getPasscode()
                            val preference = Preference().getInstance(context!!)
                            val zoomApiKey = preference!!.getString(Constants.Preference.ZOOM_SDK_KEY)
                            val zoomScretKey = preference.getString(Constants.Preference.ZOOM_SDK_SECRAT)
                            if (zoomApiKey.equals("") || zoomScretKey.equals("")){
                                Utils.showToast(context!!,context!!.getString(R.string.live_class_student_invalid_zoom_key))
                                return
                            }
                            val sdk = ZoomSDK.getInstance()
                            val params = ZoomSDKInitParams()
                            params.appKey = zoomApiKey
                            params.appSecret = zoomScretKey
                            params.domain = "zoom.us"
                            params.enableLog = true
                            val listener: ZoomSDKInitializeListener =
                                object : ZoomSDKInitializeListener {
                                    override fun onZoomSDKInitializeResult(errorCode: Int, internalErrorCode: Int) {
                                        Log.e("errorCode", errorCode.toString())
                                        Utils.showToast(context!!,context!!.getString(R.string.live_class_student_please_wait))
                                        if (!meetingId!!.equals("") && !meetingPassword!!.equals(""))
                                            joinMeeting(meetingId,meetingPassword,list!!.get(position)!!.getId()!!)
                                        else Utils.showToast(context!!,context!!.getString(R.string.live_class_student_meeting_id_blank))
                                    }
                                    override fun onZoomAuthIdentityExpired() {
                                        Log.e("errorCode", "errorCode")
                                    }
                                }
                            try {
                                sdk.initialize(context, listener, params)
                            } catch (e: java.lang.Exception) {
                                e.printStackTrace()
                                sdk.initialize(context, listener, params)
                            }

                        }else updateLiveClassTime(list!!.get(position)!!.getId()!!)
                    }
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    private fun isValidZoomUrl(urlString : String) : Boolean{
        var isValid = false
        if (urlString.contains("zoom.us"))
            isValid = true
        return isValid
    }

    private fun joinMeeting(meetingId : String,meetingPassword : String,liveClassId: String){
        try {
            val meetingService = ZoomSDK.getInstance().meetingService
            val options = JoinMeetingOptions()
            val params = JoinMeetingParams()
            params.displayName = Utils.getStudentLoginResponse(context)!!.getRecord()!!.getUsername()
            params.meetingNo = meetingId
            params.password = meetingPassword
            meetingService?.joinMeetingWithParams(context, params, options)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        updateLiveClassTime(liveClassId)
    }

    private fun updateLiveClassTime(liveClassId : String){
        val calendar = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val currentDate = df.format(calendar)

        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

        if (Utils.isNetworkAvailable(context!!)){
            Utils.hideKeyboard(context!!)

            val branchId = Preference().getInstance(context!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(context)!!.getToken()!!
            val userId = Utils.getStudentUserId(context!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent = APIClientStudent.getRetroFitClientWithNewKeyHeader(context!!, accessToken,branchId,userId).create(
                ApiInterfaceStudent::class.java)

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStudent.STUDENT_ID, Utils.getStudentId(context!!))
            jsonObject.put(Constants.ParamsStudent.STUDENT_SESSION_ID, Utils.getStudentSessionId(context!!))
            jsonObject.put(Constants.ParamsStudent.LIVE_CLASS_ID, liveClassId)
            jsonObject.put(Constants.ParamsStudent.DATE, currentDate)
            jsonObject.put(Constants.ParamsStudent.IN_TIME, currentTime)
            jsonObject.put(Constants.ParamsStudent.OUT_TIME, "")


            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/webservice/updateLiveClassStudent")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.updateLiveClassStudent(requestBody)
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
                        }else {
                            Utils.showToastPopup(context!!, context!!.getString(R.string.response_null_or_empty_validation))
                        }
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(context!!, context!!.getString(R.string.api_response_failure))
                }
            })
        }else Utils.showToastPopup(context!!, context!!.getString(R.string.internet_connection_error))
    }

    private fun dialogDescription(description : String){
        try {
            val dialog = Dialog(context!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_live_class_description)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)
            val tvDescription = dialog.findViewById<TextView>(R.id.tv_dialog_live_class_description)
            tvDescription.text = description
            val tvOk: CardView = dialog.findViewById(R.id.tv_dialog_live_class_ok)
            tvOk.setOnClickListener { v: View? -> dialog.dismiss() }
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    class ViewHolder(binding: ItemLiveClassOngoingBinding) : RecyclerView.ViewHolder(binding.root)
}