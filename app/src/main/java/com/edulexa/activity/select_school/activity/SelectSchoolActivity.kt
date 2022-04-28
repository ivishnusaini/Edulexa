package com.edulexa.activity.select_school.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.edulexa.R
import com.edulexa.activity.LoginActivity
import com.edulexa.activity.select_school.model.FetchBaseUrlResponse
import com.edulexa.api.Communicator
import com.edulexa.api.Constants
import com.edulexa.api.CustomResponseListener
import com.edulexa.databinding.ActivitySelectSchoolBinding
import com.edulexa.support.Utils
import com.loopj.android.http.RequestParams
import org.json.JSONObject


class SelectSchoolActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivitySelectSchoolBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSchoolBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        startAnimation()
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
            startActivity(Intent(mActivity!!, LoginActivity::class.java))
            finish()
            if (Utils.isNetworkAvailable(mActivity!!)){
                Utils.showProgressBar(mActivity!!)
                Utils.hideKeyboard(mActivity!!)
                val requestParams = RequestParams()
                try{
                    requestParams.put(Constants.Params.WEB_CODE,schoolCodeStr)
                }catch (e : Exception){
                    e.printStackTrace()
                }
                Utils.printLog("EnterSchoolCode Param:",requestParams.toString())
                val communicator = Communicator()
                communicator.post(1,mActivity!!,Constants.BASE_URL_SCHOOL_CODE,requestParams,object :CustomResponseListener{
                    override fun onResponse(requestCode: Int, response: String?) {
                        Utils.hideProgressBar()
                        try{
                            if (!response.isNullOrEmpty()){
                                val jsonObject = JSONObject(response)
                                val statusCode = jsonObject.optString("status")
                                if (statusCode.equals("1")){
                                    val modelResponse = Utils.getObject(
                                        response,
                                        FetchBaseUrlResponse::class.java
                                    ) as FetchBaseUrlResponse
                                }
                            }else Utils.showToastPopup(
                                mActivity!!,
                                getString(R.string.response_null_or_empty_validation)
                            )
                        }catch (e : Exception){
                            e.printStackTrace()
                        }
                    }

                    override fun onFailure(statusCode: Int, error: Throwable?) {
                        Utils.hideProgressBar()
                        Utils.showToastPopup(mActivity!!,getString(R.string.api_response_failure))
                    }

                })
            }else Utils.showToastPopup(mActivity!!,getString(R.string.internet_connection_error))
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