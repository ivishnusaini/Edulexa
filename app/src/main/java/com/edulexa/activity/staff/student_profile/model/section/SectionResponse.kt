package com.edulexa.activity.staff.student_profile.model.section

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SectionResponse {
    @SerializedName("section_list")
    @Expose
    private var sectionList: List<Section?>? = null

    fun getSectionList(): List<Section?>? {
        return sectionList
    }

    fun setSectionList(sectionList: List<Section?>?) {
        this.sectionList = sectionList
    }
}