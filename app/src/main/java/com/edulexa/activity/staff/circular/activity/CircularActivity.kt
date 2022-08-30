package com.edulexa.activity.staff.circular.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.edulexa.R
import com.edulexa.activity.staff.circular.adapter.CircularListAdapter
import com.edulexa.activity.staff.dashboard.model.notifications.DatumNotification
import com.edulexa.activity.staff.dashboard.model.notifications.NotificationResponse
import com.edulexa.activity.staff.homework.adapter.HomeworkListAdapter
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityCircularStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class CircularActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityCircularStaffBinding? = null
    var preference: Preference? = null

    var myCalendar: Calendar? = null
    var publishDateSetListener: DatePickerDialog.OnDateSetListener? = null

    val listCircular : List<DatumNotification?>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCircularStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    override fun onResume() {
        super.onResume()
        getCircularList()
    }

    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        setUpHomeworkDate()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.tvDate.setOnClickListener(this)
        binding!!.ivAdd.setOnClickListener(this)
    }

    private fun setUpHomeworkDate() {
        myCalendar = Calendar.getInstance()
        publishDateSetListener =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                myCalendar!!.set(Calendar.YEAR, year)
                myCalendar!!.set(Calendar.MONTH, monthOfYear)
                myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabelFromData(binding!!.tvDate)
            }
    }


    private fun updateLabelFromData(textView: TextView) {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textView.text = sdf.format(myCalendar!!.time)
    }

    private fun getCircularList(){
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
            builder.addFormDataPart(Constants.ParamsStaff.MODULE_ID, Utils.getStaffId(mActivity!!))
            builder.addFormDataPart(
                Constants.ParamsStaff.ROLE_ID,
                Utils.getStaffRoleId(mActivity!!)
            )
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
            builder.addFormDataPart(Constants.ParamsStaff.MODULE_ID, Constants.MODULE_ID_VALUE)
            builder.addFormDataPart(Constants.ParamsStaff.ROLE_ID, Utils.getStaffRoleId(mActivity!!))
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
                                    if (Utils.getStaffRoleId(mActivity!!) == "1" || Utils.getStaffRoleId(mActivity!!) == "7"){
                                        binding!!.recyclerView.visibility = View.VISIBLE
                                        binding!!.tvNoData.visibility = View.GONE
                                        (listCircular as ArrayList<DatumNotification?>).clear()
                                        listCircular.addAll(modelResponse.getNotification()!!.getData()!!)
                                        binding!!.recyclerView.adapter = CircularListAdapter(mActivity!!,listCircular)
                                    }else{
                                        if (getOwnStaffList(modelResponse.getNotification()!!.getData()!!).isNotEmpty()){
                                            binding!!.recyclerView.visibility = View.VISIBLE
                                            binding!!.tvNoData.visibility = View.GONE
                                            binding!!.recyclerView.adapter = CircularListAdapter(mActivity!!,listCircular)
                                        }else{
                                            binding!!.recyclerView.visibility = View.GONE
                                            binding!!.tvNoData.visibility = View.VISIBLE
                                        }
                                    }
                                } else {
                                    binding!!.recyclerView.visibility = View.GONE
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
                }

            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    private fun getOwnStaffList(list : List<DatumNotification?>) : List<DatumNotification?>{
        (listCircular as ArrayList<DatumNotification?>).clear()
        for (i in list.indices) {
            if (Utils.getStaffRoleId(mActivity!!) == list[i]!!.getCreatedId())
                listCircular.add(list[i])
        }
        return listCircular;
    }

    fun deleNotification(notificationId : String){

    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.tv_date) {
            binding!!.tvDate.text =
                getString(R.string.circular_staff_publish_date)
            Utils.hideKeyboard(mActivity!!)
            val fromDateDialog = DatePickerDialog(
                mActivity!!,
                publishDateSetListener,
                myCalendar!!.get(Calendar.YEAR),
                myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            )
            fromDateDialog.show()
        }else if (id == R.id.iv_add)
            startActivity(Intent(mActivity!!,PostNewMessageActivity::class.java))
    }


}