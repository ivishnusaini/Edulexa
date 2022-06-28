package com.edulexa.activity.student.custom_lesson_plan.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.custom_lesson_plan.model.DatumCustomLessonPlan
import com.edulexa.databinding.ItemStudentCustomLessonPlanBinding
import com.edulexa.support.Utils
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView

class CustomLessonPlanAdapter(context: Activity, list : List<DatumCustomLessonPlan?>?) :
    RecyclerView.Adapter<CustomLessonPlanAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DatumCustomLessonPlan?>? = null
    var binding : ItemStudentCustomLessonPlanBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentCustomLessonPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            if (!list!!.get(position)!!.getFile().equals("")){
                binding!!.imageDetailLay.visibility = View.VISIBLE
                binding!!.tvTypeImage.text = "Image"
            }else binding!!.imageDetailLay.visibility = View.GONE

            if (!list!!.get(position)!!.getLectureYoutubeUrl().equals("")){
                binding!!.videoDetailLay.visibility = View.VISIBLE
                binding!!.tvTypeVideo.text = "Video"
            }else binding!!.videoDetailLay.visibility = View.GONE

            binding!!.tvSubtopicVideo.text = list!!.get(position)!!.getTitle()
            binding!!.tvSubtopicImage.text = list!!.get(position)!!.getTitle()
            binding!!.tvTopicVideo.text = list!!.get(position)!!.getTopic()
            binding!!.tvTopicImage.text = list!!.get(position)!!.getTopic()
            binding!!.tvLessonVideo.text = list!!.get(position)!!.getLesson()
            binding!!.tvLessonImage.text = list!!.get(position)!!.getLesson()
            binding!!.tvSubjectName.text = list!!.get(position)!!.getName()
            binding!!.tvSubjectNameImage.text = list!!.get(position)!!.getName()

            binding!!.youtubePlayerView.playerUIController.enableLiveVideoUI(false)
            binding!!.youtubePlayerView.playerUIController.showYouTubeButton(false)
            binding!!.youtubePlayerView.initialize({ initializedYouTubePlayer: YouTubePlayer ->
                initializedYouTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady() {
                        val youtubeLink = list!![position]!!.getLectureYoutubeUrl()
                        var youtubeId: String? = ""
                        if (youtubeLink!!.contains("https://www.youtube.com/watch?v=")) {
                            var index = youtubeLink.indexOf("=")
                            index = index + 1
                            youtubeId = youtubeLink.substring(index)
                        }
                        initializedYouTubePlayer.loadVideo(youtubeId!!, 0f)
                        addFullScreenListenerToPlayer(binding!!.youtubePlayerView, youtubeId)
                    }
                })
            }, true)

            Utils.setImageUsingGlideImagePlaceholder(context,list!!.get(position)!!.getFile(),binding!!.ivImage)

            when(position % 3){
                0 -> {
                    binding!!.videoDetailLay.setBackgroundResource(R.drawable.bg_subject_syllabus_blue)
                    binding!!.imageDetailLay.setBackgroundResource(R.drawable.bg_subject_syllabus_yellow)
                    binding!!.ivInfoImage.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_1)
                    binding!!.ivInfoVideo.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_1)
                    binding!!.ivDownload.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_1)
                }
                1 -> {
                    binding!!.videoDetailLay.setBackgroundResource(R.drawable.bg_subject_syllabus_green)
                    binding!!.imageDetailLay.setBackgroundResource(R.drawable.bg_subject_syllabus_blue)
                    binding!!.ivInfoImage.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_2)
                    binding!!.ivInfoVideo.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_2)
                    binding!!.ivDownload.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_2)
                }
                2 -> {
                    binding!!.videoDetailLay.setBackgroundResource(R.drawable.bg_subject_syllabus_green)
                    binding!!.imageDetailLay.setBackgroundResource(R.drawable.bg_subject_syllabus_blue)
                    binding!!.ivInfoImage.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_3)
                    binding!!.ivInfoVideo.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_3)
                    binding!!.ivDownload.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_3)
                }
                else -> {
                    binding!!.videoDetailLay.setBackgroundResource(R.drawable.bg_subject_syllabus_green)
                    binding!!.imageDetailLay.setBackgroundResource(R.drawable.bg_subject_syllabus_blue)
                    binding!!.ivInfoImage.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_1)
                    binding!!.ivInfoVideo.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_1)
                    binding!!.ivDownload.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_1)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
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

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentCustomLessonPlanBinding) : RecyclerView.ViewHolder(binding.root)

}