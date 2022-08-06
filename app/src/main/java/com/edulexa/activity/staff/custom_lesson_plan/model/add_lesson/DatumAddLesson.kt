package com.edulexa.activity.staff.custom_lesson_plan.model.add_lesson

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DatumAddLesson {
    var isSelect = false

    @JvmName("isSelect1")
    fun isSelect(): Boolean {
        return isSelect
    }

    @JvmName("setSelect1")
    fun setSelect(select: Boolean) {
        isSelect = select
    }

    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("code")
    @Expose
    private var code: String? = null

    @SerializedName("type")
    @Expose
    private var type: String? = null

    @SerializedName("elective_subject")
    @Expose
    private var electiveSubject: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getName(): String? {
        return if (name == null) "" else name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getCode(): String? {
        return code
    }

    fun setCode(code: String?) {
        this.code = code
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String?) {
        this.type = type
    }

    fun getElectiveSubject(): String? {
        return electiveSubject
    }

    fun setElectiveSubject(electiveSubject: String?) {
        this.electiveSubject = electiveSubject
    }
}