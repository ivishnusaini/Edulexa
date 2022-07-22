package com.edulexa.activity.student.online_exam.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.edulexa.R
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityOnlineExamWebviewStudentBinding
import com.edulexa.support.Utils

class OnlineExamWebviewActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityOnlineExamWebviewStudentBinding? = null
    var webViewUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineExamWebviewStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        getBundleData()
        setUpData()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getBundleData() {
        try {
            val bundle = intent.extras
            webViewUrl = bundle!!.getString(Constants.StudentOnlineExam.WEBVIEWURL)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpData() {
        Utils.showProgressBar(mActivity!!)
        binding!!.onlineExamWebView.webViewClient = WebViewClient()
        binding!!.onlineExamWebView.getSettings().setLoadWithOverviewMode(true)
        binding!!.onlineExamWebView.getSettings().setUseWideViewPort(true)
        binding!!.onlineExamWebView.loadUrl(webViewUrl)
        binding!!.onlineExamWebView.clearCache(true)
        binding!!.onlineExamWebView.clearHistory()
        binding!!.onlineExamWebView.getSettings().setJavaScriptEnabled(true)
        binding!!.onlineExamWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true)

        binding!!.onlineExamWebView.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                if (view.title == "") view.reload() else Utils.hideProgressBar()
            }
        })
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}