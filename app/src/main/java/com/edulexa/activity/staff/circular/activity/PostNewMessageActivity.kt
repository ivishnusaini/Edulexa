package com.edulexa.activity.staff.circular.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.edulexa.R
import com.edulexa.activity.staff.circular.adapter.RoleTypeSpinnerAdapter
import com.edulexa.activity.staff.circular.model.role_type.ClassListRole
import com.edulexa.activity.staff.circular.model.role_type.Role
import com.edulexa.activity.staff.circular.model.role_type.RoleTypeResponse
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityPostNewMessageStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostNewMessageActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityPostNewMessageStaffBinding? = null
    var preference: Preference? = null
    var listRole: List<Role?>? = ArrayList()
    var classListSpinn: List<ClassListRole?>? = ArrayList()
    var roleTypeSpinnerAdapter: RoleTypeSpinnerAdapter? = null
    var classId = ""
    var messageType = "group"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostNewMessageStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        getRoleType()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.tvGroup.setOnClickListener(this)
        binding!!.tvIndividual.setOnClickListener(this)
        binding!!.tvClass.setOnClickListener(this)
    }

    private fun getRoleType(){
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

            val apiInterfaceWithHeader: ApiInterfaceStaff =
                APIClientStaff.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    Utils.getStaffToken(mActivity!!),
                    Utils.getStaffId(mActivity!!),
                    dbId!!
                ).create(ApiInterfaceStaff::class.java)


            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
            val requestBody = builder.build()


            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getRolesClassListForNotifications")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getRolesClassListForNotifications(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            val responseJsonObject = JSONObject(responseStr)
                            val classlistJsonArr = responseJsonObject.optJSONArray("classlist")
                            if (classlistJsonArr != null && classlistJsonArr.length() > 0) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    RoleTypeResponse::class.java
                                ) as RoleTypeResponse
                                if (modelResponse.getClasslist()!!.isNotEmpty()) {
                                    val classListRole = ClassListRole()
                                    classListRole.setClass_("Select class")
                                    classListSpinn = modelResponse.getClasslist()
                                    (classListSpinn as ArrayList<ClassListRole?>).add(0, classListRole)
                                    roleTypeSpinnerAdapter =
                                        RoleTypeSpinnerAdapter(mActivity!!, classListSpinn)
                                    binding!!.classSpinn.adapter = roleTypeSpinnerAdapter
                                }
                            } else {
                                val message = responseJsonObject.optString("message")
                                if (!message.isEmpty())
                                    Utils.showToastPopup(mActivity!!, message)
                                else Utils.showToastPopup(
                                    mActivity!!,
                                    getString(R.string.did_not_fetch_data)
                                )
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

    private fun resetAll(type :String){
        messageType = type
        binding!!.tvGroup.setBackgroundResource(R.drawable.bg_border_5)
        binding!!.tvGroup.setTextColor(ContextCompat.getColor(mActivity!!,R.color.primaray_text_color))
        binding!!.tvIndividual.setBackgroundResource(R.drawable.bg_border_5)
        binding!!.tvIndividual.setTextColor(ContextCompat.getColor(mActivity!!,R.color.primaray_text_color))
        binding!!.tvClass.setBackgroundResource(R.drawable.bg_border_5)
        binding!!.tvClass.setTextColor(ContextCompat.getColor(mActivity!!,R.color.primaray_text_color))
        binding!!.groupRecycler.visibility = View.GONE
        binding!!.classSectionLay.visibility = View.GONE
        when (type) {
            "group" -> {
                binding!!.groupRecycler.visibility = View.VISIBLE
                binding!!.classSectionLay.visibility = View.GONE
                binding!!.tvGroup.setBackgroundResource(R.drawable.bg_button_5)
                binding!!.tvGroup.setTextColor(ContextCompat.getColor(mActivity!!,R.color.white))
            }
            "individual" -> {
                binding!!.tvIndividual.setBackgroundResource(R.drawable.bg_button_5)
                binding!!.tvIndividual.setTextColor(ContextCompat.getColor(mActivity!!,R.color.primaray_text_color))
            }
            "class" -> {
                binding!!.groupRecycler.visibility = View.GONE
                binding!!.classSectionLay.visibility = View.VISIBLE
                binding!!.tvClass.setBackgroundResource(R.drawable.bg_button_5)
                binding!!.tvClass.setTextColor(ContextCompat.getColor(mActivity!!,R.color.primaray_text_color))
            }
        }
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.tv_group)
            resetAll("group")
        else if (id == R.id.tv_individual)
            resetAll("individual")
        else if (id == R.id.tv_class)
            resetAll("class")
    }
}