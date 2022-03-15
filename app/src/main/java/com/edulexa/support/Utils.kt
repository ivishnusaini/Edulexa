package com.edulexa.support

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.edulexa.BuildConfig
import com.edulexa.R
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.util.regex.Pattern

class Utils {
    companion object {
        fun hideStatusBar(context: Activity) {
            @Suppress("DEPRECATION")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                context.window.insetsController?.hide(WindowInsets.Type.statusBars())
            } else {
                context.window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }
        }

        fun clearGlide(context: Activity?, imageView: ImageView?) {
            Glide.with(context!!)
                .clear(imageView!!)
        }

        fun setImageUsingGlide(context: Activity?, url: String?, imageView: ImageView?) {
            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.app_icon)
                .error(R.drawable.app_icon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform()
            Glide.with(context!!)
                .load(url)
                .apply(options)
                .into(imageView!!)
        }

        fun hideKeyboard(context: Activity) {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = context.currentFocus
            if (view == null)
                view = View(context)
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun isNetworkAvailable(context: Activity): Boolean {
            val connectivityManager =
                context.getSystemService(Activity.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                } else {
                    TODO("VERSION.SDK_INT < M")
                }
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
            return false
        }

        fun startActivity(activity: Activity, aClass: Class<*>?) {
            activity.startActivity(Intent(activity, aClass))
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        fun startActivityFinish(activity: Activity, aClass: Class<*>?) {
            activity.startActivity(Intent(activity, aClass))
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            activity.finish()
        }

        fun startActivityBundle(activity: Activity, aClass: Class<*>?, bundle: Bundle?) {
            activity.startActivity(Intent(activity, aClass).putExtras(bundle!!))
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        fun isValidEmail(email: String?): Boolean {
            val EMAIL_PATTERN = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
            val pattern = Pattern.compile(EMAIL_PATTERN)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }

        fun getObject(name: String, aClass: Class<*>): Any {
            val gson = Gson()
            return gson.fromJson(name, aClass)
        }

        fun printLog(key: String, value: String) {
            if (BuildConfig.DEBUG) {
                Log.e(key, value)
            }
        }

        fun showToastPopup(context: Activity, message: String) {
            try {
                val dialog = Dialog(context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.dialog_validation_popup)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCanceledOnTouchOutside(false)
                val tvMessage = dialog.findViewById<TextView>(R.id.tv_message)
                val btnOk = dialog.findViewById<TextView>(R.id.btn_ok)
                tvMessage.text = message
                btnOk.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        dialog.dismiss()
                    }
                })
                dialog.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun hideProgressBar() {

        }

        fun showSnackBar(context: Activity, message: String) {
            val snackbar = Snackbar.make(
                context.findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_LONG
            )
            snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.red))
            snackbar.view.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            snackbar.show()
        }
    }
}