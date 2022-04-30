package com.edulexa.activity.select_school.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.edulexa.R
import com.edulexa.activity.SplashActivity
import com.edulexa.activity.select_school.model.FetchBaseUrlResponse
import com.edulexa.api.Communicator
import com.edulexa.api.Constants
import com.edulexa.api.CustomResponseListener
import com.edulexa.databinding.ActivitySelectSchoolBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import com.loopj.android.http.RequestParams
import org.json.JSONObject


class SelectSchoolActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivitySelectSchoolBinding? = null
    var preference: Preference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSchoolBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        checkSchoolCode()
        setUpClickListener()
    }

    private fun checkSchoolCode() {
        Utils.showProgressBar(mActivity!!)
        Handler(Looper.getMainLooper()).postDelayed({
            Utils.hideProgressBar()
            val appType = preference!!.getString(Constants.Preference.APP_TYPE)
            if (appType.equals(Constants.Preference.APP_TYPE_STAFF)) {
                val staffIsLogin = preference!!.getString(Constants.Preference.STAFF_IS_LOGIN)
                if (staffIsLogin.equals(Constants.Preference.STAFF_IS_LOGIN_YES)) {
                    startActivity(Intent(mActivity!!, SplashActivity::class.java))
                    finish()
                } else {
                    binding!!.selectSchoolCodeLay.visibility = View.VISIBLE
                    startAnimation()
                }
            } else {
                val studentIsLogin = preference!!.getString(Constants.Preference.STUDENT_IS_LOGIN)
                if (studentIsLogin.equals(Constants.Preference.STUDENT_IS_LOGIN_YES)) {
                    startActivity(Intent(mActivity!!, SplashActivity::class.java))
                    finish()
                } else {
                    val baseUrlFetchOrNot = preference!!.getString(Constants.Preference.BASE_URL_GET_OR_NOT)
                    if (baseUrlFetchOrNot.equals(Constants.Preference.BASE_URL_GET_OR_NOT_YES)){
                        startActivity(Intent(mActivity!!, SplashActivity::class.java))
                        finish()
                    }else{
                        binding!!.selectSchoolCodeLay.visibility = View.VISIBLE
                        startAnimation()
                    }
                }
            }
        }, 1000)
    }

    private fun setUpClickListener() {
        binding!!.ivExit.setOnClickListener(this)
        binding!!.btnSubmit.setOnClickListener(this)
    }


    private fun startAnimation() {
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.enter_school_code_side_slide)
        binding!!.cvEnterSchoolCode.startAnimation(slideAnimation)
    }

    private fun submit() {
        Utils.hideKeyboard(mActivity!!)
        val schoolCodeStr = binding!!.etSchoolCode.text.toString().trim()
        if (schoolCodeStr.isEmpty())
            Utils.showSnackBar(mActivity!!, getString(R.string.enter_school_code_empty_validation))
        else {
            if (Utils.isNetworkAvailable(mActivity!!)) {
                Utils.showProgressBar(mActivity!!)
                Utils.hideKeyboard(mActivity!!)
                val requestParams = RequestParams()
                try {
                    requestParams.put(Constants.Params.WEB_CODE, schoolCodeStr)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                Utils.printLog("EnterSchoolCode Param:", requestParams.toString())
                val communicator = Communicator()
                communicator.post(
                    1,
                    mActivity!!,
                    Constants.BASE_URL_SCHOOL_CODE,
                    requestParams,
                    object : CustomResponseListener {
                        override fun onResponse(requestCode: Int, response: String?) {
                            Utils.hideProgressBar()
                            try {
                                if (!response.isNullOrEmpty()) {
                                    val jsonObject = JSONObject(response)
                                    val statusCode = jsonObject.optString("status")
                                    if (statusCode.equals("1")) {
                                        val modelResponse = Utils.getObject(
                                            response,
                                            FetchBaseUrlResponse::class.java
                                        ) as FetchBaseUrlResponse
                                        Utils.saveBaseUrlResponse(mActivity!!, modelResponse)
                                        preference!!.putString(Constants.Preference.BASE_URL_GET_OR_NOT,Constants.Preference.BASE_URL_GET_OR_NOT_YES)
                                        var studentBaseUrl = ""
                                        if (modelResponse.getUrl()!!.getUrl()!!.endsWith("/"))
                                            studentBaseUrl = modelResponse.getUrl()!!.getUrl()!!
                                        else studentBaseUrl = modelResponse.getUrl()!!.getUrl()!! + "/"

                                        Utils.saveStudentBaseUrl(mActivity!!, studentBaseUrl)
                                        Utils.saveStudentBackgroundImage(mActivity!!, modelResponse.getUrl()!!.getBackgroundImage()!!)
                                        Utils.saveStudentSchoolName(mActivity!!, modelResponse.getUrl()!!.getSchoolName()!!)
                                        Utils.saveStudentLogoutStatus(mActivity!!, Constants.Preference.LOGOUTSTATUS_VALUE)
                                        Utils.saveSchoolLogo(mActivity!!, modelResponse.getUrl()!!.getSchoolImage()!!)

                                        Constants.BASE_URL_STUDENT = Utils.getStudentBaseUrl(mActivity!!)
                                        Constants.DOMAIN_STUDENT = Constants.BASE_URL_STUDENT + Constants.API
                                        Constants.APIURL_STUDENT = Constants.BASE_URL_STUDENT
                                        Constants.PG_RETURN_URL_STUDENT = Constants.BASE_URL_STUDENT+Constants.API_TRAKNPAY
                                        Constants.PG_RETURN_BULK_URL_STUDENT = Constants.BASE_URL_STUDENT+Constants.API_TRAKNPAY_BALKFEEADD
                                        Constants.PG_RETURN_TRANSPORT_BULK_URL_STUDENT = Constants.BASE_URL_STUDENT+Constants.API_TRAKNPAY_BALKTRANSPORTFEEADD
                                        Constants.BASEURL_WEBVIEW_STUDENT = Constants.BASE_URL_STUDENT+Constants.SITE_USERLOGIN_USERNAME
                                        Constants.BASE_URL_SCHOOL_LOGO_STUDENT = Utils.getSchoolLogo(mActivity!!)
                                        Constants.SCHOOL_NAME = Utils.getStudentSchoolName(mActivity!!)
                                        Constants.IMAGESURL_STUDENT = Constants.BASE_URL_STUDENT

                                        var staffBaseUrl = ""
                                        if (modelResponse.getUrl()!!.getUrl()!!.endsWith("/"))
                                            staffBaseUrl = modelResponse.getUrl()!!.getUrl()!!
                                        else staffBaseUrl =
                                            modelResponse.getUrl()!!.getUrl()!! + "/"
                                        Utils.saveStaffBaseUrl(mActivity!!, staffBaseUrl)
                                        Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF = Utils.getStaffBaseUrl(mActivity!!)
                                        Constants.BASE_URL_STAFF = Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF + Constants.STAFF_API_WEBSERVICE
                                        Constants.BASE_URL_WEBVIEW_STAFF = Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF + Constants.SITE_WEBVIEWLOGIN_USERNAME


                                        Log.e("key",Constants.DOMAIN_STUDENT)
                                        Log.e("key",Constants.APIURL_STUDENT)
                                        Log.e("key",Constants.PG_RETURN_URL_STUDENT)
                                        Log.e("key",Constants.PG_RETURN_BULK_URL_STUDENT)
                                        Log.e("key",Constants.PG_RETURN_TRANSPORT_BULK_URL_STUDENT)
                                        Log.e("key",Constants.BASEURL_WEBVIEW_STUDENT)
                                        Log.e("key",Constants.BASE_URL_SCHOOL_LOGO_STUDENT)
                                        Log.e("key",Constants.BASE_URL_STAFF)
                                        Log.e("key",Constants.BASE_URL_WEBVIEW_STAFF)

                                        startActivity(Intent(mActivity!!, SplashActivity::class.java))
                                        finish()
                                    }else Utils.showToastPopup(mActivity!!,getString(R.string.did_not_fetch_data))
                                } else Utils.showToastPopup(
                                    mActivity!!,
                                    getString(R.string.response_null_or_empty_validation)
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        override fun onFailure(statusCode: Int, error: Throwable?) {
                            Utils.hideProgressBar()
                            Utils.showToastPopup(
                                mActivity!!,
                                getString(R.string.api_response_failure)
                            )
                        }

                    })
            } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))
        }
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_exit)
            onBackPressed()
        else if (id == R.id.btn_submit)
            submit()
    }
}