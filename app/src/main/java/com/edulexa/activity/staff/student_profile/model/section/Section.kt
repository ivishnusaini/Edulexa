package com.edulexa.activity.staff.student_profile.model.section

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Section {
    var sectionSelect = false

    fun isSectionSelect(): Boolean {
        return sectionSelect
    }

    @JvmName("setSectionSelect1")
    fun setSectionSelect(sectionSelect: Boolean) {
        this.sectionSelect = sectionSelect
    }

    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("section_id")
    @Expose
    private var sectionId: String? = null

    @SerializedName("section")
    @Expose
    private var section: String? = null

    fun getId(): String? {
        if (id == null)
            return ""
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getSectionId(): String? {
        return if (sectionId == null) "" else sectionId
    }

    fun setSectionId(sectionId: String?) {
        this.sectionId = sectionId
    }

    fun getSection(): String? {
        return if (section == null) "null" else section
    }

    fun setSection(section: String?) {
        this.section = section
    }
}