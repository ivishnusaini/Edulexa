package com.edulexa.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.edulexa.R
import com.edulexa.activity.staff.dashboard.activity.DashboardStaffActivity
import com.edulexa.activity.student.dashboard.activity.DashboardStudentActivity
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivitySplashBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import java.util.*

class SplashActivity : AppCompatActivity() {
    var mActivity: Activity? = null
    var binding: ActivitySplashBinding? = null
    var preference : Preference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        Utils.hideStatusBar(mActivity!!)
        setBaseUrl()
        setLocale()
        setUpData()
        startAnimation()
    }

    private fun setBaseUrl(){
        try {
            if (Utils.getStudentBaseUrl(mActivity!!).endsWith("/"))
                Constants.BASE_URL_STUDENT = Utils.getStudentBaseUrl(mActivity!!)
            else Constants.BASE_URL_STUDENT = Utils.getStudentBaseUrl(mActivity!!) + "/"
            if (Constants.BASE_URL_STUDENT.endsWith(("/")))
                Constants.IMAGESURL_STUDENT = Constants.BASE_URL_STUDENT
            else Constants.IMAGESURL_STUDENT = Constants.BASE_URL_STUDENT + "/"
            Constants.BASE_URL_SCHOOL_LOGO_STUDENT = Utils.getSchoolLogo(mActivity!!)

            if (Utils.getStaffBaseUrl(mActivity!!).endsWith("/"))
                Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF = Utils.getStaffBaseUrl(mActivity!!)
            else Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF = Utils.getStaffBaseUrl(mActivity!!) + "/"
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    fun setLocale() {
        try {
            var localeName: String?
            val appType = preference!!.getString(Constants.Preference.APP_TYPE)
            if (appType.equals(Constants.Preference.APP_TYPE_STAFF))
                localeName = preference!!.getString(Constants.Preference.LANG_CODE_STAFF)
            else localeName = preference!!.getString(Constants.Preference.LANG_CODE_STUDENT)

            if (localeName == null || localeName == "") localeName = "en"
            val myLocale = Locale(localeName)
            Locale.setDefault(myLocale)
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = myLocale
            res.updateConfiguration(conf, dm)
            Log.e("Status", "Locale updated!")
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun setUpData(){
        try{
            Utils.setImageUsingGlide(mActivity,Utils.getSchoolLogo(mActivity!!),binding!!.ivSplashLogoBg)
            Utils.setImageUsingGlide(mActivity,Utils.getSchoolLogo(mActivity!!),binding!!.ivSplashLogo)
        }catch (e : Exception){
            e.printStackTrace()
        }

    }

    private fun startAnimation() {
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_side_slide)
        binding!!.ivSplashLogo.startAnimation(slideAnimation)
        goToLoginOrDashboardScreen()
    }

    private fun goToLoginOrDashboardScreen(){
        Handler(Looper.getMainLooper()).postDelayed({
            val appType = preference!!.getString(Constants.Preference.APP_TYPE)
            if (appType.equals(Constants.Preference.APP_TYPE_STAFF)){
                val staffIsLogin = preference!!.getString(Constants.Preference.STAFF_IS_LOGIN)
                if (staffIsLogin.equals(Constants.Preference.STAFF_IS_LOGIN_YES)){
                    startActivity(Intent(mActivity!!, DashboardStaffActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(mActivity!!, LoginActivity::class.java))
                    finish()
                }
            }else{
                val studentIsLogin = preference!!.getString(Constants.Preference.STUDENT_IS_LOGIN)
                if (studentIsLogin.equals(Constants.Preference.STUDENT_IS_LOGIN_YES)){
                    startActivity(Intent(mActivity!!, DashboardStudentActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(mActivity!!, LoginActivity::class.java))
                    finish()
                }
            }
        }, 3000)
    }

}