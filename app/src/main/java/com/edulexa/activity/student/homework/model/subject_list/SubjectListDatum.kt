package com.edulexa.activity.student.homework.model.subject_list

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class SubjectListDatum {
    @SerializedName("subject_group_id")
    @Expose
    private var subjectGroupId: String? = null

    @SerializedName("subject_id")
    @Expose
    private var subjectId: String? = null

    @SerializedName("subjectName")
    @Expose
    private var subjectName: String? = null

    @SerializedName("code")
    @Expose
    private var code: String? = null

    @SerializedName("type")
    @Expose
    private var type: String? = null

    @SerializedName("teacher_name")
    @Expose
    private var teacherName: String? = null

    @SerializedName("surname")
    @Expose
    private var surname: String? = null

    @SerializedName("subject_group_class_sections_id")
    @Expose
    private var subjectGroupClassSectionsId: String? = null

    fun getSubjectGroupId(): String? {
        return subjectGroupId
    }

    fun setSubjectGroupId(subjectGroupId: String?) {
        this.subjectGroupId = subjectGroupId
    }

    fun getSubjectId(): String? {
        if (subjectId == null)
            return ""
        return subjectId
    }

    fun setSubjectId(subjectId: String?) {
        this.subjectId = subjectId
    }

    fun getSubjectName(): String? {
        if (subjectName == null)
            return ""
        return subjectName
    }

    fun setSubjectName(subjectName: String?) {
        this.subjectName = subjectName
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

    fun getTeacherName(): String? {
        return teacherName
    }

    fun setTeacherName(teacherName: String?) {
        this.teacherName = teacherName
    }

    fun getSurname(): String? {
        return surname
    }

    fun setSurname(surname: String?) {
        this.surname = surname
    }

    fun getSubjectGroupClassSectionsId(): String? {
        return subjectGroupClassSectionsId
    }

    fun setSubjectGroupClassSectionsId(subjectGroupClassSectionsId: String?) {
        this.subjectGroupClassSectionsId = subjectGroupClassSectionsId
    }
}