package com.edulexa.activity.student.live_classes.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityLiveYoutubeStudentBinding
import com.pierfrancescosoffritti.youtubeplayer.player.*
import java.util.regex.Pattern

class LiveYoutubeActivity : AppCompatActivity() {
    var mActivity: Activity? = null
    var binding: ActivityLiveYoutubeStudentBinding? = null
    var videoUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveYoutubeStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        getBundleData()
        setUpYoutubePlayer()
    }

    private fun getBundleData(){
        try {
            val bundle = intent.extras
            videoUrl = bundle!!.getString(Constants.StudentLiveClass.VIDEO_URL)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setUpYoutubePlayer(){
        binding!!.youtubePlayerView.playerUIController.enableLiveVideoUI(false)
        binding!!.youtubePlayerView.playerUIController.showYouTubeButton(false)
        binding!!.youtubePlayerView.initialize({ initializedYouTubePlayer: YouTubePlayer ->
            initializedYouTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                override fun onReady() {
                    val youtubeLink = videoUrl
                    var youtubeId: String? = ""
                    if (youtubeLink.contains("https://www.youtube.com/watch?v=")) {
                        var index = youtubeLink.indexOf("=")
                        index = index + 1
                        youtubeId = youtubeLink.substring(index)
                    }
                    initializedYouTubePlayer.loadVideo(youtubeId!!, 0f)
                    addFullScreenListenerToPlayer(binding!!.youtubePlayerView, youtubeId)
                }
            })
        }, true)
    }
    private fun addFullScreenListenerToPlayer(viewfull: YouTubePlayerView, youtubeid: String) {
        viewfull.addFullScreenListener(object : YouTubePlayerFullScreenListener {
            override fun onYouTubePlayerEnterFullScreen() {
                /* val intent = Intent(context, PlayVideoActivity::class.java)
                 intent.putExtra("videoURL", youtubeid)
                 context!!.startActivity(intent)*/
            }

            override fun onYouTubePlayerExitFullScreen() {}
        })
    }
}