package com.edulexa.activity.student.live_classes.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class LiveClassResponse {
    @SerializedName("youtube_list")
    @Expose
    private var youtubeList: YoutubeList? = null

    fun getYoutubeList(): YoutubeList? {
        return youtubeList
    }

    fun setYoutubeList(youtubeList: YoutubeList?) {
        this.youtubeList = youtubeList
    }
}