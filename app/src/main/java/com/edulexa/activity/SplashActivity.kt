package com.edulexa.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.edulexa.R
import com.edulexa.databinding.ActivitySplashBinding
import com.edulexa.support.Utils

class SplashActivity : AppCompatActivity() {
    var mActivity: Activity? = null
    var binding: ActivitySplashBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        Utils.hideStatusBar(mActivity!!)
        startAnimation()
    }

    private fun startAnimation() {
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_side_slide)
        binding!!.ivSplash.startAnimation(slideAnimation)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(mActivity!!, LoginActivity::class.java))
            finish()
        }, 3000)
    }
}